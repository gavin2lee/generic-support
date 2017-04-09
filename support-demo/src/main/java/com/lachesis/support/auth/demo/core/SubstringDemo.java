package com.lachesis.support.auth.demo.core;

public class SubstringDemo {

	public static void main(String[] args) {
		String rt = "/home/gavin/Dev/Books";
		String s = "/home/gavin/Dev/Books/CAS的";
		String part = "/home/gavin/Dev";

		String midPart = s.substring(rt.lastIndexOf("/")+1, rt.length());
		System.out.println(s.substring(s.indexOf(midPart)));
	}

}
