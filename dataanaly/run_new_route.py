import get_new_route
import time

start = time.time()

# 주의 : 여행일수보다 여행지 갯수가 작으면 실행 안됨
sample_list = [5514, 12760, 9357, 10071, 8710, 3255, 13218, 8671, 13051, 8711, 13050, 7258, 6947, 12496, 12878, 237]

print(get_new_route.get_list(sample_list, '174', '2020-11-16', '2020-11-30'))
print(time.time() - start)