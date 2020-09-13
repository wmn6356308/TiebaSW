package lxspider;

import java.util.Timer;

public class Start{

	public static void main(String[] args) {
		
		Timer timer = new Timer();
		MyCrawler crawler = new MyCrawler();
		
		crawler.firstLogin();
		
		//每12小时启动1次
		//timer.schedule(crawler, 0, 1000*60*60*12);
		
		timer.schedule(crawler, 0, 2000*60);
	}

}
