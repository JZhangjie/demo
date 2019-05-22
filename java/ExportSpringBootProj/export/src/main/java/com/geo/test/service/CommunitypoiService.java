package com.geo.test.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geo.test.dao.CommunitypoiMapper;
import com.geo.test.entity.Communitypoi;

@Service
public class CommunitypoiService{

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunitypoiService.class);
	@Autowired
	private CommunitypoiMapper communitypoiMapper;

	protected CommunitypoiMapper getMapper() {
		return communitypoiMapper;
	}
	
	public List<Communitypoi> findList(Communitypoi communitypoi){
		return communitypoiMapper.findList(communitypoi);
	}
	
	public int insertSelective(Communitypoi communitypoi){
		return communitypoiMapper.insertSelective(communitypoi);
	}
	
	public int deleteByKey(String id){
		return communitypoiMapper.deleteByKey(id);
	}
	
	public int updateByKey(Communitypoi communitypoi){
		return communitypoiMapper.updateByKey(communitypoi);
	}
	
	public Communitypoi findByKey(String id){
		return communitypoiMapper.findByKey(id);
	}
	
	public int deleteInBatch(List<String> ids){
		return communitypoiMapper.deleteInBatch(ids);
	}
}