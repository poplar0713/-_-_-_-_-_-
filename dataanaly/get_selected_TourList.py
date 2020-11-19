import saveListtoDB as sd
import pandas as pd
import numpy as np
import datetime
import Analysis as analy
import time

# 추천에 필요한 여행지 유사도 행렬을 로드
place_sim_sorted_ind = np.load('CBF_Matrix.npy')
item_sim_df = pd.read_csv('item_sim.csv')
item_sim_df = item_sim_df.set_index('TID')
sample_list = list()

#여행 리스틑 생성하는 함수
def getTourList(base_address, user_id, start_date, end_date, selected_list = sample_list):
    start_date = datetime.datetime.strptime(start_date, '%Y-%m-%d')
    end_date = datetime.datetime.strptime(end_date, '%Y-%m-%d')
    a = analy.Analysis(user_id)

    good_df = a.analy_df[a.analy_df['GRADE'] > -1]
    bad_df = a.analy_df[a.analy_df['GRADE'] < 0]

    # analy모듈에서 데이터베이스 정보를 읽어오는데 시간이 필요하다.
    time.sleep(3)

    days = ((end_date - start_date).days) + 1
    numOfoneday = 4
    range = 15
    runtime = 0

    def make_tourList(base_point, days, runtime, range=20.0):

        anal_src = a.base_df[a.base_df['base_address'] == base_point]
        print(anal_src)
        # 출발 포인트 설정
        a.set_src_point(anal_src)

        def find_sim_pl(df, sorted_ind, tid, top_n=10):

            place = df[df['TID'] == tid]

            place_index = place.index.values
            similar_indexes = sorted_ind[place_index, :(top_n)]

            similar_indexes = similar_indexes.reshape(-1)

            return df.iloc[similar_indexes]

        result_df = pd.DataFrame(columns=['TID', 'label', 'address', 'category', 'grade', 'vote_count'])

        for i in good_df['TID']:
            # 사용자가 선호하는 여행지에 대하여 ibcf 행렬에서 가장 유사한 정도로 정렬

            similar_place = item_sim_df[f'{i}'].sort_values(ascending=False)[1:(days * (10 + (runtime * 10)))]

            for l in similar_place.index:

                if (bad_df['TID'] == l).any():
                    continue

                anal_dst = a.tour_df[a.tour_df['TID'] == l]

                a.set_dst_point(anal_dst)

                # 행렬속 여행지가 여행 범위내로 들어오는지 검사
                if a.get_distance() <= range:
                    result_df = result_df.append(anal_dst, ignore_index=True)

            similar_place = find_sim_pl(a.tour_df, place_sim_sorted_ind, i, (days * (10 + (runtime * 10))))

            for j in similar_place['TID']:

                if (bad_df['TID'] == j).any():
                    continue

                # 사용자가 선호하는 여행지에 대하여 cbf 행렬에서 가장 유사한 정도로 정렬
                anal_dst = a.tour_df[a.tour_df['TID'] == j]
                a.set_dst_point(anal_dst)

                # 행렬속 여행지가 여행 범위내로 들어오는지 검사
                if a.get_distance() <= range:
                    result_df = result_df.append(anal_dst, ignore_index=True)

        result_df = result_df.drop_duplicates(['label'])
        result_df = result_df[result_df['category'].str.contains('자연|인문|레포츠|쇼핑|카페', na=False)]

        t = result_df.sort_values('grade', ascending=False)[:(days * 20)]
        # 선정된 여행지들을 평점순으로 정렬
        t = t.sample(frac=1).reset_index(drop=True)

        return t

    tmp_df = make_tourList(base_address, days, runtime, range)

    # 선택한 지역과 날짜에 대하여 최소 여행지 갯수가 나올때까지 반복
    while len(selected_list) + len(tmp_df.index) < (days * numOfoneday):
        # 반복시마다 검색 범위 확대
        range += 5
        runtime += 1
        tmp_df = make_tourList(base_address, days, runtime, range)

    tmp_df = tmp_df.drop_duplicates(['gps_lat', 'gps_long'])

    i = 0
    temp_list = list()
    while i < len(selected_list):
        temp_list.append(selected_list[i])
        i += 1
    while i < days * numOfoneday:
        temp_list.append(tmp_df.iloc[i]['TID'])
        i += 1

    # 여행지들을 최단경로로 정렬
    temp_list = a.get_route(temp_list)

    days = ((end_date - start_date).days) + 1

    numOfoneday = int(len(temp_list) / days) + 1
    queue_list = list()
    x = 0
    while days + 1 > x:
        l = list()
        queue_list.append(l)
        x = x + 1
    y = 0
    put_temp = numOfoneday - 1

    print(temp_list)
    f = (len(temp_list))
    i = 0

    while i < f:
        queue_list[y].append(temp_list[i])
        if (i + 1) % (put_temp) == 0:
            y = y + 1
        if len(queue_list) <= y:
            while i < len(temp_list):
                queue_list[y - 1].append(temp_list[i])
                i += 1
            break
        i = i+1

    x = 0
    dis_list = list()
    while days > x:
        dis_list.append(0)
        x = x + 1

    while len(queue_list[days]) > 0:
        k=0
        while k < (len(dis_list)):
            dis_list[k] = 0
            k = k+1
        o = 0
        while o < (len(queue_list) - 1):
            j=0
            while j < (len(queue_list[o]) - 1):
                anal_src = a.tour_df[a.tour_df['TID'] == queue_list[o][j]]
                a.set_src_point(anal_src)
                anal_dst = a.tour_df[a.tour_df['TID'] == queue_list[o][j + 1]]
                a.set_dst_point(anal_dst)
                dis_list[o] = dis_list[o] + a.get_distance()
                j = j+1
            o = o+1
        shortest_ind = days - 1

        e=0
        while e < (len(dis_list)):
            if dis_list[e] < dis_list[shortest_ind]:
                e = e + 1
                shortest_ind = e
            else:
                e = e + 1
                continue


        s = shortest_ind + 1
        while len(queue_list) > s:
            queue_list[s - 1].append(queue_list[s].pop(0))
            s += 1

    result_df = pd.DataFrame(columns=['TID', 'UID', 'DATE', 'TIME'])

    v = 0
    x = 0
    while v < days:
        m = 0
        while len(queue_list[v]) > m:
            result_df.loc[x] = [queue_list[v][m], user_id, datetime.datetime.strftime((start_date + datetime.timedelta(days=v)), '%Y-%m-%d'), m]
            x = x+1
            m = m + 1
        v = v+1

    #print(result_df)
    #tmp_print = pd.merge(result_df, a.tour_df, how='left')
    #print(tmp_print)

    issuc = sd.save(result_df, base_address)

    if issuc == 1:
        print('일정 생성 완료')
    else:
        print('생성 실패')
