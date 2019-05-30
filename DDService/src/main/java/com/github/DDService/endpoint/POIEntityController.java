package com.github.DDService.endpoint;

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

import com.github.DDService.entity.POIEntity;
import com.github.DDService.service.POIEntityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.rogerli.framework.service.Service;
import com.github.rogerli.framework.web.AbstractRestfulController;

@RestController // 控制器接口
@RequestMapping("/api/poientity")
@CrossOrigin
public class POIEntityController extends AbstractRestfulController<POIEntity, String> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(POIEntity.class);

	@Autowired
	private POIEntityService poientityService;

	@Override
	protected Service getService() {
		return poientityService;
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
	public Map<String, Object> findList(@RequestBody POIEntity item) throws SQLException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<POIEntity> list = new ArrayList<POIEntity>();
		PageHelper.startPage(item.getPageNum(), item.getPageSize());
		list = poientityService.findList(item);

		PageInfo<POIEntity> pageInfo = new PageInfo<POIEntity>(list);
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
	public List<POIEntity> findAll(@RequestBody POIEntity item) throws SQLException, IOException {

		List<POIEntity> list = new ArrayList<POIEntity>();

		list = poientityService.findList(item);

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
	public String add(@RequestBody POIEntity item) throws SQLException, IOException {
		String id = UUID.randomUUID().toString();
		item.setId(id);
		poientityService.insertSelective(item);
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
	public List<POIEntity> addItems(@RequestBody List<POIEntity> items) throws SQLException, IOException {
		if(items!=null){
			for(POIEntity item : items){
				String id = UUID.randomUUID().toString();
				item.setId(id);
				poientityService.insertSelective(item);
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
	public POIEntity findByKey(String id) throws SQLException, IOException {
		POIEntity item = poientityService.findByKey(id);
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
	public Boolean update(@RequestBody POIEntity item) throws SQLException, IOException {
		try {
			poientityService.updateByKey(item);
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
			int cont = poientityService.deleteInBatch(idsList);
			return cont;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
