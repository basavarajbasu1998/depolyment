package com.yethi.code.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yethi.code.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
