package com.lachesis.support.auth.repository;

import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.auth.common.AuthConstants;
import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.RolePermission;

@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class PermissionRepositoryTest {
	
	@Autowired
	PermissionRepository permRepo;
	
	@Autowired
	RoleRepository roleRepo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOne() {
		Permission p = mockPermission();
		permRepo.insertOne(p);
		
		Permission ret = permRepo.findOne(p.getId());
		
		Assert.assertThat(ret, Matchers.notNullValue());
	}

	@Test
	public void testFindOneByName() {
		String name = "DEPT:LIST";
		
		Permission p = mockPermission();
		p.setName(name);
		
		permRepo.insertOne(p);
		
		
		Permission ret = permRepo.findOneByName(name);
		Assert.assertThat(ret, Matchers.notNullValue());
		Assert.assertThat(ret.getName(), Matchers.equalTo(name));
	}

	@Test
	public void testFindAll() {
		List<Permission> permissions = permRepo.findAll();
		
		Assert.assertThat(permissions, Matchers.notNullValue());
	}

	@Test
	public void testFindByRoleId() {
		Permission p = mockPermission();
		permRepo.insertOne(p);
		
		Role r = mockRole();
		roleRepo.insertOne(r);
		
		RolePermission rp = mockRolePermission(r.getId(), p.getId());
		roleRepo.addPermission(rp);
		
		List<Permission> permissions = permRepo.findByRoleId(r.getId());
		
		Assert.assertThat(permissions, Matchers.notNullValue());
	}

	@Test
	public void testInsertOne() {
		Permission p = new Permission();
		p.setCode("P:READ");
		p.setDescription("描述");
		p.setLabel("读权限");
		p.setName("P:READ");
		p.setDeleted(false);
		
		Long ret = permRepo.insertOne(p);
		
		Assert.assertThat(ret, Matchers.greaterThan(0L));
		Assert.assertThat(p.getId(), Matchers.notNullValue());
		Assert.assertThat(p.getId(), Matchers.greaterThan(0L));
	}

	@Test
	public void testUpdateOne() {
		Permission p = mockPermission();
		permRepo.insertOne(p);
		
		p = permRepo.findOne(p.getId());
		Assert.assertThat(p, Matchers.notNullValue());
		
		String des = "descriptions";
		p.setDescription(des);
		permRepo.updateOne(p);
		
		p = permRepo.findOne(p.getId());
		Assert.assertThat(p, Matchers.notNullValue());
		Assert.assertThat(p.getDescription(), Matchers.equalTo(des));
	}

	@Test
	public void testDeleteOne() {
		Permission p = new Permission();
		p.setCode("P:READ");
		p.setDescription("描述");
		p.setLabel("读权限");
		p.setName("P:READ");
		p.setDeleted(false);
		
		permRepo.insertOne(p);
		
		Permission p2 = permRepo.findOne(p.getId());
		
		Assert.assertThat(p2, Matchers.notNullValue());
		permRepo.deleteOne(p2.getId());
		
		Permission p3 = permRepo.findOne(p2.getId());
		Assert.assertThat(p3, Matchers.nullValue());
	}
	
	private Permission mockPermission(){
		Permission p = new Permission();
		long timestamp = System.nanoTime();
		p.setCode("PERM-"+timestamp);
		p.setDeleted(false);
		p.setDescription("permission-des");
		p.setLabel("权限-"+timestamp);
		p.setName("PERM:TEST-"+timestamp);
		
		return p;
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
	
	private Role mockRole() {
		Role r = new Role();
		r.setCode("ROLE-TEST");
		r.setCreateAt(new Date());
		r.setName("ROLE-TEST-"+System.nanoTime());

		r.setDeleted(false);

		return r;
	}

}
