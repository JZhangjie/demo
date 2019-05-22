package com.geo.test.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geo.test.dao.PoiMapper;
import com.geo.test.entity.Poi;

@Service
public class PoiService{

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(PoiService.class);
	@Autowired
	private PoiMapper poiMapper;

	protected PoiMapper getMapper() {
		return poiMapper;
	}
	
	public List<Poi> findList(Poi poi){
		return poiMapper.findList(poi);
	}
	
	public int insertSelective(Poi poi){
		return poiMapper.insertSelective(poi);
	}
	
	public int deleteByKey(String id){
		return poiMapper.deleteByKey(id);
	}
	
	public int updateByKey(Poi poi){
		return poiMapper.updateByKey(poi);
	}
	
	public Poi findByKey(String id){
		return poiMapper.findByKey(id);
	}
	
	public int deleteInBatch(List<String> ids){
		return poiMapper.deleteInBatch(ids);
	}
}