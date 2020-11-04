import pymysql

class Connect:

    def __init__(self, port=3306):
        #서버내의 DB에 접속하기 위한 정보를 가지고 있다.
        self.conn = pymysql.connect(host='localhost',
                                    port=port,
                                    user='analy',
                                    password='ajs0701^^',
                                    charset='utf8',
                                    db='jsdb')
        print("connect__init__")

    def __enter__(self):
        print("connect__enter__")
        return self.conn

    def __exit__(self, exc_type, exc_val, exc_tb):
        print("connect__exit__")
        self.conn.close()
