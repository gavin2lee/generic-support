package com.lachesis.support.auth.data.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.auth.common.AuthConstants;
import com.lachesis.support.auth.data.UserDataService;
import com.lachesis.support.auth.repository.RoleRepository;
import com.lachesis.support.auth.repository.UserRepository;
import com.lachesis.support.common.util.text.TextUtils;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.User;
import com.lachesis.support.objects.entity.auth.UserRole;

@Service
public class UserDataServiceImpl implements UserDataService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public User findUserById(Long id) {
		return doFindUserById(id);
	}

	@Override
	public User findUserByUsername(String username) {
		if (TextUtils.isBlank(username)) {
			return null;
		}
		User u = userRepo.findOneByUsername(username);
		if (u == null) {
			return null;
		}

		List<Role> roles = roleRepo.findByUserId(u.getId());

		u.setRoles(roles);

		return u;
	}

	@Override
	@Transactional
	public User saveUser(User u) {
		if (u == null) {
			return null;
		}

		if (u.getId() != null) {
			return null;
		}
		
		if (TextUtils.isBlank(u.getUsername())) {
			return null;
		}
		
		User existingUser = userRepo.findOneByUsername(u.getUsername());
		if(existingUser != null){
			return null;
		}

		User userToSave = new User();
		BeanUtils.copyProperties(u, userToSave);

		if (userToSave.getCreateAt() == null) {
			userToSave.setCreateAt(new Date());
		}

		if (userToSave.getActive() == null) {
			userToSave.setActive(true);
		}

		if (userToSave.getDeleted() == null) {
			userToSave.setDeleted(false);
		}

		if (userToSave.getLocked() == null) {
			userToSave.setLocked(false);
		}

		userRepo.insertOne(userToSave);
		return userToSave;
	}

	@Override
	@Transactional
	public User updateUser(User u) {
		if (u == null) {
			return null;
		}

		if ( (u.getId() == null) || (u.getId() <= 0)) {
			return null;
		}

		User userToUpdate = new User();
		BeanUtils.copyProperties(u, userToUpdate);
		userToUpdate.setUpdateAt(new Date());

		userRepo.updateOne(userToUpdate);

		return doFindUserById(u.getId());
	}

	@Override
	@Transactional
	public User removeUser(User u) {
		if (u == null) {
			return null;
		}

		if ( (u.getId() == null) || (u.getId() <= 0)) {
			return null;
		}

		userRepo.deleteOne(u.getId());
		return u;
	}

	@Override
	@Transactional
	public User addRole(User u, Role r) {
		if ( (u == null) || (r == null)) {
			return null;
		}

		if ( (u.getId() == null) || (r.getId() == null)) {
			return null;
		}

		User existingUser = userRepo.findOne(u.getId());
		if (existingUser == null) {
			return null;
		}

		Role existingRole = roleRepo.findOne(r.getId());
		if (existingRole == null) {
			return null;
		}

		userRepo.addRole(buildUserRole(u.getId(), r.getId()));
		return doFindUserById(u.getId());
	}

	@Override
	@Transactional
	public User removeRole(User u, Role r) {
		if ( (u == null) || (r == null)) {
			return null;
		}

		if ( (u.getId() == null) || (r.getId() == null)) {
			return null;
		}

		userRepo.deleteRole(u.getId(), r);
		return doFindUserById(u.getId());
	}

	protected User doFindUserById(Long id) {
		User u = userRepo.findOne(id);

		if (u == null) {
			return null;
		}

		List<Role> roles = roleRepo.findByUserId(u.getId());

		u.setRoles(roles);

		return u;
	}

	private UserRole buildUserRole(Long userId, Long roleId) {
		UserRole ur = new UserRole();
		ur.setUserId(userId);
		ur.setRoleId(roleId);
		ur.setCreateAt(new Date());
		ur.setDataSource(AuthConstants.DATA_SOURCE_SYSTEM);
		ur.setDeleted(false);

		return ur;
	}

}
