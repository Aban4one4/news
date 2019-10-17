package com.yc.news.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class News implements Serializable{
	private int nid;
	private int tid;
	private String title;
	private String author;
	private String createDate;
	private String pic;
	private String content;
	
	private String tname;

	@Override
	public String toString() {
		return "News [author=" + author + ", content=" + content
				+ ", createDate=" + createDate + ", nid=" + nid + ", pic="
				+ pic + ", tid=" + tid + ", title=" + title + ", tname="
				+ tname + "]";
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTitle() {
		return title;
	}
	
	public String getTitle10() {
		if(title.length()>10){
			return title.substring(0,10)+"...";
		}else{
			return title;
		}
	}
	
	public String getTitle20() {
		if(title.length()>20){
			return this.title.substring(0,20)+"...";
		}else{
			return title;
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public News(int nid, int tid, String title, String author,
			String createDate, String pic, String content, String tname) {
		super();
		this.nid = nid;
		this.tid = tid;
		this.title = title;
		this.author = author;
		this.createDate = createDate;
		this.pic = pic;
		this.content = content;
		this.tname = tname;
	}

	public News() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + nid;
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
		result = prime * result + tid;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((tname == null) ? 0 : tname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (nid != other.nid)
			return false;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		if (tid != other.tid)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		return true;
	}
}
