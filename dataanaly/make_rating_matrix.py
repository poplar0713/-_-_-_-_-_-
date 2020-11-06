import ssh, connect
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import mean_squared_error

def make_matrix() :
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

            ratings_matrix.to_csv('rating_matrix.csv', mode='w', encoding='utf-8-sig')

            ratings_matrix_T = ratings_matrix.transpose()
            print("==Matrix Ready==")

            item_sim = cosine_similarity(ratings_matrix_T, ratings_matrix_T)

            item_sim_df = pd.DataFrame(data=item_sim, index=ratings_matrix.columns, columns=ratings_matrix.columns)
            print("==Similarity sort ok==")

            def predict_rating_topsim(ratings_arr, item_sim_arr, n=20):
                pred = np.zeros(ratings_arr.shape)

                for col in range(ratings_arr.shape[1]):
                    top_n_items = [np.argsort(item_sim_arr[:, col])[:-n-1:-1]]

                    for row in range(ratings_arr.shape[0]):
                        pred[row, col] = item_sim_arr[col, :][top_n_items].dot(ratings_arr[row, :][top_n_items].T)
                        pred[row, col] /= np.sum(np.abs(item_sim_arr[col, :][top_n_items]))

                return pred

            rating_pred = predict_rating_topsim(ratings_matrix.values, item_sim_df.values, n=len(ratings_matrix.columns))

            rowlist = ratings_matrix.index.values.tolist()
            collist = ratings_matrix.columns.values.tolist()

            ratings_pred_df = pd.DataFrame(data=rating_pred, index=rowlist, columns=collist)
            #print(ratings_pred_df)

            ratings_pred_df.to_csv('rating_pred_df.csv', mode='w', encoding='utf-8-sig')
            print("======= 예측평점 리스트 생성 완료 =======")
