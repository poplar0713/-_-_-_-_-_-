import pandas as pd
import numpy as np
import Analysis as analy
import get_Recom_list
import time
import ssh, connect

def make_list(user_id, base_point, group_no, already_ex_list):
    strat = time.time()
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
    #target_df = target_df[target_df['address'].str.contains(f'{base_point}', na=False)]

    print(time.time() - strat)

    tmp1_df = pd.DataFrame(columns=['TID', 'label', 'address', 'description', 'depiction', 'grade', 'vote_count'])
    tmp2_df = pd.DataFrame(columns=['TID', 'label', 'address', 'description', 'depiction', 'grade', 'vote_count'])

    for i in recomm_pl:
        tmp = target_df[target_df['TID'] == i]
        tmp1_df = tmp1_df.append(tmp, ignore_index=True)

    result_df = pd.DataFrame(columns=['TID', 'label', 'description', 'address', 'depiction', 'grade', 'vote_count'])

    print(time.time() - strat)

    for r in tmp1_df['TID']:
        if (bad_df['TID'] == r).any():
            continue
        if r in already_ex_list:
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
        if l in already_ex_list:
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
        info = result_df.iloc[x]['description']
        img = result_df.iloc[x]['depiction']
        tid = result_df.iloc[x]['TID']

        j["name"] = label
        j["img"] = img
        j["info"] = info
        j["tid"] = tid
        j["group_no"] = group_no

        l.append(j)
        x = x + 1
    print(time.time() - strat)
    return l







