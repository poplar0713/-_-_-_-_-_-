import pandas as pd
import numpy as np

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


def get_list(user_id, top_n = 20) :

    unvisit_list = get_unvisit(user_id)
    recomm_pl = rating_pred_df.loc[f'{user_id}', unvisit_list].sort_values(ascending=False)[:top_n]
    recomm_pl = recomm_pl.index.values.tolist()

    return recomm_pl



