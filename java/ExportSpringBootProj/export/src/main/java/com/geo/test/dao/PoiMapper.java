package com.geo.test.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.geo.test.entity.Poi;

@Repository //dao层接口  
@org.apache.ibatis.annotations.Mapper
public interface PoiMapper {

	public List<Poi> findList(Poi poi);
	
	public int insertSelective(Poi poi);
	
	public int deleteByKey(String id);
	
	public int updateByKey(Poi poi);
	
	public Poi findByKey(String id);
	
	public int deleteInBatch(List<String> ids);
	
}