package com.interview.template.dao;

import com.interview.template.model.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	@Query("select * from users us where us.username  like ?1%")
    List<UserEntity> fetchMatchedList(String enterUserName);
}
