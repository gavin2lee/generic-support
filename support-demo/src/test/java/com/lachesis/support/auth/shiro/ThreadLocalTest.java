package com.lachesis.support.auth.shiro;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.demo.vo.Greeting;

public class ThreadLocalTest {
	private static final Logger LOG = LoggerFactory.getLogger(ThreadLocalTest.class);

	@Test
	public void testThreadLocal() {
		ThreadLocal<Greeting> greetings = new ThreadLocal<Greeting>();

		Greeting g = new Greeting();
		g.setId(String.valueOf(1));

		greetings.set(g);

		Greeting g1 = greetings.get();

		LOG.debug(g1.getId());

		Greeting g2 = greetings.get();

		LOG.debug(g2.getId());
	}

	@Test
	public void testMultiThreadLocal() {
		final ThreadLocal<Greeting> greetings = new ThreadLocal<Greeting>();

		Greeting g = new Greeting();
		g.setId(String.valueOf(1));
		
		greetings.set(g);

		LOG.debug(Thread.currentThread().toString() + ":" + greetings.get().getId());
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Greeting g1 = greetings.get();
				
				Assert.assertThat(g1, Matchers.nullValue());
				if (g1 == null) {
					LOG.debug(Thread.currentThread().toString());
				} else {
					LOG.debug(Thread.currentThread().toString() + ":" + g1.getId());
				}
			}

		});
		
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e1) {
			LOG.error("",e1);
		}

		
	}
}
