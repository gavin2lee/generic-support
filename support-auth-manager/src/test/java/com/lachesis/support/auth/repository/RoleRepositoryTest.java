package com.lachesis.support.auth.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
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
import com.lachesis.support.auth.common.AuthConstants;
import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.RolePermission;
import com.lachesis.support.objects.entity.auth.User;
import com.lachesis.support.objects.entity.auth.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	PermissionRepository permissionRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOne() {
		Role r = roleRepo.findOne(1L);
		assertThat(r, notNullValue());
	}

	@Test
	public void testFindOneByName() {
		String name = "NURSE";
		
		Role r = mockRole();
		r.setName(name);
		
		roleRepo.insertOne(r);
		
		
		Role ret = roleRepo.findOneByName(name);

		assertThat(ret, notNullValue());
		assertThat(ret.getName(), equalTo(name));
	}

	@Test
	public void testFindAll() {
		List<Role> roles = roleRepo.findAll();

		assertThat(roles, notNullValue());
		assertThat(roles.size(), greaterThan(0));

	}

	@Test
	public void testFindByUserId() {
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		User u = mockUser();
		userRepo.insertOne(u);
		
		userRepo.addRole(mockUserRole(u.getId(), r.getId()));
		List<Role> roles = roleRepo.findByUserId(u.getId());

		assertThat(roles, notNullValue());
		assertThat(roles.size(), greaterThan(0));
	}

	@Test
	public void testInsertOne() {
		Role r = mockRole();
		roleRepo.insertOne(r);

		assertThat(r.getId(), notNullValue());
	}

	private Role mockRole() {
		Role r = new Role();
		r.setCode("ROLE-TEST");
		r.setCreateAt(new Date());
		r.setName("ROLE-TEST-"+System.nanoTime());

		r.setDeleted(false);

		return r;
	}

	@Test
	public void testUpdateOne() {
		Role r = mockRole();
		roleRepo.insertOne(r);

		assertThat(r.getId(), notNullValue());
		assertThat(r.getDescription(), nullValue());

		String description = "dec_test";

		r.setDescription(description);
		roleRepo.updateOne(r);

		r = roleRepo.findOne(r.getId());
		assertThat(r.getDescription(), notNullValue());
		assertThat(r.getDescription(), equalTo(description));
	}

	private Permission mockPermission() {
		Permission p = new Permission();
		p.setName("PERM-TEST-" + System.nanoTime());
		p.setLabel("LABEL-TEST");

		return p;
	}

	@Test
	public void testAddPermission() {
		Permission p = mockPermission();
		permissionRepo.insertOne(p);

		assertThat(p.getId(), notNullValue());

		Role r = mockRole();
		roleRepo.insertOne(r);

		assertThat(r.getId(), notNullValue());

		roleRepo.addPermission(mockRolePermission(r.getId(), p.getId()));
	}

	@Test
	public void testAddPermissions() {
		Permission p1 = mockPermission();
		Permission p2 = mockPermission();
		permissionRepo.insertOne(p1);
		permissionRepo.insertOne(p2);
		
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		List<RolePermission> rps = mockRolePermissions(r.getId(), p1.getId(), p2.getId());
		
		Integer ret = roleRepo.addPermissions(rps);
		assertThat(ret, equalTo(2));
		
		Role r1 = roleRepo.findOne(r.getId());
		assertThat(r1, notNullValue());
		assertThat(r1.getPermissions(), notNullValue());
		assertThat(r1.getPermissions().size(), equalTo(2));
	}

	private List<RolePermission> mockRolePermissions(Long roleId, Long... permissionIds) {
		List<RolePermission> rps = new ArrayList<RolePermission>();
		for (Long pid : permissionIds) {
			rps.add(mockRolePermission(roleId, pid));
		}

		return rps;
	}

	private RolePermission mockRolePermission(Long roleId, Long permissionId) {
		RolePermission rp = new RolePermission();
		rp.setCreateAt(new Date());
		rp.setRoleId(roleId);
		rp.setDataSource(AuthConstants.DATA_SOURCE_SYSTEM);
		rp.setPermissionId(permissionId);
		rp.setDeleted(false);

		return rp;
	}

	@Test
	public void testDeleteOne() {
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		assertThat(r.getId(), notNullValue());
		
		Role r1 = roleRepo.findOne(r.getId());
		assertThat(r1, notNullValue());
		
		roleRepo.deleteOne(r.getId());
		r1 = roleRepo.findOne(r.getId());
		
		assertThat(r1, nullValue());
		
	}

	@Test
	public void testDeletePermissions() {
		Permission p1 = mockPermission();
		Permission p2 = mockPermission();
		permissionRepo.insertOne(p1);
		permissionRepo.insertOne(p2);
		
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		List<RolePermission> rps = mockRolePermissions(r.getId(), p1.getId(), p2.getId());
		
		Integer ret = roleRepo.addPermissions(rps);
		assertThat(ret, equalTo(2));
		
		Role r1 = roleRepo.findOne(r.getId());
		assertThat(r1, notNullValue());
		assertThat(r1.getPermissions(), notNullValue());
		assertThat(r1.getPermissions().size(), equalTo(2));
		
		List<Permission> perms = new ArrayList<Permission>();
		perms.add(p1);
		perms.add(p2);
		roleRepo.deletePermissions(r1.getId(), perms);
		
		r1 = roleRepo.findOne(r.getId());
		
		assertThat(r1, notNullValue());
		assertThat(r1.getPermissions(), notNullValue());
		assertThat(r1.getPermissions().size(), equalTo(0));
	}

	@Test
	public void testDeletePermission() {
		Permission p1 = mockPermission();
		Permission p2 = mockPermission();
		permissionRepo.insertOne(p1);
		permissionRepo.insertOne(p2);
		
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		List<RolePermission> rps = mockRolePermissions(r.getId(), p1.getId(), p2.getId());
		
		Integer ret = roleRepo.addPermissions(rps);
		assertThat(ret, equalTo(2));
		
		Role r1 = roleRepo.findOne(r.getId());
		assertThat(r1, notNullValue());
		assertThat(r1.getPermissions(), notNullValue());
		assertThat(r1.getPermissions().size(), equalTo(2));
		
		roleRepo.deletePermission(r1.getId(), p1);
		
		r1 = roleRepo.findOne(r.getId());
		
		assertThat(r1, notNullValue());
		assertThat(r1.getPermissions(), notNullValue());
		assertThat(r1.getPermissions().size(), equalTo(1));
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

}
