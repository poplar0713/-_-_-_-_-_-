import pandas as pd
import ssh, connect
import random
import distance

#분석에 필요한 데이터들을 로드, 가공하여 주는 클래스
class Analysis:

    pick_number = 4
    distance = distance.GPSDistance()

    def __init__(self, user_id='12'):
        try:
            with ssh.Tunnel() as tunnel:
                with connect.Connect(port=tunnel.local_bind_port) as conn:
                    #사용자 선호도 데이터를 로드
                    sql = f"select * from analysis_tour where uid = '{user_id}'"
                    self.analy_df = pd.read_sql_query(sql, conn)
                    self.analy_df = self.analy_df.drop_duplicates(['TID'])

                    #여행지 정보를 로드
                    sql = "select TID, label, address, category, depiction, description, grade, vote_count, gps_lat, gps_long from crawling_tour"
                    self.tour_df = pd.read_sql_query(sql, conn)
                    # self.tour_df.set_index('TID', inplace=True)
                    # print(type(float(self.tour_df['gps_lat'].iloc[0])))

                    #여행지역의 gps 정보들을 로드
                    sql = "select * from point"
                    self.base_df = pd.read_sql_query(sql, conn)

                    print('########## Analyis module init complete ##########')

        except Exception as e:
            print(e)

    def set_src_point(self, *args, **kwargs):
        # 기준점이 되는 여행지를 선택하는 메소드
        # print("anal-args:", args)
        # print("anal-kwargs:", kwargs)

        self.distance.set_src_gps(*args, **kwargs)
        # if 'gps_lat' in kwargs.keys() and 'gps_long' in kwargs.keys():
        #     self.distance.set_src_gps(kwargs)
        # elif str(type(args[0])) == "<class 'dict'>" or str(type(args[0])) == "<class 'pandas.core.frame.DataFrame'>":
        #     self.distance.set_src_gps(args[0])
        # else:
        #     self.distance.set_src_gps(args[0], args[1])

    def set_dst_point(self, *args, **kwargs):
        # 기준점으로부터 거리를 재고자하는 여행지를 선택하는 메소드
        # print("anal-args:", args)
        # print("anal-kwargs:", kwargs)

        self.distance.set_dst_gps(*args, **kwargs)
        # if 'gps_lat' in kwargs.keys() and 'gps_long' in kwargs.keys():
        #     self.distance.set_dst_gps(kwargs)
        # elif str(type(args[0])) == "<class 'dict'>" or str(type(args[0])) == "<class 'pandas.core.frame.DataFrame'>":
        #     self.distance.set_dst_gps(args[0])
        # else:
        #     self.distance.set_dst_gps(args[0], args[1])

    def get_distance(self, *args, **kwargs):
        # 두 여행지 사이의 거리를 반환하는 메소드
        # print("args:", args)
        # print("kwargs:", kwargs)

        if bool(args) or bool(kwargs):
            # print(bool(args) or bool(kwargs))
            self.set_dst_point(*args, **kwargs)
        return self.distance.get_distance()

    def get_route(self, tour_list):
        #여행지 id 리스트를 받아 최단 경로로 리스트를 정렬하는 메소드

        if len(tour_list) < 2:
            return tour_list

        result = []
        temp = self.tour_df[['TID', 'gps_lat', 'gps_long']]
        temp.set_index('TID', inplace=True)

        distance = [[1000 for i in range(len(tour_list))] for j in range(len(tour_list))]
        visited = [False for i in range(len(tour_list))]
        check = [True for i in range(len(tour_list))]

        for i in range(len(tour_list)):
            self.set_src_point(temp.loc[tour_list[i]])
            for j in range(len(tour_list)):
                if i == j:
                    continue
                distance[i][j] = self.get_distance(temp.loc[tour_list[j]])

        now = 0
        while not check == visited:
            result.append(tour_list[now])
            visited[now] = True
            ok = True
            while ok:
                if check == visited:
                    break
                pick = distance[now].index(min(distance[now]))
                if visited[pick]:
                    distance[now][pick] = 1000
                else:
                    ok = False

            now = pick

        return result