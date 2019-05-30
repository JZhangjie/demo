package com.github.DDService.entity;

import java.io.Serializable;

import com.github.rogerli.framework.model.BaseModel;

public class POIEntity extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String typename;
	private String typelevel1;
	private String typelevel2;
	private String name;
	private String jzlb;
	private String jblb;
	private String zylx;
	private String gylb;
	private String gyxt;
	private String cclb;
	private String syzk;
	private boolean sfjg;
	private String parentid;
	private String lng;
	private String lat;
	private String locationname;
	private String addr;
	private String creatername;
	private String createtime;
	private String mobile;
	private String attachmentpath;
	private String searchstr;
	private String sort;
        
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypelevel1() {
		return typelevel1;
	}
	public void setTypelevel1(String typelevel1) {
		this.typelevel1 = typelevel1;
	}
	public String getTypelevel2() {
		return typelevel2;
	}
	public void setTypelevel2(String typelevel2) {
		this.typelevel2 = typelevel2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJzlb() {
		return jzlb;
	}
	public void setJzlb(String jzlb) {
		this.jzlb = jzlb;
	}
	public String getJblb() {
		return jblb;
	}
	public void setJblb(String jblb) {
		this.jblb = jblb;
	}
	public String getZylx() {
		return zylx;
	}
	public void setZylx(String zylx) {
		this.zylx = zylx;
	}
	public String getGylb() {
		return gylb;
	}
	public void setGylb(String gylb) {
		this.gylb = gylb;
	}
	public String getGyxt() {
		return gyxt;
	}
	public void setGyxt(String gyxt) {
		this.gyxt = gyxt;
	}
	public String getCclb() {
		return cclb;
	}
	public void setCclb(String cclb) {
		this.cclb = cclb;
	}
	public String getSyzk() {
		return syzk;
	}
	public void setSyzk(String syzk) {
		this.syzk = syzk;
	}
	public boolean getSfjg() {
		return sfjg;
	}
	public void setSfjg(boolean sfjg) {
		this.sfjg = sfjg;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLocationname() {
		return locationname;
	}
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCreatername() {
		return creatername;
	}
	public void setCreatername(String creatername) {
		this.creatername = creatername;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAttachmentpath() {
		return attachmentpath;
	}
	public void setAttachmentpath(String attachmentpath) {
		this.attachmentpath = attachmentpath;
	}
	public String getSearchstr() {
		return this.searchstr;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSort() {
		return this.sort;
	}
	public void setSearchstr(String searchstr) {
		this.searchstr = searchstr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
