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

import com.lachesis.support.auth.data.PermissionService;
import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.restful.context.vo.ResponseVO;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
	private static final Logger LOG = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	private PermissionService permService;

	@RequestMapping(method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO addPermission(@RequestBody Permission p) {
		if(LOG.isTraceEnabled()){
			LOG.trace("add permission:"+p);
		}
		Permission retPerm = permService.savePermission(p);
		
		if(retPerm == null){
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}
		return ResponseVO.ok(retPerm);
	}
	
	@RequestMapping(path = "/{id}", method = { RequestMethod.PUT }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO modifyPermission(@PathVariable("id") long permId, @RequestBody Permission p){
		if(LOG.isTraceEnabled()){
			LOG.trace("modify permission:"+p);
		}
		Permission retPerm = permService.updatePermission(p);
		
		if(retPerm == null){
			LOG.warn("operation failed");
			return ResponseVO.CONFLICT;
		}
		return ResponseVO.ok(retPerm);
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO getPermissions(){
		List<Permission> perms = permService.findAll();
		if(perms == null){
			return ResponseVO.NOT_FOUND;
		}
		return ResponseVO.ok(perms);
	}
	
	@RequestMapping(path = "/{id}", method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseVO getPermission(@PathVariable("id") long permId){
		Permission perm = permService.findPermissionById(permId);
		if(perm == null){
			return ResponseVO.NOT_FOUND;
		}
		return ResponseVO.ok(perm);
	}
}
