package com.geo.test.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.geo.test.entity.Community;

@Repository //dao层接口  
@org.apache.ibatis.annotations.Mapper
public interface CommunityMapper {

	public List<Community> findList(Community community);
	
	public int insertSelective(Community community);
	
	public int deleteByKey(String id);
	
	public int updateByKey(Community community);
	
	public Community findByKey(String id);
	
	public int deleteInBatch(List<String> ids);
	
}