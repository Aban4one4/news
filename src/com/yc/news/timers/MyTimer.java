package com.yc.news.timers;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

public class MyTimer {
	private TimerTask task; //定时任务
	private Timer timer=new Timer(); //定时器
	
	public void getTimer(final HttpSession session){
		task=new TimerTask(){
			@Override
			public void run() {
				session.setAttribute("code","");
				session.removeAttribute("code");
			}
		};
		timer.schedule(task,2*60*1000); //2分钟之后执行任务一次
	}
	
	//取消任务
	public void closeTime(){
		timer.cancel();
		task.cancel();
	}
	
}
