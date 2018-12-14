package com.example.dao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class UserItem {
	private int iid;
	private String username;
	private String password;
	private String name;
	private String enterprise_name;
	private int type;
	private String imsi;
	private String msisdn;
	private String email;
	private String department_name;
	private int department_id;
	private String region_name;
	private int region_id;
	private int flag;
	private Area area;
	private List<Map<String, Object>> authAreaList;
	private List<String> authFuncList;
}