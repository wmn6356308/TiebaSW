package lxspider;


import java.net.NetworkInterface;
import java.util.Enumeration;

public class MAC {
   
	public static String getLocalMac() {

        String macStr = new String();
        
        try {

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) 
            {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface != null) 
                {
                    String name = networkInterface.getName();
                    StringBuffer sb = new StringBuffer("");
                    byte[] bytes = networkInterface.getHardwareAddress();
                    if (bytes != null) 
                    {
                        for (int i = 0; i < bytes.length; i++) 
                        {
                            if (i != 0) 
                            {
                                sb.append(":");
                            }
                            int tmp = bytes[i] & 0xff; // 字节转换为整数
                            String str = Integer.toHexString(tmp);
                            if (str.length() == 1) 
                            {
                                sb.append("0" + str);
                            } 
                            else 
                            {
                                sb.append(str);
                            }
                        }
                        
                        macStr = macStr + name + ":" + sb.toString() + "/";
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return macStr;
    }
}
