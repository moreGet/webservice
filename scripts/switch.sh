#!/usr/bin/env bash

# 현재 stop.sh가 속해 있는 경로를 찾습니다. 하단의 코드와 같이 profile.shh의 경로를 찾기 위해 사용됩니다.
ABSPATH=$(readlink -f $0)
# 일종의 import 구문입니다, 해당 코드로 인해 stop.sh에서도 profile.sh의 여러 function을 사용할 수 있게 됩니다.
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    # 하나의 문장을 만들어 파이프라인(I) 으로 넘겨주기 위해 echo를 사용
    # 엔진엑스가 변경할 프록시 주소를 생성
    # 쌍따옴표를 사용
    # 사용하지 않으면 $service_url을 그대로 인식하지 못하고 변수를 찾게됨
    # tee는 파일 덮어쓰기
    echo "set \$service_url http://127.0.0.1${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    # reload는 restart와는 다르게 끊김없이 nginx를 불러옴 중요 설정들은 반영할 땐 restart를 사용 해야함
    # 외부 설정 파일을 불러오는 거라 reload 가능
    echo "> 엔진엑스 Reload"
    sudo service nginx reload
}