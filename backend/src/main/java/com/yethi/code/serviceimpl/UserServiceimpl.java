package com.yethi.code.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yethi.code.domain.User;
import com.yethi.code.repo.UserRepo;
import com.yethi.code.service.UserService;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public User adduserUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public List<User> getUser() {
		return userRepo.findAll();
	}

}
