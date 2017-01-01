package com.lachesis.support.auth.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.User;
import com.lachesis.support.objects.entity.auth.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testFindOne() {
		User u = mockUser();
		userRepo.insertOne(u);
		
		assertThat(u.getId(), notNullValue());
		User u1 = userRepo.findOne(u.getId());
		assertThat(u1, notNullValue());
	}

	@Test
	public void testFindOneByUsername() {
		User u = mockUser();
		userRepo.insertOne(u);
		
		assertThat(u.getId(), notNullValue());
		User u1 = userRepo.findOneByUsername(u.getUsername());
		assertThat(u1, notNullValue());
	}

	@Test
	@Rollback(false)
	public void testInsertOne() {
		User u = mockUser();
		Long ret = userRepo.insertOne(u);

		assertThat(ret, greaterThan(0L));
		assertThat(u.getId(), notNullValue());
	}

	@Test
	public void testUpdateOne() {
		User u = mockUser();
		userRepo.insertOne(u);
		
		assertThat(u.getId(), notNullValue());
		
		String mobilePhone = "13789899999";
		
		User u2 = userRepo.findOne(u.getId());
		
		assertThat(u2, notNullValue());
		assertThat(u2.getMobilePhone(),not(mobilePhone));
		
		User u1 = new User();
		u1.setId(u.getId());
		u1.setCreateAt(new Date());
		u1.setMobilePhone(mobilePhone);
		
		int ret = userRepo.updateOne(u1);
		assertThat(ret, greaterThan(0));
		
		u2 = userRepo.findOne(u.getId());
		
		assertThat(u2, notNullValue());
		assertThat(u2.getMobilePhone(), equalTo(mobilePhone));
	}

	@Test
	public void testAddRole() {
		User u = mockUser();
		Role r = mockRole();
		
		long retUser = userRepo.insertOne(u);
		long retRole = roleRepo.insertOne(r);
		
		assertThat(retUser, greaterThan(0L));
		assertThat(retRole, greaterThan(0L));
		assertThat(u.getId(), notNullValue());
		assertThat(r.getId(), notNullValue());
		
		UserRole ur = mockUserRole(u.getId(), r.getId());
		userRepo.addRole(ur);
		
		List<Role> roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), greaterThan(0));
		
		assertThat(roles.get(0).getName(), equalTo(r.getName()));
	}

	@Test
	public void testAddRoles() {
		User u = mockUser();
		Role r1 = mockRole();
		Role r2 = mockRole();
		
		userRepo.insertOne(u);
		roleRepo.insertOne(r1);
		roleRepo.insertOne(r2);
		
		assertThat(u.getId(), notNullValue());
		assertThat(r1.getId(), notNullValue());
		assertThat(r2.getId(), notNullValue());
		
		List<UserRole> urs = mockUserRoles(u.getId(), r1.getId(),r2.getId());
		userRepo.addRoles(urs);
		
		List<Role> roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), equalTo(2));
	}

	@Test
	public void testDeleteRoles() {
		User u = mockUser();
		Role r1 = mockRole();
		Role r2 = mockRole();
		
		userRepo.insertOne(u);
		roleRepo.insertOne(r1);
		roleRepo.insertOne(r2);
		
		assertThat(u.getId(), notNullValue());
		assertThat(r1.getId(), notNullValue());
		assertThat(r2.getId(), notNullValue());
		
		List<UserRole> urs = mockUserRoles(u.getId(), r1.getId(),r2.getId());
		userRepo.addRoles(urs);
		
		List<Role> roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), equalTo(2));
		
		List<Role> rolesToDelete = new ArrayList<Role>();
		rolesToDelete.add(r1);
		rolesToDelete.add(r2);
		
		userRepo.deleteRoles(u.getId(), rolesToDelete);
		roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), equalTo(0));
	}

	@Test
	public void testDeleteRole() {
		User u = mockUser();
		Role r1 = mockRole();
		Role r2 = mockRole();
		
		userRepo.insertOne(u);
		roleRepo.insertOne(r1);
		roleRepo.insertOne(r2);
		
		assertThat(u.getId(), notNullValue());
		assertThat(r1.getId(), notNullValue());
		assertThat(r2.getId(), notNullValue());
		
		List<UserRole> urs = mockUserRoles(u.getId(), r1.getId(),r2.getId());
		userRepo.addRoles(urs);
		
		List<Role> roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), equalTo(2));
		
		userRepo.deleteRole(u.getId(), r1);
		roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), equalTo(1));
	}

	@Test
	public void testDeleteAllRoles() {
		User u = mockUser();
		Role r1 = mockRole();
		Role r2 = mockRole();
		
		userRepo.insertOne(u);
		roleRepo.insertOne(r1);
		roleRepo.insertOne(r2);
		
		assertThat(u.getId(), notNullValue());
		assertThat(r1.getId(), notNullValue());
		assertThat(r2.getId(), notNullValue());
		
		List<UserRole> urs = mockUserRoles(u.getId(), r1.getId(),r2.getId());
		userRepo.addRoles(urs);
		
		List<Role> roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), equalTo(2));
		
		userRepo.deleteAllRoles(u.getId());
		roles = roleRepo.findByUserId(u.getId());
		assertThat(roles, notNullValue());
		assertThat(roles.size(), equalTo(0));
	}

	@Test
	public void testDeleteOne() {
		User u = mockUser();
		Long ret = userRepo.insertOne(u);

		assertThat(ret, greaterThan(0L));
		assertThat(u.getId(), notNullValue());
		
		int retDel = userRepo.deleteOne(u.getId());
		assertThat(retDel, equalTo(1));
		u = userRepo.findOne(u.getId());
		assertThat(u, nullValue());
	}
	
	private User mockUser() {
		User u = new User();
		u.setId(System.nanoTime());
		u.setUsername("user-test-" + System.nanoTime());
		u.setName("USER-TEST");
		u.setCode("code-test");
		u.setTelephone("0755-89896666");
		u.setPassword("123");
		u.setCreateAt(new Date());
		u.setActive(true);
		u.setDeleted(false);
		u.setLocked(false);
		u.setCreateBy(1L);
		u.setEmail("abcddd@lachesis.com");
		u.setDataSource("SYSTEM");
		u.setGender("M");
		u.setMobilePhone("189252222211");
		u.setUpdateAt(new Date());
		u.setUpdateBy(2L);

		return u;
	}
	
	private Role mockRole(){
		Role r = new Role();
		r.setCode("ROLE_TEST-");
		r.setCreateAt(new Date());
		r.setName("ROLE-TEST-"+System.nanoTime());

		r.setDeleted(false);

		return r;
	}
	
	private UserRole mockUserRole(long userId, long roleId){
		UserRole ur = new UserRole();
		ur.setId(System.nanoTime());
		ur.setUserId(userId);
		ur.setRoleId(roleId);
		ur.setDataSource("SYSTEM");
		ur.setCreateBy(userId);
		ur.setCreateAt(new Date());
		ur.setDeleted(false);
		
		return ur;
	}
	
	private List<UserRole> mockUserRoles(long userId, Long...roleIds){
		List<UserRole> urs = new ArrayList<UserRole>();
		for(long roleId : roleIds){
			urs.add(mockUserRole(userId, roleId));
		}
		
		return urs;
	}

}
