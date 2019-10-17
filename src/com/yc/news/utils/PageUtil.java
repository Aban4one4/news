package com.yc.news.utils;

public class PageUtil {
	private int pageNo=1; //当前是第几页
	private int pageSize=10; //每页显示的条数
	private int totalSize; //总共有多少条记录
	private int totalPages; //总共有多少页

	@Override
	public String toString() {
		return "PageUtil [pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", totalPages=" + totalPages + ", totalSize=" + totalSize
				+ "]";
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if(pageNo<=0){
			this.pageNo=1;
		}else if(pageNo>=getTotalPages()){
			this.pageNo = totalPages;
		}else{
			this.pageNo = pageNo;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize<=0){
			this.pageSize=10;
		}else{
			this.pageSize = pageSize;
		}
	}

	public void setTotalSize(int totalSize) {
		if(totalSize<0){
			this.totalSize=0;
		}else{
			this.totalSize = totalSize;
		}
	}

	public int getTotalSize() {
		return this.totalSize;
	}


	public int getTotalPages(){
		//20  5  4
		//21  5  4+1
		this.totalPages=this.getTotalSize()%this.getPageSize()==0?this.getTotalSize()/this.getPageSize():this.getTotalSize()/this.getPageSize()+1;
		return totalPages;
	}

	//下一页
	public int getNextPageNo(){
		int nextPage=1;
		if(pageNo<this.getTotalPages()){ //说明还有下一页
			nextPage=pageNo+1;
		}else{
			nextPage=this.getTotalPages();
		}
		return nextPage;
	}

	//下一页
	public int getProPageNo(){
		int proPage=1;
		if(pageNo>1){ //说明还有下一页
			proPage=pageNo-1;
		}else{
			proPage=1;
		}
		return proPage;
	}

	
}
