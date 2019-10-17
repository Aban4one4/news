package com.yc.news.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Topic implements Serializable{
	private int tid;
	private String tname;
	
	@Override
	public String toString() {
		return "Topic [tid=" + tid + ", tname=" + tname + "]";
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Topic(int tid, String tname) {
		super();
		this.tid = tid;
		this.tname = tname;
	}

	public Topic() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tid;
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
		Topic other = (Topic) obj;
		if (tid != other.tid)
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		return true;
	}
}
