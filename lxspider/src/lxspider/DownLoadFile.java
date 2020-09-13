package lxspider;
import java.io.*;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
public class DownLoadFile 
{
	public String getFileNameByUrl (String url)
	{
		url = url.substring(7);
		return url = MD5.getMD5(url.getBytes())+".html";
	}
	
	public boolean downloadFile(String url)
	{
		GetMethod getMethod= null;
		HttpClient httpClient= null;

		httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(500000);
		try
		{
			getMethod = new GetMethod(url);
		}
		catch(Exception exception)
		{
		}
		
		try
		{
			getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 500000);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(getMethod);
			
			//System.out.print("statusCode = " + statusCode + "\n");
			if(statusCode != HttpStatus.SC_OK)
			{
				System.out.println("Method failed" + getMethod.getStatusLine());
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "UTF-8"));
			
			String str = "";
			
			
			while((str = reader.readLine()) != null)
			{
				if(str.contains("本吧吧主火热招募中，点击参加"))
				{
					return true;
				}
				else if(str.contains("threadlist_author"))
				{
					return false;
				}
//				else
//				{
//					System.out.print(str);
//				}
			}
		}

		catch(HttpException e)
		{
			e.printStackTrace();
			System.out.println("1处异常");
			getMethod.releaseConnection();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("2处异常");
			getMethod.releaseConnection();
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("3处异常");
		}

		return false;
	}
}



