package com.smartinsight.model;

public class fullCount {
	
	private String claimClinicMap;
	
	private String claimProvinceMap;
	
	private int countNumber;

	@Override
	public String toString() {
		return "fullCount [claimClinicMap=" + claimClinicMap + ", claimProvinceMap=" + claimProvinceMap
				+ ", countNumber=" + countNumber + "]";
	}

	public String getClaimClinicMap() {
		return claimClinicMap;
	}

	public void setClaimClinicMap(String claimClinicMap) {
		this.claimClinicMap = claimClinicMap;
	}

	public String getClaimProvinceMap() {
		return claimProvinceMap;
	}

	public void setClaimProvinceMap(String claimProvinceMap) {
		this.claimProvinceMap = claimProvinceMap;
	}

	public int getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(int countNumber) {
		this.countNumber = countNumber;
	}

	public fullCount(String claimClinicMap, String claimProvinceMap, int countNumber) {
		super();
		this.claimClinicMap = claimClinicMap;
		this.claimProvinceMap = claimProvinceMap;
		this.countNumber = countNumber;
	}
	
	

}
