import pandas as pd
import numpy as np
import Analysis as analy
from queue import Queue
import time
import datetime

def get_list(tid_list, user_id, start_date, end_date):
    a = analy.Analysis(user_id)
    temp_list = a.get_route(tid_list)
    start_date = datetime.datetime.strptime(start_date, '%Y-%m-%d')
    end_date = datetime.datetime.strptime(end_date, '%Y-%m-%d')
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
    #print(queue_list)

    for i in range(len(temp_list)):
        #print(f'y = [{y}], i = [{i}]')
        queue_list[y].append(temp_list[i])
        if (i + 1) % (put_temp) == 0:
            y = y + 1
        if len(queue_list) <= y:
            # queue_list[y-1].append(temp_list[i+1:].values)
            while i < len(temp_list):
                queue_list[y - 1].append(temp_list[i])
                i += 1
            break

    x = 0
    dis_list = list()
    while days > x:
        dis_list.append(0)
        x = x + 1

    while len(queue_list[days]) > 0:
        #print(queue_list)
        for k in range(len(dis_list)):
            dis_list[k] = 0
        for i in range(len(queue_list) - 1):

            for j in range(len(queue_list[i]) - 1):
                anal_src = a.tour_df[a.tour_df['TID'] == queue_list[i][j]]
                #print(f'queue[{i}][{j}]')
                #print(f'{anal_src}')
                a.set_src_point(anal_src)
                anal_dst = a.tour_df[a.tour_df['TID'] == queue_list[i][j + 1]]
                #print(f'queue[{i}][{j + 1}]')
                #print(f'{anal_dst}')
                a.set_dst_point(anal_dst)
                dis_list[i] = dis_list[i] + a.get_distance()
                #print(dis_list)

        shortest_ind = days - 1

        for i in range(len(dis_list)):
            if dis_list[i] < dis_list[shortest_ind]:
                shortest_ind = i
            else:
                continue

        j = shortest_ind + 1
        while len(queue_list) > j:
            queue_list[j - 1].append(queue_list[j].pop(0))
            j += 1

    #print(queue_list)

    v = 0
    j_list = list()

    while v < days:
        m = 0
        while len(queue_list[v]) > m:
            j = {}
            tmp = a.tour_df[a.tour_df['TID'] == queue_list[v][m]]
            j["label"] = tmp.iloc[0]['label']
            j["address"] = tmp.iloc[0]['address']
            j["img"] = tmp.iloc[0]['depiction']
            j["description"] = tmp.iloc[0]['description']
            j["date"] = datetime.datetime.strftime((start_date + datetime.timedelta(days=v)), '%Y-%m-%d')
            j["time"] = m
            j_list.append(j)
            m = m + 1

        v = v + 1

    return j_list




