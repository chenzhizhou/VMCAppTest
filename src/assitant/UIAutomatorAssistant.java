package assitant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


@SuppressWarnings("deprecation")
public class UIAutomatorAssistant extends UiAutomatorTestCase{
	
	
	public static String m_logpathString = "/sdcard/PerformanceLog.txt";
	public static void UiAutomatorLog(String str) {//输出日志
	        // 取得当前时间
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(System.currentTimeMillis());
	        String datestr = calendar.get(Calendar.MONTH)+1 + "-" +calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "." + calendar.get(Calendar.MILLISECOND) + ": ";

	        FileWriter fwlog = null;
	        try
	        {
	            fwlog = new FileWriter(m_logpathString,true);
	            fwlog.write(datestr + str + "\r\n");
	            System.out.println(datestr + str);
	            fwlog.flush();

	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        } finally
	        {
	            try
	            {
	                fwlog.close();
	            } catch (IOException e)
	            {
	                e.printStackTrace();
	            }
	        }
	    }
	public static Document documentload(String filename) {//读取xml文件

		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));  //读取XML文件,获得document对象
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	public static void pressTimes(int keyCode, int times) {//对于一个按键按多次
		for(int i=0;i<times;i++){
		UiDevice.getInstance().pressKeyCode(keyCode);
		}
	}
	public static String getRandomString(int length) {//获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
	    //随机字符串的随机字符库
	    String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
	    StringBuffer sb = new StringBuffer();
	    int len = KeyString.length();
	    for (int i = 0; i < length; i++) {
	       sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
	    }
	    return sb.toString();
	}
	public static int getRandomInteger(int MIN, int MAX){
		Random rand = new Random();
		int randNumber =rand.nextInt(MAX - MIN + 1) + MIN;
		return randNumber;
	}
}
