package com.bns.onm.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
- 보통 ibatis나 mybatis등에서 사용 하는 Dao
- 기본 Entity 클래스와 기본 EntityRepository는 같은 패키지에 위치 해야함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    // SpringDataJpa에서 제공하지 않는 메소드는 아래처럼 쿼리로 작성 해도 됨
    // 가독성이 훨씬 뛰어남
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
