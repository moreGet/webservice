#!/usr/bin/env bash

# 현재 stop.sh가 속해 있는 경로를 찾습니다. 하단의 코드와 같이 profile.shh의 경로를 찾기 위해 사용됩니다.
ABSPATH=$(readlink -f $0)
# 일종의 import 구문입니다, 해당 코드로 인해 stop.sh에서도 profile.sh의 여러 function을 사용할 수 있게 됩니다.
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=webservice

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echp "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"
echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo"> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar -Dspring.config.location=classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-prd-db.properties -Dspring.properties.active=$IDLE_PROFILE $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &