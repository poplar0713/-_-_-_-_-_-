import ssh, connect
import time

#생성된 리스트를 DB에 저장하는 코드이다.
def save(df, base_address):
    try:
        with ssh.Tunnel() as tunnel:
            with connect.Connect(port=tunnel.local_bind_port) as conn:
                cur = conn.cursor()
                #db에 저장되는 연,월,시간을 기준으로 추천 여행지의 그룹 ID를 생성한다.
                now = time.strftime('%y%m%d%H%M%S', time.localtime(time.time()))
                group_no = f"{now}"
                x=0
                print("UID | TID | SCHEDULE_NAME | DATE | GROUP_NO | TIME")
                while x < len(df.index):
                    #DB에 저장
                    sql = f"insert into schedule (UID, TID, SCHEDULE_NAME, DATE, GROUP_NO, TIME) value ('{df.loc[x]['UID']}',{df.loc[x]['TID']},'{base_address}','{df.loc[x]['DATE']}','{group_no}',{df.loc[x]['TIME']})"
                    cur.execute(sql)
                    conn.commit()
                    x += 1
    except Exception as e:
        print(e)

        return -1

    finally:
        return 1