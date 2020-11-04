import ssh, connect
import pandas as pd

with ssh.Tunnel() as tunnel:
    df = []
    # read sql data, make dataframe
    with connect.Connect(port=tunnel.local_bind_port) as conn:

        sql = "select * from analysis_tour"
        #sql = "update point set base_address = '충청남도 계룡시' where pid = 143"
        #sql = "update crawling_tour set grade = 5 where vote_count > 0 and grade <= 0"
        #sql = "select * from crawling_tour"

        #cur = conn.cursor()
        #cur.execute(sql)
        #conn.commit()

        df = pd.read_sql_query(sql, conn)
        #df.to_csv('./tour_gps_info.csv', encoding='utf-8-sig', mode= 'w')

        print(df)
        #df = pd.read_sql_query(sql, conn)
        #df.to_csv('./tour_info.csv', encoding='utf-8-sig', mode= 'w')

        print('clear!')
