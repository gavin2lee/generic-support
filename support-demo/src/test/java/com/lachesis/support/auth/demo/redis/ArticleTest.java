package com.lachesis.support.auth.demo.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ArticleTest {
	static final int TEN_THOUSAND = 1000 * 10;

	static final int MAX_ROUND_IN_BATCH_TEST = TEN_THOUSAND * 5;
	String host = "192.168.0.105";
	int port = 6379;
	// Jedis jedis = new Jedis(host, port);

	JedisPool jedisPool = new JedisPool(host, port);

	String articleIdKey = "articleIdGen";
	String peopleIdKey = "peopleIdGen";
	String peopleSetKey = "peopleSet";

	String articleTimeKey = "articleTime";

	String articleSetKey = "articleSet";

	Jedis jedis = null;

	@Before
	public void setUp() {
		jedis = jedisPool.getResource();
	}

	@After
	public void tearDown() {
		if (jedis != null) {
			jedis.close();
		}
	}

	@Ignore
	@Test
	public void testGetId() {
		int threadSize = 10;
		ExecutorService executor = Executors.newFixedThreadPool(100);
		for (int i = 0; i < threadSize; i++) {
			final int label = i;
			executor.execute(new Runnable() {
				Jedis jedisLocal = jedisPool.getResource();

				@Override
				public void run() {
					try {
						for (int j = 0; j < 10; j++) {
							Long aId = jedisLocal.incr(articleIdKey);

							System.out.println(label + " >>>  " + aId);
						}
					} finally {
						jedisLocal.close();
					}
				}
			});
		}

		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {

		} finally {
			executor.shutdown();
		}
	}

	@Test
	public void testPostArticle() {

		//Jedis jedis = jedisPool.getResource();

		try {

			postArticleSingle();

		} finally {

			//jedis.close();
		}
	}
	
	@Test
	public void testPostArticleInBatch(){
		String batchTests = System.getProperty("batchTests");

		int maxRound = 0;
		if (batchTests != null) {
			if (batchTests.trim().length() > 0) {
				try {
					maxRound = Integer.parseInt(batchTests.trim());
				} catch (Exception e) {
					maxRound = MAX_ROUND_IN_BATCH_TEST;
				}
			} else {
				maxRound = MAX_ROUND_IN_BATCH_TEST;
			}
		}
		
		for(int i=0; i<maxRound; i++){
			postArticleSingle();
		}
	}
	
	private void postArticleSingle(){
		long aId = jedis.incr(articleIdKey);
		long pId = jedis.incr(peopleIdKey);

		People p = mockPeople();
		p.setId(pId);

		Article a = mockArticle();
		a.setId(aId);
		a.setPoster(p);

		String peopleKey = People.class.getSimpleName() + ":" + p.getId();
		String articleKey = Article.class.getSimpleName() + ":" + a.getId();

		jedis.zadd(articleTimeKey, System.currentTimeMillis(), articleKey);
		jedis.sadd(peopleSetKey, peopleKey);
		jedis.sadd(articleSetKey, articleKey);

		Map<String, String> peopleAttrs = new HashMap<String, String>();
		peopleAttrs.put("id", String.valueOf(p.getId()));
		peopleAttrs.put("name", p.getName());

		jedis.hmset(peopleKey, peopleAttrs);

		Map<String, String> articleAttrs = new HashMap<String, String>();
		articleAttrs.put("id", String.valueOf(a.getId()));
		articleAttrs.put("title", a.getTitle());
		articleAttrs.put("link", a.getLink());
		articleAttrs.put("pubAt", DateTimeFormat.fullDateTime().print(new DateTime(a.getPubAt())));
		articleAttrs.put("poster", peopleKey);
		articleAttrs.put("votes", String.valueOf(1));

		jedis.hmset(articleKey, articleAttrs);

		System.out.println(peopleKey);
		System.out.println(articleKey);
	}

	private Article mockArticle() {
		Article a = new Article();
		a.setTitle("Article-" + System.nanoTime());
		a.setLink("http://articles?title=" + a.getTitle());
		a.setPubAt(new Date());
		a.setVotes(0);

		return a;
	}

	private People mockPeople() {
		People p = new People();
		p.setName("People-" + System.nanoTime());

		return p;
	}

}
