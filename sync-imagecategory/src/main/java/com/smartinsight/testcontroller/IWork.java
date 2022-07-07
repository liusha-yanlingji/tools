package com.smartinsight.testcontroller;

public class IWork {

	
	public static void main(String[] args) {
		int hashCode = java.util.UUID.randomUUID().toString().hashCode();
		if (hashCode <0){
			hashCode=-hashCode;
		}
		// 0 代表前面补充0 
		// 10 代表长度为10
		// d 代表参数为正数型
		String format = String.format("%019d", hashCode).substring(0,19);
		System.out.println(format);
	}
}
