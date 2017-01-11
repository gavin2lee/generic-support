package com.generic.support.netio.bean;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class SentenceGenerator {
	private String[] sentences;
	
	private String poemFilename = "changhenge-poem.txt";
	public SentenceGenerator(){
		init();
	}
	
	
	public String generate(){
		Random r = new Random();
		int index = r.nextInt(sentences.length);
		return sentences[index];
	}
	
	protected void init(){
		String path = SentenceGenerator.class.getClassLoader().getResource(poemFilename).getPath();
		try {
			List<String> lines = FileUtils.readLines(new File(path), "UTF-8");
			sentences = (String[]) lines.toArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
