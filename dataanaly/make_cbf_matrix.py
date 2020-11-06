import pandas as pd
import numpy as np
import warnings; warnings.filterwarnings('ignore')
import re
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from konlpy.tag import Okt
import time
import ssh, connect

#cbf 행렬 파일을 생성하는 파일
with ssh.Tunnel() as tunnel:
    with connect.Connect(port=tunnel.local_bind_port) as conn:

        start = time.time()

        sql = "select * from crawling_tour"
        places_df = pd.read_sql_query(sql, conn)

        print("====== 데이터 로드 성공 ======")

        places_df['type'] = places_df['type'].str.replace(':','')
        places_df['category'] = places_df['category'].str.replace('.',' ')

        #데이터 전처리 작업 (한자제거, 특수문자 기호제거)
        places_df['lab'] = places_df['label'].str.replace('(',' ')
        places_df['lab'] = places_df['label'].str.replace(')',' ')

        places_df['description'].fillna(' ')
        places_df['label'].fillna(' ')
        han = re.compile('[^ ㄱ-ㅎ | 가-힣 | 0-9 | a-z | A-Z ]+')


        places_df['lab'] = places_df['label'].apply(lambda x : han.sub("",str(x)))
        places_df['des'] = places_df['description'].apply(lambda x : han.sub("",str(x)))

        #한국어 형태소 분석모듈 
        okt = Okt()

        print('====== 명사화 및 데이터 전 처리 중 ======')
        #여행지명과 여행지 설명 데이터를 명사로 나누고 리스트로 저장시킨다.
        places_df['lab'] = places_df['lab'].apply(lambda x : okt.nouns(x))
        places_df['des'] = places_df['des'].apply(lambda x : okt.nouns(x))

        #리스트화 된 명사들을 문자열타입으로 다시 변환시켜 준다
        places_df['lab'] = places_df['lab'].apply(lambda x : (' ').join(x))
        places_df['des'] = places_df['des'].apply(lambda x : (' ').join(x))
        places_df['des'] = places_df[['des', 'category']].apply(lambda x : (' ').join(x), axis=1)
        places_df['lab'] = places_df[['lab', 'type']].apply(lambda x : (' ').join(x), axis=1)

        count_vect = CountVectorizer(min_df=0,ngram_range=(1,2))

        print('====== 코사인 벡터화 작업 중 ======')
        #각 속성을 유사도 벡터화 시킴
        cat_mat1 = count_vect.fit_transform(places_df['des'])
        cat_mat2 = count_vect.fit_transform(places_df['lab'])

        #각 유사도 행렬들을 일정한 비율로 반영
        cat_sim1 = cosine_similarity(cat_mat1, cat_mat1)
        cat_sim2 = cosine_similarity(cat_mat2, cat_mat2)
        cat_sim1 *= 0.9
        cat_sim2 *= 0.1

        print("====== 행렬화 완료 =======")

        cat_sim = cat_sim1 + cat_sim2
        #유사도가 큰 순으로 정렬
        cat_sim = cat_sim.argsort()[:, ::-1]
        cat_sim = cat_sim[:, 0:500]

        print(cat_sim.shape)
        print(cat_sim)

        np.save('./CBF_Matrix', cat_sim)

        print("======저장 완료. CBF_Matrix.npy 가 생성되었습니다.======")

        print(time.time() - start)