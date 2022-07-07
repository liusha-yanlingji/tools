package com.smartinsight.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
@TableName("images_cls")
public class ImagesCls implements Serializable{
	
	@TableId(value = "id",type = IdType.NONE)
	private Long id;
	
	private String code;
	
	private String name;
	
	private String aliasName;
	
	private String pCls;
	
	private int businessType;
	
	private int claimType;

	private int claimValue;
	
	private int stubType;
	
	private int underwritingType;
	
	private int underwritingValue;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private Date createdTime;
	
	private Date modifiedTime;
	
	private byte isDel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getpCls() {
		return pCls;
	}

	public void setpCls(String pCls) {
		this.pCls = pCls;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public int getClaimType() {
		return claimType;
	}

	public void setClaimType(int claimType) {
		this.claimType = claimType;
	}

	public int getClaimValue() {
		return claimValue;
	}

	public void setClaimValue(int claimValue) {
		this.claimValue = claimValue;
	}

	public int getStubType() {
		return stubType;
	}

	public void setStubType(int stubType) {
		this.stubType = stubType;
	}

	public int getUnderwritingType() {
		return underwritingType;
	}

	public void setUnderwritingType(int underwritingType) {
		this.underwritingType = underwritingType;
	}

	public int getUnderwritingValue() {
		return underwritingValue;
	}

	public void setUnderwritingValue(int underwritingValue) {
		this.underwritingValue = underwritingValue;
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
		return "ImagesCls [id=" + id + ", code=" + code + ", name=" + name + ", aliasName=" + aliasName + ", pCls="
				+ pCls + ", businessType=" + businessType + ", claimType=" + claimType + ", claimValue=" + claimValue
				+ ", stubType=" + stubType + ", underwritingType=" + underwritingType + ", underwritingValue="
				+ underwritingValue + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + ", isDel=" + isDel + "]";
	}

	public ImagesCls(Long id, String code, String name, String aliasName, String pCls, int businessType, int claimType,
			int claimValue, int stubType, int underwritingType, int underwritingValue, String createdBy,
			String modifiedBy, Date createdTime, Date modifiedTime, byte isDel) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.aliasName = aliasName;
		this.pCls = pCls;
		this.businessType = businessType;
		this.claimType = claimType;
		this.claimValue = claimValue;
		this.stubType = stubType;
		this.underwritingType = underwritingType;
		this.underwritingValue = underwritingValue;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdTime = createdTime;
		this.modifiedTime = modifiedTime;
		this.isDel = isDel;
	}
	
	
	
	
}
