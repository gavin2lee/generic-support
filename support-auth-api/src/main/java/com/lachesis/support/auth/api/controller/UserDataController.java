package com.lachesis.support.auth.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.data.UserDataService;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.User;
import com.lachesis.support.restful.context.vo.ResponseVO;

@RestController
@RequestMapping("users")
public class UserDataController {
	private static final Logger LOG = LoggerFactory.getLogger(UserDataController.class);

	@Autowired
	private UserDataService userDataService;

	@RequestMapping(method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO addUser(@RequestBody User u) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("add user " + u);
		}

		User userRet = userDataService.saveUser(u);
		
		if(userRet == null){
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}

		return ResponseVO.ok(userRet);
	}

	@RequestMapping(path = "/{id}", method = { RequestMethod.PUT }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO updateUser(@PathVariable("id") long userId, @RequestBody User u) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("update user " + u);
		}

		User userRet = userDataService.updateUser(u);

		if (userRet == null) {
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}
		return ResponseVO.ok(userRet);
	}

	@RequestMapping(path = "/{id}", method = { RequestMethod.DELETE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO deleteUser(@PathVariable("id") long userId) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("delete user " + userId);
		}
		userDataService.removeUser(createUserWithId(userId));
		return ResponseVO.NO_CONTENT;
	}

	@RequestMapping(path = "/{id}", method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO findUserById(@PathVariable("id") long userId) {
		User userRet = userDataService.findUserById(userId);

		if (userRet == null) {
			return ResponseVO.NOT_FOUND;
		}
		return ResponseVO.ok(userRet);
	}

	@RequestMapping(path = "/user", method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO findUserByUsername(@RequestParam("username") String username) {
		User userRet = userDataService.findUserByUsername(username);
		if (userRet == null) {
			return ResponseVO.NOT_FOUND;
		}
		return ResponseVO.ok(userRet);
	}

	@RequestMapping(path = "/{userId}/roles", method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO addRole(@PathVariable("userId") long userId, @RequestBody Role role) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("add role for user " + userId);
		}

		User userRet = userDataService.addRole(createUserWithId(userId), role);

		if (userRet == null) {
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}

		return ResponseVO.ok(userRet);
	}

	@RequestMapping(path = "/{userId}/roles/{roleId}", method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO deleteRole(@PathVariable("userId") long userId, @PathVariable("roleId") long roleId) {

		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("delete role %d from user %d", roleId, userId));
		}

		Role r = new Role();
		r.setId(roleId);

		userDataService.removeRole(createUserWithId(userId), r);
		return ResponseVO.NO_CONTENT;
	}

	private User createUserWithId(long id) {
		User u = new User();
		u.setId(id);

		return u;
	}
}
