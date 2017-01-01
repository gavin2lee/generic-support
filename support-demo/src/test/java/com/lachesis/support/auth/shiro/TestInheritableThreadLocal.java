package com.lachesis.support.auth.shiro;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestInheritableThreadLocal {
	static final Logger LOG = LoggerFactory.getLogger(TestInheritableThreadLocal.class);
	@Test
	public void testThreadLocal() {  
        final ThreadLocal<String> local = new ThreadLocal<String>();  
        work(local);  
    }  
      
	@Test
    public void testInheritableThreadLocal() {  
        final ThreadLocal<String> local = new InheritableThreadLocal<String>();  
        work(local);  
    }  
      
    private void work(final ThreadLocal<String> local) {  
        local.set("a");  
        LOG.debug(Thread.currentThread() + "," + local.get());  
        Thread t = new Thread(new Runnable() {  
              
            @Override  
            public void run() {  
            	LOG.debug(Thread.currentThread() + "," + local.get());  
                local.set("b");  
                LOG.debug(Thread.currentThread() + "," + local.get());  
            }  
        });  
          
        t.start();  
        try {  
            t.join();  
        } catch (InterruptedException e) {  
        	Assert.fail(); 
        }  
          
        LOG.debug(Thread.currentThread() + "," + local.get());  
    } 
}
