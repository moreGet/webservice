#!/usr/bin/env bash

# 쉬고 있는 profile 찾기: prd1이 사용 중이면 prd2가 쉬고 있고, 반대면 prd1이 쉬고 있음
function find_idle_profile()
{
  # 현재 엔진엑스가 바라보고 있는 스프링 부트가 정상적으로 수행 중인지 확인합니다.
  # 응답값을 httpStatus로 받습니다.
  # 정상이면 200, 오류라면 400 이상 발생시 prd2를 현재 profile로 사용
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] # 에러가 400 보다 크면
  then
    CURRENT_PROFILE=prd2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == prd1 ]
  then
    # 엔진엑스와 연결되지 않은 profile입니다.
    # 스프링 부트 프로젝트를 이 profile로 연결하기 위해 반환합니다.
    IDLE_PROFILE=prd2
  else
    IDLE_PROFILE=prd1
  fi

  # bash스크립트는 return기능이 없어 출력후 그 값을 사용함.
  # 따라서 echo 를 중간에서 사용하면 안됩니다.
  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == prd1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}