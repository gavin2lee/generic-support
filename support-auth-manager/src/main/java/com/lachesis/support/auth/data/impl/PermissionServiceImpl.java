package com.lachesis.support.auth.data.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.auth.data.PermissionService;
import com.lachesis.support.auth.repository.PermissionRepository;
import com.lachesis.support.common.util.text.TextUtils;
import com.lachesis.support.objects.entity.auth.Permission;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permRepo;

	@Override
	@Transactional
	public Permission savePermission(Permission p) {
		if(p == null){
			return null;
		}
		
		if(p.getId() > 0){
			return null;
		}
		
		if(TextUtils.isBlank(p.getName())){
			return null;
		}
		
		Permission permExisting = permRepo.findOneByName(p.getName());
		
		if(permExisting != null){
			return null;
		}
		
		Permission permToSave = new Permission();
		BeanUtils.copyProperties(p, permToSave);
		
		permRepo.insertOne(permToSave);
		
		return permToSave;
	}

	@Override
	@Transactional
	public Permission updatePermission(Permission p) {
		if(p == null){
			return null;
		}
		
		if(p.getId() == null || (p.getId() <= 0)){
			return null;
		}
		
		Permission permToUpdate = new Permission();
		BeanUtils.copyProperties(p, permToUpdate);
		
		permRepo.updateOne(permToUpdate);
		return permToUpdate;
	}

	@Override
	@Transactional
	public Permission removePermission(Permission p) {
		if(p == null){
			return null;
		}
		
		if(p.getId() == null || (p.getId() <= 0)){
			return null;
		}
		
		permRepo.deleteOne(p.getId());
		return p;
	}

	@Override
	public Permission findPermissionById(Long id) {
		if(id == null || (id <= 0)){
			return null;
		}
		Permission p = permRepo.findOne(id);
		return p;
	}

	@Override
	public Permission findPermissionByName(String name) {
		if(TextUtils.isBlank(name)){
			return null;
		}
		
		Permission p = permRepo.findOneByName(name);
		return p;
	}

	@Override
	public List<Permission> findAll() {
		return permRepo.findAll();
	}

}
