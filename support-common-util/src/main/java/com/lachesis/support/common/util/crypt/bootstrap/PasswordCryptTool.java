package com.lachesis.support.common.util.crypt.bootstrap;

import java.io.PrintStream;

import com.lachesis.support.common.util.crypt.CryptUtils;
import com.lachesis.support.common.util.exception.CryptException;

public class PasswordCryptTool {

	public static void main(String[] args) {
		if(args.length < 1){
			printUsage(System.out);
			System.exit(-1);
		}
		
		String plaintext = args[0];
		
		printUsageIfHelp(args[0]);
		
		try {
			String digestData = CryptUtils.digest(plaintext);
			printResult(digestData);
		} catch (CryptException e) {
			printResult(e.getMessage(),System.err);
		}
	}
	
	private static void printUsageIfHelp(String cmd){
		if("-h".equalsIgnoreCase(cmd) || "--help".equalsIgnoreCase(cmd)){
			printUsage(System.out);
			System.exit(-1);
		}
	}
	
	private static void printUsage(PrintStream ps){
		String msg = "Usage:\n";
		msg += "pwtool <text>\n";
		msg += "java com.lachesis.support.common.util.crypt.bootstrap.PasswordCryptTool <password>";
		
		ps.println(msg);
	}
	
	private  static void printResult(String s){
		printResult(s,null);
	}
	
	private static void printResult(String s, PrintStream ps){
		if(ps == null){
			ps = System.out;
		}
		
		ps.println(s);
	}

}
