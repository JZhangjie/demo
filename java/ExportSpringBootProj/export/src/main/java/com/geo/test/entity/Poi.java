package com.geo.test.entity;

import java.io.Serializable;

public class Poi implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private double gps_x;
	private double gps_y;
	private String searchstr;
	private String sort;
	private int pageSize;
	private int pageNum;
        
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
