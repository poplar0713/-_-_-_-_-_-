import getTourList
import time

start = time.time()
getTourList.getTourList('부산광역시 해운대구', 'ohee78', '2020-11-01', '2020-11-04')
print(time.time() - start)