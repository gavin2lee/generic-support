package com.lachesis.support.auth.repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lachesis.support.auth.annotation.RepositoryTestContext;
import com.lachesis.support.objects.entity.auth.Token;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTestContext
public class TokenRepositoryTest {
	@Autowired
	TokenRepository repo;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSelectList() {
	}

	@Test
	@Transactional
	@Rollback
	public void testSelectOne() {
		long sizeBefore = repo.selectSize();

		Token tokenToInsert = mockToken();
		repo.insertOne(tokenToInsert);
		Token t = repo.selectOne(tokenToInsert.getTokenValue());

		long sizeAfter = repo.selectSize();

		Assert.assertThat(t, Matchers.notNullValue());
		Assert.assertThat(sizeAfter, Matchers.is(sizeBefore + 1));
	}

	@Test
	@Transactional
	@Rollback
	public void testSelectPage() {
		List<Token> tokensToInsert = new LinkedList<Token>();
		int maxSize = 300;
		int pageNo = 1;
		int pageSize = 10;

		for (int i = 0; i < 300; i++) {
			tokensToInsert.add(mockToken());
		}

		int ret = repo.insertBatch(tokensToInsert);

		Assert.assertThat(ret, Matchers.equalTo(maxSize));

		Page<Token> page = PageHelper.startPage(pageNo, pageSize);
//		PageHelper.startPage(pageNo, pageSize);
		List<Token> pagedTokens = repo.selectPage();

		Assert.assertThat(page.getPageSize(), Matchers.equalTo(pageSize));
		Assert.assertThat(pagedTokens, Matchers.notNullValue());
		Assert.assertThat(pagedTokens.size(), Matchers.equalTo(pageSize));
	}

	@Test
	@Transactional
	@Rollback
	public void testInsertOne() {
		Token t = new Token();
		t.setActive(true);
		t.setLastModified(new Date());
		t.setPassword("111");
		t.setTerminalIp("10.0.0.1");
		t.setTokenValue("2222");
		t.setUsername("2888");

		int ret = repo.insertOne(t);
		Assert.assertThat(ret, Matchers.greaterThan(0));
	}

	@Test
	@Transactional
	@Rollback
	public void testInsertBatch() {
		List<Token> tokensToInsert = new LinkedList<Token>();
		int maxSize = 300;

		for (int i = 0; i < 300; i++) {
			tokensToInsert.add(mockToken());
		}

		int ret = repo.insertBatch(tokensToInsert);

		Assert.assertThat(ret, Matchers.equalTo(maxSize));
	}

	@Test
	@Transactional
	@Rollback
	public void testUpdateOne() {
		Token tokenToInsert = mockToken();
		repo.insertOne(tokenToInsert);

		Token tokenSelected = repo.selectOne(tokenToInsert.getTokenValue());

		Assert.assertThat(tokenSelected, Matchers.notNullValue());
		Assert.assertThat(tokenSelected.getUsername(), Matchers.equalTo(tokenToInsert.getUsername()));

		String newUsername = "88888";

		Token tokenToUpdate = new Token();
		tokenToUpdate.setLastModified(new Date());
		tokenToUpdate.setUsername(newUsername);
		tokenToUpdate.setTokenValue(tokenToInsert.getTokenValue());

		Integer ret = repo.updateOne(tokenToUpdate);
		Assert.assertThat(ret, Matchers.greaterThan(0));

		tokenSelected = repo.selectOne(tokenToInsert.getTokenValue());
		Assert.assertThat(tokenSelected, Matchers.notNullValue());
		Assert.assertThat(tokenSelected.getUsername(), Matchers.equalTo(newUsername));
	}

	@Ignore
	@Test
	@Transactional
	@Rollback(false)
	public void testDeleteAll() {
		long ret = repo.deleteAll();
		long totalSize = repo.selectSize();
		Assert.assertThat(ret, Matchers.greaterThanOrEqualTo(0L));
		Assert.assertThat(totalSize, Matchers.greaterThanOrEqualTo(0L));
	}

	@Test
	@Transactional
	@Rollback
	public void testDeleteOne() {
		Token tokenToInsert = mockToken();
		repo.insertOne(tokenToInsert);

		Token tokenSelected = repo.selectOne(tokenToInsert.getTokenValue());

		Assert.assertThat(tokenSelected, Matchers.notNullValue());

		int ret = repo.deleteOne(tokenSelected.getTokenValue());
		Assert.assertThat(ret, Matchers.greaterThan(0));
	}

	@Test
	@Transactional
	@Rollback
	public void testDeleteExpiredInBatch() {
		int size = prepareTokens();
		long totalSizeBefore = repo.selectSize();
		DateTime now = new DateTime(new Date());
		DateTime twentyOneMinutesAgo = now.minusMinutes(22);
		DateTime twentyMinutesAgo = now.minusMinutes(20);

		int pageSize = 10;
		
		Assert.assertThat(size, Matchers.equalTo(300));
		Assert.assertThat(totalSizeBefore, Matchers.equalTo(300L));

		PageHelper.startPage(2, pageSize);
		List<Token> tokensNeedUpdateTime = repo.selectPage();
		for (Token t : tokensNeedUpdateTime) {
			Token tNew = new Token();
			tNew.setTokenValue(t.getTokenValue());
			tNew.setLastModified(twentyOneMinutesAgo.toDate());

			repo.updateOne(tNew);
		}

		int ret = repo.deleteExpiredInBatch(twentyMinutesAgo.toDate());
		Assert.assertThat(ret, Matchers.equalTo(pageSize));

		long totalSize = repo.selectSize();
		Assert.assertThat(totalSize, Matchers.equalTo(Long.valueOf(size - pageSize)));

	}

	private Token mockToken() {
		Token t = new Token();
		t.setActive(true);
		t.setLastModified(new Date());
		t.setPassword("123456");
		t.setTerminalIp("10.0.0.1");
		t.setTokenValue(String.valueOf(System.nanoTime()));
		t.setUsername("175994");

		return t;
	}

	private int prepareTokens() {
		List<Token> tokensToInsert = new LinkedList<Token>();
		int maxSize = 300;

		for (int i = 0; i < 300; i++) {
			tokensToInsert.add(mockToken());
		}

		int ret = repo.insertBatch(tokensToInsert);

		Assert.assertThat(ret, Matchers.equalTo(maxSize));

		return ret;
	}

}
