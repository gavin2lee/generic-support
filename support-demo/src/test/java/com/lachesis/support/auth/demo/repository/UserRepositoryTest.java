package com.lachesis.support.auth.demo.repository;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.objects.entity.auth.User;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/support-auth-manager-repository-test2.xml" })
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;

	@Before
	public void setUp() throws Exception {
	}

//	@Ignore
	@Test
	public void testFindOne() {
		Assert.assertThat(userRepo, Matchers.notNullValue());
		
		User u = userRepo.findOne(1L, User.class);
		
		Assert.assertThat(u, Matchers.notNullValue());
	}
	@Ignore
	@Test
	public void testSaveOne(){
		User u = new User();
		u.setUsername("9999");
		Integer numbers = userRepo.saveOne(u, User.class);
		
		Assert.assertThat(numbers, Matchers.greaterThan(0));
	}
	
	@Test
	@Transactional
	public void testSaveOneInBatch(){
		User u1 = new User();
		u1.setUsername("11111111");
		
		User u2 = new User();
		u2.setUsername("22222222");
		
		User u3 = new User();
		u3.setUsername("33333333");
		
		User u4 = new User();
		u4.setUsername("44444444");
		
		User u5 = new User();
		u5.setUsername("55555555");
		
		User u6 = new User();
		u6.setUsername("66666666");
		
		User u7 = new User();
		u7.setUsername("777777777");
		
		User u8 = new User();
		u8.setUsername("888888888");
		
		Integer numbers = userRepo.saveOne(u1, User.class);
		numbers += userRepo.saveOne(u2, User.class);
		numbers += userRepo.saveOne(u3, User.class);
		numbers += userRepo.saveOne(u4, User.class);
		numbers += userRepo.saveOne(u5, User.class);
		numbers += userRepo.saveOne(u6, User.class);
		numbers += userRepo.saveOne(u7, User.class);
		numbers += userRepo.saveOne(u8, User.class);
		
		Assert.assertThat(numbers, Matchers.greaterThan(0));
	}
	
	@Ignore
	@Test(expected = RuntimeException.class)
	public void testSaveOneWithException(){
		User u = new User();
		u.setId(1L);
		u.setUsername("9999");
		Integer numbers = userRepo.saveOne(u, User.class);
		
		Assert.assertThat(numbers, Matchers.greaterThan(0));
	}
	
	@Ignore
	@Test
	public void testUpdateOne(){
		User u = new User();
		u.setId(1L);
		u.setMobilePhone("13966668888");
		int numbers = userRepo.updateOne(u, User.class);
		
		u = userRepo.findOne(1L, User.class);
		
		Assert.assertThat(u, Matchers.notNullValue());
		Assert.assertThat(numbers, Matchers.greaterThan(0));
		
	}
	
	@Ignore
	@Test
	public void testDeleteOne(){
		userRepo.deleteOne(5L, User.class);
		
		User u = userRepo.findOne(5L, User.class);
		Assert.assertThat(u, Matchers.nullValue());
	}
	
	@Ignore
	@Test
	public void testFindListByCriteria(){
		User template = new User();
		template.setPassword("123");
		
		List<User> users = userRepo.findListByCriteria(template, User.class);
		
		Assert.assertThat(users, Matchers.notNullValue());
		Assert.assertThat(users.size(), Matchers.greaterThan(1));
	}

}
