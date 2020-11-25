import get_new_route
import time

start = time.time()

# 주의 : 여행일수보다 여행지 갯수가 작으면 실행 안됨
sample_list = [5514, 12760]

print(get_new_route.get_list(sample_list, '174', '2020-11-16', '2020-11-30'))
print(time.time() - start)