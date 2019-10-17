package com.yc.news.biz;

import java.util.List;

import com.yc.news.entity.News;

public interface INewsBiz {
	/**
	 * 添加新闻
	 * @param tid：新闻类型编号
	 * @param title：新闻标题
	 * @param author：新闻作者
	 * @param createDate：发布日期
	 * @param pic：新闻图片
	 * @param content：新闻内容
	 * @return：成功返回true
	 */
	public boolean addNews(String tid,String title,String author,String createDate,String pic,String content);
	
	/**
	 * 修改信息
	 * @param tid：新闻类型编号
	 * @param title：新闻标题
	 * @param author：新闻作者
	 * @param createDate：发布日期
	 * @param pic：新闻图片
	 * @param content：新闻内容
	 * @param nid：要修改的新闻编号
	 * @return：成功返回true
	 */
	public boolean updateNews(String tid,String title,String author,String createDate,String pic,String content,String nid);
	
	/**
	 * 根据新闻编号删除新闻信息
	 * @param nid：要删除的新闻编号
	 * @return：成功返回true
	 */
	public boolean delNews(String nid);
	
	/**
	 * 根据新闻类型删除新闻信息
	 * @param tid：要删除的新闻类型编号
	 * @return：成功返回true
	 */
	public boolean delNewsByTid(String tid);
	
	/**
	 * 根据新闻编号，查询新闻信息
	 * @param nid：要查询的新闻信息
	 * @return：返回该新闻信息
	 */
	public News getNews(String nid);
	
	/**
	 * 分页查询给定类型的新闻
	 * @param tid：要查询的新闻类型编号，如果此项为空，则说明查询所有新闻
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的条数
	 * @return：返回所有满足条件的新闻信息
	 */
	public List<News> getNews(String tid,Integer pageNo,Integer pageSize);
	
	/**
	 * 分页查询给定类型的新闻
	 * @param tid：要查询的新闻类型编号，如果此项为空，则说明查询所有新闻
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的条数
	 * @return：返回所有满足条件的新闻信息
	 */
	public List<News> getHeadNews(String tid,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据类型名分页查询新闻信息
	 * @param tname：要查询的新闻类型名称
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的条数
	 * @return：返回所有满足条件的新闻信息
	 */
	public List<News> getNewsByTname(String tname,Integer pageNo,Integer pageSize);
	
	/**
	 * 查询给定的新闻类型的总条数
	 * @param tid：要统计的新闻类型编号，如果此项为空，说明统计全部新闻
	 * @return
	 */
	public int getTotal(String tid);
	
	/**
	 * 根据新闻类型编号分页查询图片新闻
	 * @param tid：要查询的新闻类型编号，如果为空，则查询全部类型
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的条数
	 * @return：返回所有满足条件的新闻信息
	 */
	public List<News> getNewsByPic(String tid,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 分页查询新闻信息，不返回新闻内容
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的条数
	 * @return：返回所有满足条件的新闻信息
	 */
	public List<News> getNewsHead(String tname,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 根据新闻类型编号分页查询图片新闻,不返回新闻内容
	 * @param tid：要查询的新闻类型编号，如果为空，则查询全部类型
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的条数
	 * @return：返回所有满足条件的新闻信息
	 */
	public List<News> getNewsHeadPic(String tid,Integer pageNo,Integer pageSize);
}
