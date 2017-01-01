package com.lachesis.support.auth.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.demo.service.UserService;
import com.lachesis.support.common.util.service.CrudService;
import com.lachesis.support.objects.entity.auth.User;

@RestController
@RequestMapping("users")
public class UserController extends com.lachesis.support.restful.context.controller.AbstractRestController<User,User> {
	@Autowired
	private UserService userService;

	@Override
	protected CrudService<User> getCrudService() {
		
		return userService;
	}
}
