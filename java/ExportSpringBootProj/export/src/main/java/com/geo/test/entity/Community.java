package com.geo.test.entity;

import java.io.Serializable;

public class Community implements Serializable {

	private static final long serialVersionUID = 1L;
	private double fid__1;
	private double longitude;
	private double latitude;
	private String searchstr;
	private String sort;
	private int pageSize;
	private int pageNum;
        
	public double getFid__1() {
		return fid__1;
	}
	public void setFid__1(double fid__1) {
		this.fid__1 = fid__1;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
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
