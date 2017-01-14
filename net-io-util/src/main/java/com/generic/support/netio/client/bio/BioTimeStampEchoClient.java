package com.generic.support.netio.client.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.bean.SentenceGenerator;
import com.generic.support.netio.client.AbstractMultipleIOClient;
import com.generic.support.netio.server.IOServer;

public class BioTimeStampEchoClient extends AbstractMultipleIOClient {
	private static int DEFAULT_TIMES = 1;
	private static final Logger log = LoggerFactory.getLogger(BioTimeStampEchoClient.class);

	String lineSeparator = System.getProperty("line.separator");

	private SentenceGenerator sentenceGenerator = new SentenceGenerator();

	public BioTimeStampEchoClient(String host, int port, int times) {
		super(host,port,times);
	}

	public BioTimeStampEchoClient(String host, int port) {
		this(host, port, DEFAULT_TIMES);
	}

	@Override
	public void kickoff() throws Exception {
		long st = System.currentTimeMillis();
		Socket client = new Socket(getHost(), getPort());

		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

		PrintStream printer = new PrintStream(client.getOutputStream());
		try {
			for (int i = 0; i < getTimes(); i++) {
				String line = sentenceGenerator.generate();
				printer.print(line);
//				printer.println();
				printer.write("\n".getBytes(STRING_ENCODING));
				
				printer.flush();
				log.debug("SEND \t" + i + " >>> " + line);
				String revLine = reader.readLine();
				log.debug("RECV \t" + i + " <<< " + revLine + "  "+(i+1));
			}
		} finally {
			printer.println(IOServer.CLOSE_SIG);
			printer.flush();

			reader.close();
			printer.close();
			client.close();
		}
		
		long end = System.currentTimeMillis();
		log.info("TIME ELAPSE:" + ((end-st)/1000.0) + " seconds");

	}

	protected SentenceGenerator getSentenceGenerator() {
		return sentenceGenerator;
	}

	protected void setSentenceGenerator(SentenceGenerator sentenceGenerator) {
		this.sentenceGenerator = sentenceGenerator;
	}

}
