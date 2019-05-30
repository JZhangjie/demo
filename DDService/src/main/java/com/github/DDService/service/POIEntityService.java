package com.github.DDService.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.DDService.dao.POIEntityMapper;
import com.github.DDService.entity.POIEntity;
import com.github.rogerli.framework.service.AbstractService;

@Service
public class POIEntityService extends AbstractService<POIEntity, String, POIEntityMapper> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(POIEntityService.class);
	@Autowired
	private POIEntityMapper poientityMapper;
	@Override
	protected POIEntityMapper getMapper() {
		return poientityMapper;
	}
	
}