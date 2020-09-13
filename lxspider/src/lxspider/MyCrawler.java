package lxspider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimerTask;

import javax.mail.MessagingException;

public class MyCrawler extends TimerTask{

	public static final Object signal = new Object();
	private static String fileName;
	private static byte[] signCode;
	
	private boolean crawling(String seeds, String tiebaName)
	{
		DownLoadFile downLoader = new DownLoadFile();
		boolean result = downLoader.downloadFile(seeds);
		return result;
	}

	private boolean verifiTime(String message)
	{
		String[] subMessage = message.split(" ");
		
//		System.out.print(subMessage[3]);
//		System.out.print(subMessage[0]);
		long validiTime_s = Long.parseLong(subMessage[3].trim());
		
		validiTime_s = validiTime_s * 24 * 60 * 60 * 1000;
		
		
		
		String applyTime = subMessage[0].trim();
		String webTime = GetWebTime.getNetworkTime();
		
		if(webTime == null)
		{
			System.out.print("请联网使用");
			Scanner in = new Scanner(System.in);
			in.next();
			//in.close();
			System.exit(1);
		}
		
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        
		try
		{
			long NTime = df.parse(webTime).getTime();
			long OTime = df.parse(applyTime).getTime();
	        long diff = (NTime - OTime);
	        
	        //System.out.print(diff);
	        if((diff >= 0) && (diff <= validiTime_s))
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean tieBaSWstart()
	{
		System.out.print("请选择 \n1：输入授权码使用 \n2：注册授权码\n");
		@SuppressWarnings("resource")
		Scanner selectItemNumScan = new Scanner(System.in);
		String selectItemNumStr = selectItemNumScan.nextLine();
		
		if(selectItemNumStr.equalsIgnoreCase("1")) 
		{
			//授权码验证
			System.out.print("请输入授权码：");
			byte[] signCode = selectItemNumScan.nextLine().getBytes();
			//byte[] signCode = "fFopeCOJ70zpEjMvImSZT0KjccyySr9J4y2mAB+NMWMbtos1yCDzSiZYApDYDaS1W7xHOBZ1TC5HpLW4G5RXqiw11RF5ptnBbuN57UA8nnGPV4h7/bwfhdhF7iWy85W09YoXZS4n0eOot8iajBS3oGNiItTn7rucH2RiZ2B54UE=".getBytes();
			//String test = "123 123 15 eth0:08:71:90:a9:b7:c5 wlan0:0a:71:90:a9:b7:c1 wlan1:08:71:90:a9:b7:c1 eth4:04:d4:c4:e8:5c:f0 wlan2:08:71:90:a9:b7:c2";
			
			File file = new File("sdfjkl");
			if(file.equals(null))
			{
				System.out.print("文件打开失败");
			}
  
			BufferedReader reader;
			try 
			{
				reader = new BufferedReader(new InputStreamReader(new FileInputStream("sdfjkl"), "UTF-8"));
				String message = reader.readLine();
				message = message + " " + MAC.getLocalMac();
				//System.out.print(message + "\n");
				boolean verify = VerifyTools.verify(message.getBytes(), signCode, KeyTools.getPublicKeyFromCer());
				if(verify)
				{
					//这里添加对message信息的解析，拿到message中的起始时间，和当前时间，两个时间做个比较，判断软件授权时间还在不
					
					boolean result = this.verifiTime(message);
					reader.close();
					return result;
				}
				else
				{
					reader.close();
					System.out.print("授权码校验失败，请联系mn.bob@qq.com");
					Scanner in = new Scanner(System.in);
					in.nextLine();
					//in.close();
					System.exit(1);
				}
				
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(selectItemNumStr.equalsIgnoreCase("2"))
		{
			//获取用户信息
			Scanner in = new Scanner(System.in);
			
			System.out.print("输入邮箱：");
			String emailAddress = in.nextLine();
			
			System.out.print("输入手机号：");
			String telNum = in.nextLine();
			
			String authorizeTime = new String();
			System.out.print("输入期望授权时间（1/7/15/30）：");
			authorizeTime = in.nextLine();
			
//			while(true)
//			{
//				System.out.print("输入期望授权时间（1/7/15/30）：");
//				authorizeTime = in.nextLine();
//				int day = Integer.getInteger(authorizeTime).intValue();
//				if(1 != day || 7 != day || 15 != day || 30 != day)
//				{
//					System.out.print("输入错误");
//				}
//				else
//				{
//					break;
//				}
//			}
			
			//in.close();
			
			String MACAddress = MAC.getLocalMac();
			
			Date day = new Date();    
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); 
			String applyTime = new String(df.format(day));
			
			/* 将上面的信息邮件发送到我自己的邮箱 */
			String saveToLocalInfo = applyTime + " " + emailAddress + " " + telNum + " " +authorizeTime;
			String sendEmailSginInfo = saveToLocalInfo + " " + MACAddress;
			
			try 
			{
				SendEmail.Send(sendEmailSginInfo, 1);
			}
			catch (MessagingException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (GeneralSecurityException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String WriteFileName = new String("sdfjkl");
			
			try 
			{
				File writeFile = new File(WriteFileName);
				if( !writeFile.exists() )
				{
					writeFile.createNewFile();
				}
				FileWriter fileWritter = new FileWriter(writeFile.getName(), false);
				fileWritter.write(saveToLocalInfo);
				fileWritter.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			System.out.println("注册信息已发送，请关注邮箱获取授权码。联系邮箱：mn.bob@qq.com");
			Scanner inn = new Scanner(System.in);
			inn.nextLine();

			System.exit(0);
		}
		else
		{
			System.out.print("无效选择");
			System.exit(0);
		}
		return false;
	}
	
	private void authCheck() {
		
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("sdfjkl"), "UTF-8"));
			String message = reader.readLine();
			message = message + " " + MAC.getLocalMac();	/* 本地不保存Mac地址，防止在其它电脑上运行，每次验证重新获取 */
			
			boolean VeriResult = VerifyTools.verify(message.getBytes(), signCode, KeyTools.getPublicKeyFromCer());
			/* 授权码验证通过后，再校验授权时间是否有效 */
			if(VeriResult)
			{
				boolean timeCheckResult = this.verifiTime(message);
				
				if(timeCheckResult)
				{
					System.out.println("授权码OK，授权时间OK");
				}
				else
				{
					System.out.println("授权时间到期，需要重新授权。联系mn.bob@qq.com");
					Scanner in = new Scanner(System.in);
					in.nextLine();
					//in.close();
					System.exit(1);
				}
				reader.close();
			}
			else
			{
				reader.close();
				System.out.print("授权码校验失败。请联系mn.bob@qq.com");
				Scanner in = new Scanner(System.in);
				in.nextLine();
				//in.close();
				System.exit(1);
			}
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void firstLogin()
	{
		System.out.print("请选择 \n1：输入授权码使用 \n2：注册授权码\n");
		@SuppressWarnings("resource")
		Scanner selectItemNumScan = new Scanner(System.in);
		String selectItemNumStr = selectItemNumScan.nextLine();
		
		if(selectItemNumStr.equalsIgnoreCase("1")) 
		{
			/* 从本地读取注册信息，文件名字随便起，吓唬人 */
			File file = new File("sdfjkl");
			if(file.equals(null))
			{
				System.out.print("文件打开失败");
			}
			
			/* 授权码输入及验证 */
			System.out.print("请输入授权码：");
			String signCodeStr = selectItemNumScan.nextLine();
			if(signCodeStr.contains(" ") || signCodeStr.trim().length() != 172 || signCodeStr.contains("_"))
			{
				System.out.println("授权码错误");
				Scanner end = new Scanner(System.in);
				end.nextLine();
				System.exit(1);
			}
			signCode = signCodeStr.getBytes();
  
			this.authCheck();
		}
		else if(selectItemNumStr.equalsIgnoreCase("2"))
		{
			//获取用户信息
			Scanner in = new Scanner(System.in);
			
			System.out.print("输入邮箱：");
			String emailAddress = in.nextLine();
			
			System.out.print("输入手机号：");
			String telNum = in.nextLine();
			
			String authorizeTime = new String();
			System.out.print("输入期望授权时间（1/7/15/30）：");
			authorizeTime = in.nextLine();
			
//			while(true)
//			{
//				System.out.print("输入期望授权时间（1/7/15/30）：");
//				authorizeTime = in.nextLine();
//				int day = Integer.getInteger(authorizeTime).intValue();
//				if(1 != day || 7 != day || 15 != day || 30 != day)
//				{
//					System.out.print("输入错误");
//				}
//				else
//				{
//					break;
//				}
//			}
			
			String MACAddress = MAC.getLocalMac();
			
			Date day = new Date();    
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); 
			String applyTime = new String(df.format(day));
			
			/* 将上面的信息邮件发送到我自己的邮箱 */
			String saveToLocalInfo = applyTime + " " + emailAddress + " " + telNum + " " +authorizeTime;
			String sendEmailSginInfo = saveToLocalInfo + " " + MACAddress;
			
			try 
			{
				SendEmail.Send(sendEmailSginInfo, 1);
			}
			catch (MessagingException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (GeneralSecurityException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String WriteFileName = new String("sdfjkl");
			
			try 
			{
				File writeFile = new File(WriteFileName);
				if( !writeFile.exists() )
				{
					writeFile.createNewFile();
				}
				FileWriter fileWritter = new FileWriter(writeFile.getName(), false);
				fileWritter.write(saveToLocalInfo);
				fileWritter.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			System.out.println("注册信息已发送，请关注邮箱获取授权码。联系邮箱：mn.bob@qq.com");
			Scanner inn = new Scanner(System.in);
			inn.nextLine();

			System.exit(0);
		}
		else
		{
			System.out.print("无效选择");
			System.exit(0);
		}

		System.out.print("输入文件路径：");
		Scanner in = new Scanner(System.in);
		fileName = in.nextLine();
		this.crawler();
	}
	
	public void crawler() {
		
		boolean result = false;
		String tieBaName = new String();
		String chineseURL = new String();
		String emailContent = new String();
		
		this.authCheck();
		
		File file = new File(fileName);
		if(file.equals(null))
		{
			System.out.print("文件打开失败");
			System.exit(1);
		}

		try
		{  
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			
			while ((tieBaName = reader.readLine()) != null) 
			{
				try {
					
					chineseURL = URLEncoder.encode(tieBaName, "UTF-8");
					
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				}
				
				result = this.crawling("https://tieba.baidu.com/f?ie=utf-8&kw=" + chineseURL, tieBaName);
				
				if(result)
				{
					System.out.println(tieBaName);
					emailContent += (tieBaName + "\r\n");
				}
				else
				{
					//System.out.println("fail");
				}
			}
			//reader.close();  
		} 
		catch (IOException e) 
		{  
			e.printStackTrace();  
		}    	

		if(null != emailContent)
		{
			Date day=new Date();    
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); 
			String WriteFileName = new String(df.format(day) + ".dat");
			
			try 
			{
				File writeFile = new File(WriteFileName);
				if( !writeFile.exists() )
				{
					writeFile.createNewFile();
				}
				FileWriter fileWritter = new FileWriter(writeFile.getName(), false);
				fileWritter.write(emailContent);
				fileWritter.close();
				System.out.println("扫描结果已写入文件：" + WriteFileName);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		//System.out.printf(emailContent);
		
		try {
			
			SendEmail.Send(emailContent, 0);
			
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}catch (javax.mail.MessagingException e) {
			e.printStackTrace();
		}
		
		System.out.println("邮件已发送");

		System.out.println("12小时后，进行下次扫描");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		this.crawler();
	}
}
