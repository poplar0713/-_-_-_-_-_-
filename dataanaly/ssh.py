from sshtunnel import SSHTunnelForwarder

class Tunnel:
    def __init__(self):
        #ssh 서버에 접속하기위한 정보를 가지고 있다.
        self.tunnel = SSHTunnelForwarder(('211.253.26.214', 22),
                                         ssh_username='analy',
                                         ssh_password='b5j4Mj6YvA9P^^',
                                         remote_bind_address=('localhost', 3306)
                                         )

    def __enter__(self):
        print("SSH tunnel start")
        self.tunnel.start()
        return self.tunnel

    def __exit__(self, exc_type, exc_val, exc_tb):
        print("SSH tunnel closed")
        self.tunnel.close()