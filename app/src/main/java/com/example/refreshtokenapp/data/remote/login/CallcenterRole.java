package com.example.refreshtokenapp.data.remote.login;

import java.util.List;

public class CallcenterRole{
	private List<Object> companyCode;
	private List<Object> warehouses;
	private List<Object> distributers;
	private String id;

	public List<Object> getCompanyCode(){
		return companyCode;
	}

	public List<Object> getWarehouses(){
		return warehouses;
	}

	public List<Object> getDistributers(){
		return distributers;
	}

	public String getId(){
		return id;
	}
}