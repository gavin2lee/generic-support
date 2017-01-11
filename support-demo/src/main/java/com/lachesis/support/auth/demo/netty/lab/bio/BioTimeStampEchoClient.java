package com.lachesis.support.auth.demo.netty.lab.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.demo.netty.lab.AbstractIOClient;
import com.lachesis.support.auth.demo.netty.lab.IOServer;
import com.lachesis.support.auth.demo.netty.lab.SentenceGenerator;

public class BioTimeStampEchoClient extends AbstractIOClient {
	private static int DEFAULT_TIMES = 1;
	private static final Logger log = LoggerFactory.getLogger(BioTimeStampEchoClient.class);
	private int times;
	private String host;
	private int port;

	private SentenceGenerator sentenceGenerator = new SentenceGenerator();

	public BioTimeStampEchoClient(String host, int port, int times) {
		this.host = host;
		this.port = port;
		this.times = times;
	}

	public BioTimeStampEchoClient(String host, int port) {
		this(host, port, DEFAULT_TIMES);
	}

	@Override
	public void kickoff() throws Exception {
		Socket client = new Socket(host, port);

		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

		PrintStream print = new PrintStream(client.getOutputStream());
		try {
			for (int i = 0; i < times; i++) {
				String line = sentenceGenerator.generate();
				print.println(line);
				String revLine = reader.readLine();
				log.debug(revLine + "  > "+(i+1));
			}
		} finally {
			print.println(IOServer.CLOSE_SIG);
			print.flush();

			reader.close();
			print.close();
			client.close();
		}

	}

	

}
