package com.example.refreshtokenapp.data.remote.login;

public class LoginResponse{
	private Data data;
	private String message;
	private int status;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}
