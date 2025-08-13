package com.yethi.code.service;

import java.util.List;

import com.yethi.code.domain.User;

public interface UserService {

	User adduserUser(User user);

	List<User> getUser();
}
