package com.yc.news.biz;

import java.util.List;

import com.yc.news.entity.Topic;

public interface ITopicBiz {
	/**
	 * 添加新闻类型信息
	 * @param tname：新闻类型名
	 * @return：成功返回true
	 */
	public boolean addTopic(String tname);
	
	/**
	 * 删除新闻类型
	 * @param tid：要删除的类型编号
	 * @return
	 */
	public boolean delTopic(String tid);
	
	/**
	 * 修改新闻类型信息
	 * @param tname：新的类型名
	 * @param tid：要修改的类型编号
	 * @return
	 */
	public boolean updateTopic(String tname,String tid);
	
	/**
	 * 获取所有的类型信息
	 * @return
	 */
	public List<Topic> getAllTopic();
	
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Topic> getAllTopic(Integer pageNo,Integer pageSize);
	
	/**
	 * 获取总记录条数
	 * @return
	 */
	public int getTotal();
}
