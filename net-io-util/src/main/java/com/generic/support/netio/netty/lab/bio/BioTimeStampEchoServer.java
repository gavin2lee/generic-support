package com.generic.support.netio.netty.lab.bio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.netty.lab.IOServer;

public class BioTimeStampEchoServer implements IOServer {
	private static final Logger log = LoggerFactory.getLogger(BioTimeStampEchoServer.class);
	private AtomicLong count = new AtomicLong();

	public void start(int port) throws Exception {
		ServerSocket server = new ServerSocket(port);

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				log.warn(BioTimeStampEchoServer.class.getSimpleName() + " is going to shutdown.");
			}

		});

		try {
			while (true) {
				Socket s = server.accept();
				try {
					log.debug("receive from "+s.getRemoteSocketAddress().toString() + "  " + count.incrementAndGet());
					socketCallback(s);
				} catch (Exception e) {
					log.error("", e);
				}
			}
		} finally {
			server.close();
		}

	}

	protected void socketCallback(Socket s) throws Exception {
		new Thread(new SocketHandler(s)).start();
	}

	public static class SocketHandler implements Runnable {
		private Socket socket;

		public SocketHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			boolean keepRunning = true;
			while (keepRunning) {
				try {
					keepRunning = process(socket);
				} catch (Exception e) {
					log.error("", e);
					keepRunning = false;
					try {
						socket.close();
					} catch (IOException e1) {
						log.error("", e1);
					}
				}
			}

			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					log.error("", e1);
				}
			}

		}

		private boolean process(Socket s) throws Exception {
			InputStream is = s.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			BufferedReader reader = new BufferedReader(new InputStreamReader(bis));

			PrintStream print = new PrintStream(s.getOutputStream());

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					if (IOServer.CLOSE_SIG.equalsIgnoreCase(line)) {
						return false;
					}
					String timeStrampedLine = String.format("%s - %s - %s", Thread.currentThread().getName(),
							new Date().toString(), line);
					print.println(timeStrampedLine);
				}
			} finally {
				reader.close();
				print.close();
			}

			return true;
		}

	}

}
