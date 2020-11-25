import getTourList
import time

start = time.time()
getTourList.getTourList('제주특별자치도', '1133', '2020-11-30', '2020-12-03')
print(time.time() - start)