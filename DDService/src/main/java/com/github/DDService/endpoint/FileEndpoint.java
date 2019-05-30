package com.github.DDService.endpoint;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.DDService.service.FileService;
import com.github.DDService.tools.DDHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class FileEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileEndpoint.class);
    
    @Autowired
    private FileService fileService;

    @RequestMapping(value="/upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request,@RequestParam(value = "path", required = false) String path){
    	Map<String, Object> result=new HashMap<>();
    	result.put("status", 0);
    	MultipartFile[] files=null;
    	//钉钉上传文件时文件获取代码。
		MultipartHttpServletRequest temprequest = (MultipartHttpServletRequest) request;
		if(temprequest!=null) {
			List<MultipartFile> teMultipartFiles = new ArrayList<>();
			Iterator<String> fileNames = temprequest.getFileNames();
			while(fileNames.hasNext()){
				MultipartFile file = temprequest.getFile(fileNames.next());
				if(file !=null && !file.isEmpty()) {
					teMultipartFiles.add(file);
				}
			}
			if(teMultipartFiles.size()>0) {
				files = new MultipartFile[teMultipartFiles.size()];
				for(int i=0;i<teMultipartFiles.size();i++) {
					files[i] = teMultipartFiles.get(i);
				}
			}
			fileService.saveFiles(files, path);
		}
		List<Object> rs = fileService.getFiles(path);
		result.put("status", 1);
		result.put("data", rs);
		return result;
    }
    
    @RequestMapping(value="/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request,@RequestParam(value = "path", required = false) String path){
    	Map<String, Object> result=new HashMap<>();
    	if(path == null || path == "") {
    		result.put("status", 0);
    	}else {    		
    		List<Object> rs = fileService.getFiles(path);
    		result.put("status", 1);
    		result.put("data", rs);
    	}
		return result;
    }
    
    @RequestMapping(value="/delete")
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request,@RequestParam(value = "file", required = false) String file){
    	Map<String, Object> result=new HashMap<>();
    	if(file == null || file == "") {
    		result.put("status", 0);
    	}else {    		
    		boolean b = fileService.deleteFile(file);
    		String path = new File(file).getParent();
    		List<Object> rs = fileService.getFiles(path);
    		result.put("status", b?1:2);
    		result.put("data", rs);
    	}
		return result;
    }
}