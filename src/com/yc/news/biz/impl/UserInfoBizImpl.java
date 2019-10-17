package com.yc.news.biz.impl;

import java.util.ArrayList;
import java.util.List;

import com.yc.news.biz.IUserInfoBiz;
import com.yc.news.dao.DBHelper;
import com.yc.news.entity.UserInfo;

public class UserInfoBizImpl extends DBHelper implements IUserInfoBiz{
	private String sql;
	private List<Object> params;
	
	public boolean addUserInfo(String uname, String pwd, String email) {
		sql="insert into userInfo values(1,?,?,?)";
		params=new ArrayList<Object>();
		params.add(uname);
		params.add(pwd);
		params.add(email);
		
		if( this.update(sql,params)>0){
			return true;
		}else{
			return false;
		}
	}

	public UserInfo loginUserInfo(String uname, String pwd) {
		sql="select * from userInfo where uname=? and pwd=?";
		params=new ArrayList<Object>();
		params.add(uname);
		params.add(pwd);
		
		List<UserInfo> list=this.findResult(sql, params, UserInfo.class);
		if(list!=null  &&  list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public boolean checkEmail(String email) {
		sql="select * from userInfo where email=?";
		params=new ArrayList<Object>();
		params.add(email);
		
		List<UserInfo> list=this.findResult(sql, params, UserInfo.class);
		if(list!=null  &&  list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	public boolean checkUserName(String uname) {
		sql="select * from userInfo where uname=?";
		params=new ArrayList<Object>();
		params.add(uname);
		
		List<UserInfo> list=this.findResult(sql, params, UserInfo.class);
		if(list!=null  &&  list.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
