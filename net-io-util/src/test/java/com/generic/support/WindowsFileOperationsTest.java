package com.generic.support;

import java.io.File;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WindowsFileOperationsTest {
	private static final Logger log = LoggerFactory.getLogger(WindowsFileOperationsTest.class);

	@Test
	public void testListDisk(){
		String baseDir = "D";
		log.debug("list all children files under "+baseDir);
		File dir = new File(baseDir);
		Assert.assertThat(dir, Matchers.notNullValue());
		for(File childF : dir.listFiles()){
			log.debug(childF.getName());
		}
	}

}
