package com.geo.test.endpoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.geo.test.entity.Poi;
import com.geo.test.service.PoiService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController // 控制器接口
@RequestMapping("/api/poi")
@CrossOrigin
public class PoiController{

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Poi.class);

	@Autowired
	private PoiService poiService;

	protected PoiService getService() {
		return poiService;
	}

	/**
	 * 获取List
	 * 
	 * @param 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> findList(@RequestBody Poi item) throws SQLException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Poi> list = new ArrayList<Poi>();
		PageHelper.startPage(item.getPageNum(), item.getPageSize());
		list = poiService.findList(item);

		PageInfo<Poi> pageInfo = new PageInfo<Poi>(list);
		map.put("code", 0);
		map.put("msg", "ok");
		map.put("count", pageInfo.getTotal());
		map.put("data", pageInfo.getList());
		return map;
	}
		/**
	 * 获取所有
	 * 
	 * @param 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<Poi> findAll(@RequestBody Poi item) throws SQLException, IOException {

		List<Poi> list = new ArrayList<Poi>();

		list = poiService.findList(item);

		return list;
	}
	
	/**
	 * 新增
	 * 
	 * @param mp
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public String add(@RequestBody Poi item) throws SQLException, IOException {
		String id = UUID.randomUUID().toString();
		item.setId(id);
		poiService.insertSelective(item);
		return item.getId();
	}
	
	/**
	 * 新增多个
	 * 
	 * @param mp
	 * @return
	 */
	@RequestMapping(value = "/addItems", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<Poi> addItems(@RequestBody List<Poi> items) throws SQLException, IOException {
		if(items!=null){
			for(Poi item : items){
				String id = UUID.randomUUID().toString();
				item.setId(id);
				poiService.insertSelective(item);
			}
		}
		return items;
	}

	/**
	 * 根据id获取对象
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/findByKey", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public Poi findByKey(String id) throws SQLException, IOException {
		Poi item = poiService.findByKey(id);
		return item;
	}

	/**
	 * 编辑
	 * 
	 * @param mp
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Boolean update(@RequestBody Poi item) throws SQLException, IOException {
		try {
			poiService.updateByKey(item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return 影响行数
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public int deletes(@RequestBody String[] ids) throws SQLException, IOException {
		List<String> idsList = Arrays.asList(ids);
		try {
			int cont = poiService.deleteInBatch(idsList);
			return cont;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
