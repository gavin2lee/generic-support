package com.generic.support;

import java.io.File;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class  WindowsFileOperationsTest{
	private static final Logger log = LoggerFactory.getLogger(WindowsFileOperationsTest.class);

	@Test
	public void testListDisk(){
		String baseDir = "D:\\";
		String os = System.getProperty("os.name");
		log.debug("OS:"+os);
		if(!os.startsWith("Windows")){
		    log.debug(WindowsFileOperationsTest.class.getName() + " test skipped");
		    return;
		}
		log.debug("list all children files under "+baseDir);
		File dir = new File(baseDir);
		Assert.assertThat(dir, Matchers.notNullValue());
		for(File childF : dir.listFiles()){
			log.debug(childF.getName());
		}
	}

}
