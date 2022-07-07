package com.smartinsight.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author wlmz
 *
 */
/**
 * @author wlmz
 *
 */
@TableName("full_text")
public class FullText implements Serializable{
	
	@TableId(value = "id")
	private int id;
	
	private String name;
	
	private String nameType;
	
	private String claimClinicMap;
	
	private String claimClinicMapChina;
	
	private String claimProvinceMap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	public String getClaimClinicMap() {
		return claimClinicMap;
	}

	public void setClaimClinicMap(String claimClinicMap) {
		this.claimClinicMap = claimClinicMap;
	}

	public String getClaimClinicMapChina() {
		return claimClinicMapChina;
	}

	public void setClaimClinicMapChina(String claimClinicMapChina) {
		this.claimClinicMapChina = claimClinicMapChina;
	}

	public String getClaimProvinceMap() {
		return claimProvinceMap;
	}

	public void setClaimProvinceMap(String claimProvinceMap) {
		this.claimProvinceMap = claimProvinceMap;
	}

	@Override
	public String toString() {
		return "FullText [id=" + id + ", name=" + name + ", nameType=" + nameType + ", claimClinicMap=" + claimClinicMap
				+ ", claimClinicMapChina=" + claimClinicMapChina + ", claimProvinceMap=" + claimProvinceMap + "]";
	}

	public FullText(int id, String name, String nameType, String claimClinicMap, String claimClinicMapChina,
			String claimProvinceMap) {
		super();
		this.id = id;
		this.name = name;
		this.nameType = nameType;
		this.claimClinicMap = claimClinicMap;
		this.claimClinicMapChina = claimClinicMapChina;
		this.claimProvinceMap = claimProvinceMap;
	}

	public FullText() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
