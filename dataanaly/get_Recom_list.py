import pandas as pd
import numpy as np
import ssh, connect

rating_pred_df = pd.read_csv('rating_pred_df.csv')
rating_pred_df = rating_pred_df.set_index('Unnamed: 0')
ratings_matrix = pd.read_csv('rating_matrix.csv')
ratings_matrix = ratings_matrix.set_index('uid')

def get_unvisit(user_id):

    user_rating = ratings_matrix.loc[user_id, :]
    already = user_rating[user_rating != 0.0].index.tolist()
    pl_list = ratings_matrix.columns.tolist()
    unvisit_list = [pl for pl in pl_list if pl not in already]

    return unvisit_list

def get_list(user_id, top_n = 30):
    with ssh.Tunnel() as tunnel:
        with connect.Connect(port=tunnel.local_bind_port) as conn:
            unvisit_list = get_unvisit(user_id)
            recomm_pl = rating_pred_df.loc[f'{user_id}', unvisit_list].sort_values(ascending=False)[:top_n]
            recomm_pl = recomm_pl.index.values.tolist()

            tmp = '(99999999'
            for str in recomm_pl:
                tmp = tmp + ',' + str
            tmp = tmp + ')'

            sql = f"select label, address, depiction from crawling_tour where tid IN {tmp}"
            df = pd.read_sql_query(sql, conn)
            l = list()
            x = 0
            while x < len(df.index) :
                j = {}
                label = df.iloc[x]['label']
                address = df.iloc[x]['address']
                img = df.iloc[x]['depiction']
                j["label"] = label
                j["address"] = address
                j["img"] = img
                l.append(j)
                x = x+1

            return l

