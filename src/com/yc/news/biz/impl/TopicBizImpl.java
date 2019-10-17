package com.yc.news.biz.impl;

import java.util.ArrayList;
import java.util.List;

import com.yc.news.biz.ITopicBiz;
import com.yc.news.dao.DBHelper;
import com.yc.news.entity.Topic;

public class TopicBizImpl extends DBHelper implements ITopicBiz {
	private String sql="";
	private List<Object> params;
	
	public boolean addTopic(String tname) {
		sql="insert into topic values(1,?)";
		params=new ArrayList<Object>();
		params.add(tname);
		if(this.update(sql,params)>0){
			return true;
		}else{
			return false;
		}
	}	

	public boolean delTopic(String tid) {
		sql="delete from topic where tid in("+tid+")";
		if(this.update(sql,null)>0){
			return true;
		}else{
			return false;
		}
	}

	public List<Topic> getAllTopic() {
		sql="select * from topic order by tid asc";
		List<Topic> topics=this.findResult(sql,null,Topic.class);
		return topics;
	}

	public boolean updateTopic(String tname, String tid) {
		sql="update topic set tname=? where tid=?";
		params=new ArrayList<Object>();
		params.add(tname);
		params.add(tid);
		if(this.update(sql,params)>0){
			return true;
		}else{
			return false;
		}
	}

	public List<Topic> getAllTopic(Integer pageNo, Integer pageSize) {
		sql="select * from (select aa.*,rownum rn from(select * from topic order by tid)aa";
		params=new ArrayList<Object>();

		if(pageNo!=null){ //说明要根据分页查询
			sql+=" where rownum<=?) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else{
			sql+=")";
		}
		return this.findResult(sql, params,Topic.class);
	}

	public int getTotal() {
		sql="select count(*) from topic";
		List<String> list=this.findResult(sql,null);
		if(list!=null && list.size()>0){
			return Integer.parseInt(list.get(0));
		}else{
			return 0;
		}
	}
}
