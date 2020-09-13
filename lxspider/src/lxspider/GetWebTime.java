package lxspider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetWebTime {

		private static String webUrl = "http://www.baidu.com";
	    
	    /*
	     * 获取当前网络时间
	     */
	    public static String getNetworkTime() 
	    {
	        try
	        {
	            URL url = new URL(webUrl);
	            URLConnection conn = url.openConnection();
	            conn.connect();
	            long dateL = conn.getDate();
	            Date date = new Date(dateL);
				//在这里，如果是想返回sql的Date类型则修改方法的返回类型
				//将目前获取到的网络时间util.Date转换成sql.Date的操作如下：
				// java.sql.Date date1=new java.sql.Date(date.getTime());//年 月 日
				//java.sql.Time date2=new java.sql.Time(date.getTime());//时   分    秒
				//java.sql.Timestamp date3=new java.sql.Timestamp(date.getTime());//年  月 日  时  分   秒 毫秒
				//System.out.println("输出当前时间年月日"+date1);
				// System.out.println("输出当前时间时分秒"+date2);//System.out.println("输出当前时间年月日时分秒毫秒"+date3);
				//以下是将时间转换成String类型并返回
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	            
	            return dateFormat.format(date);
	            
	        }
	        catch (MalformedURLException e) 
	        {
	            e.printStackTrace();
	        }
	        catch (IOException e) 
	        {
	            // TODO: handle exception
	            e.printStackTrace();
	        }
	        return "";
	    }
}
