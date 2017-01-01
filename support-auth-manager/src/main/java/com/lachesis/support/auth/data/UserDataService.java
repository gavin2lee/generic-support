package com.lachesis.support.auth.data;

import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.User;

public interface UserDataService {
	User findUserById(Long id);
	User findUserByUsername(String username);
	
	User saveUser(User u);
	User updateUser(User u);
	User removeUser(User u);
	
	User addRole(User u, Role r);
	User removeRole(User u, Role r);
}
