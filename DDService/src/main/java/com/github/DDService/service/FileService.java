package com.github.DDService.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.DDService.entity.FileInfo;
import com.github.DDService.model.FTPParameter;
import com.github.DDService.tools.FTPHelper;


@Service
public class FileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private FTPParameter ftpParameter;
	
	@Autowired
	private FTPHelper ftpHelper;
	
	public int saveFiles(MultipartFile[] files,String path) {
		if(files != null && files.length>0) {
			String rootPath =ftpParameter.getPath();
			if(rootPath == null ||rootPath=="") {
				throw new RuntimeException("没有设置文件地址");
			}
			File dir = new File(rootPath+"//"+path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			int num = 0;
			for (MultipartFile file : files) {
				String relativepath = path+"//"+file.getName();
				String filepath = rootPath+"//"+relativepath;
				boolean ftpb = false;
				try {
					ftpb = ftpHelper.upload(relativepath, file.getInputStream());
					num++;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(!ftpb) {	
					LOGGER.debug("ftp未保存上，使用一般方法保存文件。");
					try {
						file.transferTo(new File(filepath));
						num++;
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return num;
		}
		return 0;
	}
	
	public List<Object> getFiles(String path) {
		String rootPath =ftpParameter.getPath();
		if(rootPath == null ||rootPath=="") {
			throw new RuntimeException("没有设置文件地址");
		}
		
		List<Object> files = new ArrayList<>();
	    File file = new File(rootPath+"//"+path);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	        	FileInfo fileInfo = new FileInfo();
	        	fileInfo.setName(tempList[i].getName());
	        	fileInfo.setPath(path+"/"+tempList[i].getName());
	        	fileInfo.setUploadtime(new Timestamp(tempList[i].lastModified()));
	            files.add(fileInfo);
	        }
	    }
	    return files;
	}
	
	public boolean deleteFile(String file) {
		String rootPath =ftpParameter.getPath();
		if(rootPath == null ||rootPath=="") {
			throw new RuntimeException("没有设置文件地址");
		}
	    File temp = new File(rootPath+"//"+file);
	    if(temp.exists()) {
	    	return temp.delete();
	    }
	    return false;
	}
}
