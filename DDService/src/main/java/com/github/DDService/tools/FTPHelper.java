package com.github.DDService.tools;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.DDService.model.FTPParameter;
import com.github.DDService.service.FileService;

@Component
public class FTPHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	private static String LOCAL_CHARSET = "GBK";
	
	@Autowired
	private FTPParameter ftpParameter;

	public FTPClient connect() {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpParameter.getHost(), Integer.parseInt(ftpParameter.getPort().trim()));
			boolean isLogin = ftpClient.login(ftpParameter.getUser(), ftpParameter.getPassword());
			if (isLogin) {
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.setControlEncoding(LOCAL_CHARSET);
				ftpClient.setBufferSize(4096);
				int replyCode = ftpClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(replyCode)) {
					ftpClient.logout();
					ftpClient.disconnect();
					ftpClient = null;
				}
			} else {
				ftpClient.disconnect();
				ftpClient = null;
			}
		} catch (Exception e) {
			close(ftpClient);
			LOGGER.error("Failed to connect to server! has exception: "+ e.getMessage());
		}
		return ftpClient;
	}

	public void close(FTPClient ftpClient) {
		if (ftpClient != null) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (Exception e) {
				LOGGER.error("close ftp connect has :" + e.getMessage());
			}
		}
	}
	
	public boolean upload(String remotePath, InputStream source) {
		if (remotePath.length() == 0) {
			LOGGER.error("the remotePath is empty! [remotePath:" + remotePath + "]");
		}
		FTPClient ftpClient = connect();
		if(ftpClient==null) {
			return false;
		}
		ftpClient.enterLocalPassiveMode();
		try {
			String path = "";
			String name = remotePath;
			if (remotePath.indexOf("/") >= 0) {
				path = remotePath.substring(0, remotePath.lastIndexOf("/"));
				name = remotePath.substring(remotePath.lastIndexOf("/") + 1);
			}
			createDirectory(ftpClient, path);
			boolean b = ftpClient.storeFile(new String(name.getBytes(LOCAL_CHARSET), "iso-8859-1"),source);
			if (b) {				
				LOGGER.info(path + "," + name + ",add");
				return true;
			}
			else {				
				throw new RuntimeException( "failed to upload file! [remotePath:" + remotePath + "]");
			}
		} catch (Exception e) {
			LOGGER.error("upload file service has exception:"+ e.getMessage());
			return false;
		}finally {
			close(ftpClient);	
		}
	}
	
	public Boolean createDirectory(FTPClient ftpClient, String remotePath) {
		String[] pathArray = remotePath.split("/");
		for (int i = 0; i < pathArray.length; i++) {
			try {
				LOGGER.debug("createDirectory split remotePath elements is :"
								+ pathArray[i]);
				if (!ftpClient.changeWorkingDirectory(new String(pathArray[i].getBytes(LOCAL_CHARSET),"iso-8859-1"))) {
					if(ftpClient.makeDirectory(new String(pathArray[i].getBytes(LOCAL_CHARSET),"iso-8859-1"))){
						ftpClient.changeWorkingDirectory(pathArray[i]);
					};
				}
			} catch (IOException e) {
				LOGGER.error("createDirectory service has exception:"+ e.getMessage());
				return false;
			} 
		}
		return true;
	}
}
