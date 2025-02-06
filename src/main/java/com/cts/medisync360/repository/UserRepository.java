package com.cts.medisync360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.medisync360.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUserName(String userName);
}
