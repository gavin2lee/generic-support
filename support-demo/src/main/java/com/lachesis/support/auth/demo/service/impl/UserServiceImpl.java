package com.lachesis.support.auth.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.demo.repository.UserRepository;
import com.lachesis.support.auth.demo.service.UserService;
import com.lachesis.support.common.util.dal.BaseRepository;
import com.lachesis.support.common.util.service.AbstractCrudService;
import com.lachesis.support.objects.entity.auth.User;

@Service("userService")
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

	@Override
	protected BaseRepository<User> getBaseRepository() {
		return userRepo;
	}

	

}
