import get_selected_TourList
import time
sample_list = [3255, 13218, 8671, 13051, 8711]

start = time.time()
get_selected_TourList.getTourList('전라북도 전주시', '1133', '2020-11-16', '2020-11-17', sample_list)
print(time.time() - start)