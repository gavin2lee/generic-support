package com.lachesis.support.common.util.crypt.bootstrap;

import java.io.PrintStream;

import com.lachesis.support.common.util.crypt.CryptUtils;

public class ConfigCryptTool {

	public static void main(String[] args) {
		String content = null;
		boolean encrypt = true;
		if (args.length == 1) {
			content = args[0];
			printUsageIfHelp(args[0]);
		} else if (args.length == 2) {
			if (!"-d".equalsIgnoreCase(args[0])) {
				printUsage(System.out);
				System.exit(-1);
			}
			content = args[1];
			encrypt = false;
		} else {
			printUsage(System.out);
			System.exit(-1);
		}

		String result = "";
		try {
			if (encrypt) {
				result = CryptUtils.encrypt(content);
			} else {
				result = CryptUtils.decrypt(content);
			}
			
			printResult(result);
		} catch (Exception e) {
			printResult(e.getMessage(), System.err);
		}
	}
	
	private static void printUsageIfHelp(String cmd){
		if("-h".equalsIgnoreCase(cmd) || "--help".equalsIgnoreCase(cmd)){
			printUsage(System.out);
			System.exit(-1);
		}
	}

	private static void printResult(String s) {
		printResult(s, null);
	}

	private static void printResult(String s, PrintStream ps) {
		if (ps == null) {
			ps = System.out;
		}
		ps.println(s);
	}

	private static void printUsage(PrintStream ps) {
		String msg = "Usage:\n";
		msg += "configtool <text>\n";
		msg += "configtool -d <text>\n";
		msg += "java com.lachesis.support.common.util.crypt.bootstrap.ConfigCryptTool <plaintext>\n";
		msg += "java com.lachesis.support.common.util.crypt.bootstrap.ConfigCryptTool -d <ciphertext>";

		ps.println(msg);
	}

}
