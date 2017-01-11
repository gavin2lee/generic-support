package com.generic.support.netio.bean;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class SentenceGenerator {
	private String[] sentences;
	private String stripChars = "\n \t";
	
	private String poemFilename = "changhenge-poem.txt";
	public SentenceGenerator(){
		init();
	}
	
	
	public String generate(){
		Random r = new Random();
		int index = r.nextInt(sentences.length);
		return StringUtils.strip(sentences[index], stripChars);
	}
	
	protected void init(){
		String path = SentenceGenerator.class.getClassLoader().getResource(poemFilename).getPath();
		try {
			List<String> lines = FileUtils.readLines(new File(path), "UTF-8");
			String[] buf = new String[lines.size()];
			sentences = lines.toArray(buf);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
