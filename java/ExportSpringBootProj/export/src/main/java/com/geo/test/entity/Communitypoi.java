package com.geo.test.entity;

import java.io.Serializable;

public class Communitypoi implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private double gps_x;
	private double gps_y;
	private String type;
	private double distance;
	private double xqid;
	private String projectid;
	private String searchstr;
	private String sort;
	private int pageSize;
	private int pageNum;
        
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getGps_x() {
		return gps_x;
	}
	public void setGps_x(double gps_x) {
		this.gps_x = gps_x;
	}
	public double getGps_y() {
		return gps_y;
	}
	public void setGps_y(double gps_y) {
		this.gps_y = gps_y;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getXqid() {
		return xqid;
	}
	public void setXqid(double xqid) {
		this.xqid = xqid;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
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
	public String getSearchstr() {
		return this.searchstr;
	}
	public int getPageSize() {
		return this.pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return this.pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
