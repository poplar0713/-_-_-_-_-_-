import ssh, connect
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

#ibcf행렬 파일을 생성하는 파일
with ssh.Tunnel() as tunnel:
    # read sql data, make dataframe
    with connect.Connect(port=tunnel.local_bind_port) as conn:
        sql = "select * from analysis_tour"
        a_df = pd.read_sql_query(sql, conn)
        sql = "select * from crawling_tour"
        t_df = pd.read_sql_query(sql, conn)

        print("==Data Frame Ready==")

        rating_place = pd.merge(a_df, t_df, on='TID')
        ratings_matrix = rating_place.pivot_table('GRADE', index='uid', columns='TID')
        ratings_matrix = ratings_matrix.fillna(0)

        ratings_matrix_T = ratings_matrix.transpose()
        print("==Matrix Ready==")

        item_sim = cosine_similarity(ratings_matrix_T, ratings_matrix_T)

        item_sim_df = pd.DataFrame(data=item_sim, index=ratings_matrix.columns, columns=ratings_matrix.columns)
        print("==Similarity sort ok==")
        item_sim_df.to_csv('item_sim.csv', mode='w', encoding='utf-8-sig')
