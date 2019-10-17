package com.yc.news.biz.impl;

import java.util.ArrayList;
import java.util.List;

import com.yc.news.biz.INewsBiz;
import com.yc.news.dao.DBHelper;
import com.yc.news.entity.News;

public class NewsBizImpl extends DBHelper implements INewsBiz {
	private String sql;
	private List<Object> params;

	public boolean addNews(String tid, String title, String author,String createDate, String pic, String content) {
		sql="insert into news values(1,?,?,?,to_date(?,'YYYY-MM-DD'),?,?)";

		params=new ArrayList<Object>();
		params.add(tid);
		params.add(title);
		params.add(author);
		params.add(createDate);
		params.add(pic);
		params.add(content);

		if(this.update(sql, params)>0){
			return true;
		}else{
			return false;
		}
	}

	public boolean delNews(String nid) {
		sql="delete from news where nid=?";

		params=new ArrayList<Object>();
		params.add(nid);

		if(this.update(sql, params)>0){
			return true;
		}else{
			return false;
		}
	}

	public boolean delNewsByTid(String tid) {
		sql="delete from news where tid=?";

		params=new ArrayList<Object>();
		params.add(tid);

		if(this.update(sql, params)>0){
			return true;
		}else{
			return false;
		}
	}

	public News getNews(String nid) {
		sql="select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,content,tname from news n,topic t where n.tid=t.tid and nid=?";
		params=new ArrayList<Object>();
		params.add(nid);

		List<News> list=this.findResult(sql, params,News.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<News> getNews(String tid, Integer pageNo, Integer pageSize) {
		sql="select * from (select aa.*,rownum rn from(select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,content,tname " +
		"from news n,topic t where n.tid=t.tid";
		params=new ArrayList<Object>();
		if(tid!=null){
			sql+=" and n.tid=?";
			params.add(tid);
		}

		if(pageNo!=null){ //说明要根据分页查询
			sql+=" order by createDate desc)aa where rownum<=?) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else{
			sql+=" order by createDate desc)aa)";
		}
		return this.findResult(sql, params,News.class);
	}

	public List<News> getNewsByPic(String tid, Integer pageNo, Integer pageSize) {
		sql="select * from (select aa.*,rownum rn from(select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,content,tname " +
		"from news n,topic t where n.tid=t.tid and pic is not null";
		params=new ArrayList<Object>();
		if(tid!=null){
			sql+=" and n.tid=?";
			params.add(tid);
		}

		if(pageNo!=null){ //说明要根据分页查询
			sql+=" order by createDate desc)aa where rownum<=?) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else{
			sql+=" order by createDate desc)aa)";
		}
		return this.findResult(sql, params,News.class);
	}

	public List<News> getNewsByTname(String tname, Integer pageNo,Integer pageSize) {
		sql="select * from (select aa.*,rownum rn from(select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,content,tname " +
		"from news n,topic t where n.tid=t.tid";
		params=new ArrayList<Object>();
		if(tname!=null){
			sql+=" and tname=?";
			params.add(tname);
		}

		if(pageNo!=null){ //说明要根据分页查询
			sql+=" order by createDate desc)aa where rownum<=?) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else{
			sql+=" order by createDate desc)aa)";
		}
		return this.findResult(sql, params,News.class);
	}

	public int getTotal(String tid) {
		sql="select count(*) from news where 1=1";
		params=new ArrayList<Object>();
		if(tid!=null){
			sql+=" and tid=?";
			params.add(tid);
		}
		List<String> list=this.findResult(sql,params);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0));
		}else{
			return 0;
		}
	}

	public boolean updateNews(String tid, String title, String author,String createDate, String pic, String content, String nid) {
		sql="update news set nid=nid";
		params=new ArrayList<Object>();
		
		if(tid!=null){
			sql+=",tid=?";
			params.add(tid);
		}
		if(title!=null){
			sql+=",title=?";
			params.add(title);
		}
		if(author!=null){
			sql+=",author=?";
			params.add(author);
		}
		if(createDate!=null){
			sql+=",createDate=to_date(?,'YYYY-MM-DD')";
			params.add(createDate);
		}
		if(pic!=null&&!"".equals(pic.trim())){
			sql+=",pic=?";
			params.add(pic);
		}
		if(content!=null){
			sql+=",content=?";
			params.add(content);
		}
		sql+=" where nid=?";
		params.add(nid);
		
		if(this.update(sql, params)>0){
			return true;
		}else{
			return false;
		}
	}

	public List<News> getNewsHead(String tname, Integer pageNo, Integer pageSize) {
		sql="select * from (select aa.*,rownum rn from(select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,tname " +
		"from news n,topic t where n.tid=t.tid";
		params=new ArrayList<Object>();
		if(tname!=null){
			sql+=" and tname=?";
			params.add(tname);
		}

		if(pageNo!=null){ //说明要根据分页查询
			sql+=" order by createDate desc)aa where rownum<=?) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else{
			sql+=" order by createDate desc)aa)";
		}
		return this.findResult(sql, params,News.class);
	}

	public List<News> getNewsHeadPic(String tid, Integer pageNo,Integer pageSize) {
		sql="select * from (select aa.*,rownum rn from(select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,tname " +
		"from news n,topic t where n.tid=t.tid and pic is not null";
		params=new ArrayList<Object>();
		if(tid!=null){
			sql+=" and n.tid=?";
			params.add(tid);
		}

		if(pageNo!=null){ //说明要根据分页查询
			sql+=" order by createDate desc)aa where rownum<=?) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else{
			sql+=" order by createDate desc)aa)";
		}
		return this.findResult(sql, params,News.class);
	}

	public List<News> getHeadNews(String tid, Integer pageNo, Integer pageSize) {
		sql="select * from (select aa.*,rownum rn from(select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,tname " +
		"from news n,topic t where n.tid=t.tid";
		params=new ArrayList<Object>();
		if(tid!=null){
			sql+=" and n.tid=?";
			params.add(tid);
		}

		if(pageNo!=null){ //说明要根据分页查询
			sql+=" order by createDate desc)aa where rownum<=?) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else{
			sql+=" order by createDate desc)aa)";
		}
		return this.findResult(sql, params,News.class);
	}

}
