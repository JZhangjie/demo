package com.github.DDService.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.DDService.entity.POIEntity;
import com.github.rogerli.framework.dao.Mapper;

@Repository //dao层接口  
@org.apache.ibatis.annotations.Mapper
public interface POIEntityMapper extends Mapper<POIEntity, String> {
	}