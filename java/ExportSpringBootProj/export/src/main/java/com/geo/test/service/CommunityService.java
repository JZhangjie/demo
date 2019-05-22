package com.geo.test.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geo.test.dao.CommunityMapper;
import com.geo.test.entity.Community;

@Service
public class CommunityService{

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunityService.class);
	@Autowired
	private CommunityMapper communityMapper;

	protected CommunityMapper getMapper() {
		return communityMapper;
	}
	
	public List<Community> findList(Community community){
		return communityMapper.findList(community);
	}
	
	public int insertSelective(Community community){
		return communityMapper.insertSelective(community);
	}
	
	public int deleteByKey(String id){
		return communityMapper.deleteByKey(id);
	}
	
	public int updateByKey(Community community){
		return communityMapper.updateByKey(community);
	}
	
	public Community findByKey(String id){
		return communityMapper.findByKey(id);
	}
	
	public int deleteInBatch(List<String> ids){
		return communityMapper.deleteInBatch(ids);
	}
}