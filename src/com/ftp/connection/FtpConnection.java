package com.ftp.connection;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FTPClient _ftp_client = new FTPClient();
		try {
			_ftp_client.connect("192.168.88.100", 2021);
			System.out.println(_ftp_client.login("ftpyst", "ftp123$%^yst"));
			
			FTPFile [] _ftp_files = _ftp_client.listFiles("/");
			System.out.println(_ftp_files.length);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
