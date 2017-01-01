package com.lachesis.support.auth.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.data.RoleService;
import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.restful.context.vo.ResponseVO;

@RestController
@RequestMapping("roles")
public class RoleController {
	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping(method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO addRole(@RequestBody Role r) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("add role:" + r);
		}

		Role retRole = roleService.saveRole(r);
		
		if(retRole == null){
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}

		return ResponseVO.ok(retRole);
	}

	@RequestMapping(method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO getRoles() {
		if (LOG.isTraceEnabled()) {
			LOG.trace("get roles");
		}

		List<Role> roles = roleService.findAll();
		return ResponseVO.ok(roles);
	}

	@RequestMapping(path = "/{roleId}", method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO getRoleById(@PathVariable("roleId") long id) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("get role by id :" + id);
		}

		Role roleRet = roleService.findRoleById(id);
		return ResponseVO.ok(roleRet);
	}
	
	@RequestMapping(path = "/{roleId}", method = { RequestMethod.PUT }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO updateRole(@PathVariable("roleId") long roleId, @RequestBody Role role){
		
		Role roleRet = roleService.updateRole(role);
		
		if(roleRet == null){
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}
		return ResponseVO.ok(roleRet);
	}

	@RequestMapping(path = "/{roleId}", method = { RequestMethod.DELETE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO deleteRoleById(@PathVariable("roleId") long id) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("delete role by id:" + id);
		}

		Role roleToRemove = new Role();
		roleToRemove.setId(id);
		roleService.removeRole(roleToRemove);

		return ResponseVO.NO_CONTENT;
	}

	@RequestMapping(path = "/{roleId}/permissions", method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO addPermission(@PathVariable("roleId") long roleId, @RequestBody Permission p) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("add permission for role %d", roleId));
		}
		Role roleRet = roleService.addPermission(createStubRoleWithId(roleId), p);
		
		if(roleRet == null){
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}

		return ResponseVO.ok(roleRet);
	}

	@RequestMapping(path = "/{roleId}/permissions/{permId}", method = { RequestMethod.DELETE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO deletePermission(@PathVariable("roleId") long roleId, @PathVariable("permId") long permId) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("delete permission %d for role %d", permId, roleId));
		}

		Permission p = new Permission();
		p.setId(permId);

		roleService.deletePermission(createStubRoleWithId(roleId), p);
		return ResponseVO.NO_CONTENT;
	}

	private Role createStubRoleWithId(long id) {
		Role r = new Role();
		r.setId(id);

		return r;
	}
}
