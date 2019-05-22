package com.geo.test.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.geo.test.entity.Communitypoi;

@Repository //dao层接口  
@org.apache.ibatis.annotations.Mapper
public interface CommunitypoiMapper {

	public List<Communitypoi> findList(Communitypoi communitypoi);
	
	public int insertSelective(Communitypoi communitypoi);
	
	public int deleteByKey(String id);
	
	public int updateByKey(Communitypoi communitypoi);
	
	public Communitypoi findByKey(String id);
	
	public int deleteInBatch(List<String> ids);
	
}