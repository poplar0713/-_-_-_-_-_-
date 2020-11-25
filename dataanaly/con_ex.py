import ssh, connect
import pandas as pd

with ssh.Tunnel() as tunnel:
    df = []
    # read sql data, make dataframe
    with connect.Connect(port=tunnel.local_bind_port) as conn:

        #sql = "delete from schedule where uid = '1133' and schedule_name = 'To 전라북도 전주시'"
        sql = "select * from schedule where uid = '1133' and schedule_name = 'To 전라북도 전주시'"
        #sql = "insert into point (base_address, gps_lat, gps_long) values('제주특별자치도','33.489011','126.498302')"
        #sql = "select * from crawling_tour"

        #cur = conn.cursor()
        #cur.execute(sql)
        #conn.commit()

        df = pd.read_sql_query(sql, conn)

        #df.to_csv('./base_point.csv', encoding='utf-8-sig', mode= 'w')

        print(df)
        #df = pd.read_sql_query(sql, conn)
        #df.to_csv('./tour_info.csv', encoding='utf-8-sig', mode= 'w')

        print('clear!')
