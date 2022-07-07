package com.smartinsight.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
@TableName("images_fieid")
public class ImagesFieid implements Serializable{
	
	@TableId(value = "id",type = IdType.NONE)
	private long id;
	
	private long clsId;
	
	private String fieldName;
	
	private byte isMoney;
	
	private byte isTable;
	
	private int serialNo;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private Date createdTime;
	
	private Date modifiedTime;
	
	private byte isDel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getClsId() {
		return clsId;
	}

	public void setClsId(long clsId) {
		this.clsId = clsId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public byte getIsMoney() {
		return isMoney;
	}

	public void setIsMoney(byte isMoney) {
		this.isMoney = isMoney;
	}

	public byte getIsTable() {
		return isTable;
	}

	public void setIsTable(byte isTable) {
		this.isTable = isTable;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public byte getIsDel() {
		return isDel;
	}

	public void setIsDel(byte isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "ImagesFieid [id=" + id + ", clsId=" + clsId + ", fieldName=" + fieldName + ", isMoney=" + isMoney
				+ ", isTable=" + isTable + ", serialNo=" + serialNo + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", isDel=" + isDel
				+ "]";
	}

	public ImagesFieid(long id, long clsId, String fieldName, byte isMoney, byte isTable, int serialNo,
			String createdBy, String modifiedBy, Date createdTime, Date modifiedTime, byte isDel) {
		super();
		this.id = id;
		this.clsId = clsId;
		this.fieldName = fieldName;
		this.isMoney = isMoney;
		this.isTable = isTable;
		this.serialNo = serialNo;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdTime = createdTime;
		this.modifiedTime = modifiedTime;
		this.isDel = isDel;
	}

	public ImagesFieid() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
