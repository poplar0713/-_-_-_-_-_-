import math
import numpy as np

#두 여행지의 gps값을 비교하여 두 지점사이의 거리를 측정하는 클래스
class GPSDistance:
    def __init__(self, base_lat=0.0, base_long=0.0, dest_lat=0.0, dest_long=0.0):
        self.src_lat = base_lat
        self.src_long = base_long
        self.dst_lat = dest_lat
        self.dst_long = dest_long

    # set source(base) point GPS latitude and longitude
    # input :   (lat, long) float
    #           (['gps_lat', 'gps_long']) list, dict, tuple, DataFrame available
    def set_src_gps(self, *args, **kwargs):
        # print("args:", args)
        # print("kwargs:", kwargs)
        # print(kwargs.items())

        if (str(type(args[0])) == "<class 'dict'>") or (
                str(type(args[0])) == "<class 'pandas.core.frame.DataFrame'>") or (
                str(type(args[0])) == "<class 'pandas.core.series.Series'>"):
            kwargs = args[0]
            # print(args[0])
            # print(kwargs)

        if 'gps_lat' in kwargs:
            self.src_lat = kwargs['gps_lat']
            #print(self.src_lat)
        else:
            self.src_lat = args[0]

        if 'gps_long' in kwargs:
            self.src_long = kwargs['gps_long']
            #print(self.src_long)
        else:
            self.src_long = args[1]

        if (str(type(self.src_lat)) == "<class 'str'>") or (str(type(self.src_long)) == "<class 'str'>"):
            self.src_lat = np.float(self.src_lat)
            self.src_long = np.float(self.src_long)

        # print("base", self.src_lat, self.src_long, sep="\n")

    # set destination point GPS latitude and longitude
    # input :   (lat, long) float
    #           (['gps_lat', 'gps_long']) list, dict, tuple, DataFrame available
    def set_dst_gps(self, *args, **kwargs):
        # print("args:", args)
        # print("kwargs:", kwargs)
        # print(kwargs.items())

        if (str(type(args[0])) == "<class 'dict'>") or (
                str(type(args[0])) == "<class 'pandas.core.frame.DataFrame'>") or (
                str(type(args[0])) == "<class 'pandas.core.series.Series'>"):
            kwargs = args[0]
            # print(args[0])
            # print(kwargs)

        if 'gps_lat' in kwargs:
            self.dst_lat = kwargs['gps_lat']
            #print(self.dst_lat)
        else:
            self.dst_lat = args[0]

        if 'gps_long' in kwargs:
            self.dst_long = kwargs['gps_long']
            #print(self.dst_long)
        else:
            self.dst_long = args[1]

        # print(self.dst_lat, self.dst_long)

        if (str(type(self.dst_lat)) == "<class 'str'>") or (str(type(self.dst_long)) == "<class 'str'>"):
            self.dst_lat = np.float(self.dst_lat)
            self.dst_long = np.float(self.dst_long)

        # print("dest", *args, **kwargs, sep="\n")

    # Get distance between source point and destination point using GPS latitude and longitude
    def get_distance(self):
        #print(type(self.src_lat), ":", self.src_lat)
        #print(type(self.src_long), ":", self.src_long)
        res = 6371 * math.acos(math.cos(math.radians(self.src_lat)) * math.cos(math.radians(self.dst_lat)) * math.cos(
            math.radians(self.dst_long) - math.radians(self.src_long)) + math.sin(
            math.radians(self.src_lat)) * math.sin(math.radians(self.dst_lat)))
        return res

    def get_src(self):
        return {'gps_lat': self.src_lat, 'gps_long': self.src_long}

    def get_dst(self):
        return {'gps_lat': self.dst_lat, 'gps_long': self.dst_long}
