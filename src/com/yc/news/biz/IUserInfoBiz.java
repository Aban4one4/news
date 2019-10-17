package com.yc.news.biz;

import com.yc.news.entity.UserInfo;

public interface IUserInfoBiz {
	/**
	 * 用户注册
	 * @param uname：用户名
	 * @param pwd：密码
	 * @param email：注册邮箱
	 * @return
	 */
	public boolean addUserInfo(String uname,String pwd,String email);
	
	/**
	 * 用户登录
	 * @param uname：用户名
	 * @param pwd：密码
	 * @return
	 */
	public UserInfo loginUserInfo(String uname,String pwd);
	
	/**
	 * 验证用户名是否存在
	 * @param uname：用户名
	 * @return：如果存在，则返回true
	 */
	public boolean checkUserName(String uname);
	
	/**
	 * 验证邮箱是否存在
	 * @param uname：用户名
	 * @return：如果存在，则返回true
	 */
	public boolean checkEmail(String email);
}
