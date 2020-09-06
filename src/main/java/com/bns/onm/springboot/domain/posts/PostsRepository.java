package com.bns.onm.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
- 보통 ibatis나 mybatis등에서 사용 하는 Dao
- 기본 Entity 클래스와 기본 EntityRepository는 같은 패키지에 위치 해야함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
