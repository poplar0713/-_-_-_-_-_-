import pandas as pd
import numpy as np
import Analysis as analy
import get_Recom_list
import time
import ssh, connect

def get_ex(group_no, uid):
    with ssh.Tunnel() as tunnel:
        # read sql data, make dataframe
        with connect.Connect(port=tunnel.local_bind_port) as conn:
            sql = f"select TID from schedule where uid = {uid} and schedule_name = {group_no}"
            df = pd.read_sql_query(sql, conn)

            return df

def make_list(user_id, base_point, group_no):
    strat = time.time()
    already_ex = get_ex(group_no, user_id)
    a = analy.Analysis(user_id)
    print(time.time() - strat)
    bad_df = a.analy_df[a.analy_df['GRADE'] < 0]
    range = 16
    anal_src = a.base_df[a.base_df['base_address'] == base_point]
    a.set_src_point(anal_src)
    unvisit_list = get_Recom_list.get_unvisit(user_id)
    recomm_pl = get_Recom_list.rating_pred_df.loc[f'{user_id}', unvisit_list].sort_values(ascending=False)[:500]
    recomm_pl = recomm_pl.index.values.tolist()
    target_df = a.tour_df[a.tour_df['category'].str.contains('자연|인문|레포츠|쇼핑|카페|경기장|도서관|문화전수시설|문화원|공연장', na=False)]

    print(time.time() - strat)

    tmp1_df = pd.DataFrame(columns=['TID', 'label', 'address', 'depiction', 'grade', 'vote_count'])
    tmp2_df = pd.DataFrame(columns=['TID', 'label', 'address', 'depiction', 'grade', 'vote_count'])

    for i in recomm_pl:
        tmp = target_df[target_df['TID'] == i]
        tmp1_df = tmp1_df.append(tmp, ignore_index=True)

    result_df = pd.DataFrame(columns=['TID', 'label', 'address', 'depiction', 'grade', 'vote_count'])

    print(time.time() - strat)

    for r in tmp1_df['TID']:
        if (bad_df['TID'] == r).any():
            continue
        if (already_ex['TID'] == r).any():
            continue

        anal_dst = target_df[target_df['TID'] == r]

        a.set_dst_point(anal_dst)

        # 행렬속 여행지가 여행 범위내로 들어오는지 검사
        if a.get_distance() <= range:
            result_df = result_df.append(anal_dst, ignore_index=True)

    print(time.time() - strat)

    for l in target_df['TID']:

        if (bad_df['TID'] == l).any():
            continue
        if (already_ex['TID'] == l).any():
            continue
        anal_dst = target_df[target_df['TID'] == l]

        a.set_dst_point(anal_dst)

        # 행렬속 여행지가 여행 범위내로 들어오는지 검사
        if a.get_distance() <= range:
            tmp2_df = tmp2_df.append(anal_dst, ignore_index=True)

    print(time.time() - strat)

    t = tmp2_df.sort_values('grade', ascending=False)[:]
    # 선정된 여행지들을 평점순으로 정렬

    result_df = result_df.append(t, ignore_index = True)
    result_df = result_df.drop_duplicates(['label'])
    result_df = result_df[result_df['category'].str.contains('자연|인문|레포츠|쇼핑|카페|경기장|도서관|문화전수시설|문화원|공연장', na=False)]

    print(time.time() - strat)
    l = list()
    x = 0
    while x < len(result_df.index):
        j = {}
        label = result_df.iloc[x]['label']
        address = result_df.iloc[x]['address']
        img = result_df.iloc[x]['depiction']
        j["label"] = label
        j["address"] = address
        j["img"] = img
        l.append(j)
        x = x + 1
    print(time.time() - strat)
    return l







