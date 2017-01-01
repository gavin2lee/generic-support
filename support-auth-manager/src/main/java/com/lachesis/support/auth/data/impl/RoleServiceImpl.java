package com.lachesis.support.auth.data.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.auth.common.AuthConstants;
import com.lachesis.support.auth.data.RoleService;
import com.lachesis.support.auth.repository.PermissionRepository;
import com.lachesis.support.auth.repository.RoleRepository;
import com.lachesis.support.common.util.text.TextUtils;
import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.RolePermission;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PermissionRepository permRepo;

	@Override
	@Transactional
	public Role saveRole(Role r) {
		if (r == null) {
			return null;
		}

		if (r.getId() != null) {
			return null;
		}

		if (TextUtils.isBlank(r.getName())) {
			return null;
		}

		Role existingRole = roleRepo.findOneByName(r.getName());
		if (existingRole != null) {
			return null;
		}

		Role roleToSave = new Role();
		BeanUtils.copyProperties(r, roleToSave);
		
		if(roleToSave.getCreateAt() == null){
			roleToSave.setCreateAt(new Date());
		}
		
		if(roleToSave.getDeleted() == null){
			roleToSave.setDeleted(false);
		}
		
		if(roleToSave.getDataSource() == null){
			roleToSave.setDataSource(AuthConstants.DATA_SOURCE_SYSTEM);
		}

		roleRepo.insertOne(roleToSave);
		return roleToSave;
	}

	@Override
	@Transactional
	public Role updateRole(Role r) {
		if (r == null) {
			return null;
		}

		if ( (r.getId() == null) || (r.getId() <= 0)) {
			return null;
		}

		Role roleToUpdate = new Role();
		BeanUtils.copyProperties(r, roleToUpdate);
		roleToUpdate.setUpdateAt(new Date());

		roleRepo.updateOne(roleToUpdate);
		return roleToUpdate;
	}

	@Override
	@Transactional
	public Role removeRole(Role r) {
		if (r == null) {
			return null;
		}

		if ( (r.getId() == null) || (r.getId() <= 0)) {
			return null;
		}

		roleRepo.deleteOne(r.getId());

		return r;
	}

	@Override
	public Role findRoleById(Long id) {

		return roleRepo.findOne(id);
	}

	@Override
	public Role findRoleByName(String name) {
		if (TextUtils.isBlank(name)) {
			return null;
		}
		return roleRepo.findOneByName(name);
	}

	@Override
	public List<Role> findAll() {
		return roleRepo.findAll();
	}

	@Override
	@Transactional
	public Role addPermission(Role role, Permission perm) {
		if (role == null || (perm == null)) {
			return null;
		}

		if (role.getId() == null || (role.getId() <= 0)) {
			return null;
		}

		if (perm.getId() == null || (perm.getId() <= 0)) {
			return null;
		}

		Role existingRole = roleRepo.findOne(role.getId());
		if (existingRole == null) {
			return null;
		}

		Permission existingPerm = permRepo.findOne(perm.getId());
		if (existingPerm == null) {
			return null;
		}

		roleRepo.addPermission(buildRolePermission(role.getId(), perm.getId()));

		existingRole = roleRepo.findOne(role.getId());
		return existingRole;
	}

	@Override
	@Transactional
	public Role deletePermission(Role role, Permission perm) {
		if (role == null || (perm == null)) {
			return null;
		}

		if (role.getId() == null || (role.getId() <= 0)) {
			return null;
		}

		if (perm.getId() == null || (perm.getId() <= 0)) {
			return null;
		}

		roleRepo.deletePermission(role.getId(), perm);

		Role existingRole = roleRepo.findOne(role.getId());
		return existingRole;
	}

	private RolePermission buildRolePermission(Long roleId, Long permId) {
		RolePermission rp = new RolePermission();
		rp.setRoleId(roleId);
		rp.setPermissionId(permId);
		rp.setCreateAt(new Date());
		rp.setDataSource(AuthConstants.DATA_SOURCE_SYSTEM);
		rp.setDeleted(false);

		return rp;
	}

}
