package com.lachesis.support.auth.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.auth.model.AuthUser;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class AuthUserRepositoryTest {

	@Autowired
	private AuthUserRepository authUserRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindById() {
		Long id = 1L;

		AuthUser u = authUserRepository.findById(id);

		Assert.assertThat(u, Matchers.notNullValue());
		Assert.assertThat(u.getId(), Matchers.equalTo(id));
	}

	@Test
	public void testFindByUsername() {
		String username  = "283";

		AuthUser u = authUserRepository.findByUsername(username);

		Assert.assertThat(u, Matchers.notNullValue());
		Assert.assertThat(u.getUsername(), Matchers.equalTo(username));
	}

}
