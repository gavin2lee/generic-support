package com.generic.support;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowsFileOperations {
	private static final Logger log = LoggerFactory.getLogger(WindowsFileOperations.class);

	@Test
	public void testListDisk(){
		String baseDir = "D";
		log.debug("list all children files under "+baseDir);
		File dir = new File(baseDir);
		for(File childF : dir.listFiles()){
			log.debug(childF.getName());
		}
	}

}
