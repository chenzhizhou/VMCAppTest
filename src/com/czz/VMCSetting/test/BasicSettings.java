package com.czz.VMCSetting.test;



import org.dom4j.Document;
import org.dom4j.Element;


import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.view.KeyEvent;
import assitant.UIAutomatorAssistant;

@SuppressWarnings("deprecation")
public class BasicSettings extends UiAutomatorTestCase{
	public BasicSettings(){
		super();
	}
	String testCmp = "com.inhand.vmcsettings/.VMCSettings";
	String configPath = "/sdcard/inbox/config/config.xml";
	String smartvmcfgPath = "/sdcard/inbox/config/smartvm_cfg.xml";
	
	
	@Override
	public void setUp() throws Exception{
		super.setUp();
		Process process = Runtime.getRuntime().exec("rm /sdcard/PerformanceLog.txt");
		process.waitFor();
		startApp(testCmp);
	}
	@Override
	public void tearDown() throws Exception{
		super.tearDown();
	}
	private int startApp(String componentName){
		StringBuffer testsBuffer = new StringBuffer();
		testsBuffer.append("am start -n ");
		testsBuffer.append(componentName);
		int ret = -1;
		try{
			Process process = Runtime.getRuntime().exec(testsBuffer.toString());
			ret = process.waitFor();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public void testBasicSettings() throws UiObjectNotFoundException{
		
		String hostString = UIAutomatorAssistant.getRandomString(5);
		String orgNameString = UIAutomatorAssistant.getRandomString(5);
		String HeartBeatIntervalRandomNum = Integer.toString(UIAutomatorAssistant.getRandomInteger(20, 60));
		UiObject btn_cancel = new UiObject(new UiSelector().text("取消"));
		UiObject btn_submit = new UiObject(new UiSelector().text("应用"));
		UiScrollable listScrollable = new UiScrollable(new UiSelector().scrollable(true));
		//进入基本配置
		clickButton("基本配置");
		//展开高级设置
		clickButton("高级设置");
		//输入框
		editTextEdit("机构设置", 1, "org-name", listScrollable, btn_cancel, btn_submit, orgNameString, smartvmcfgPath);
		editTextEdit("服务器地址设置", 3, "server-address", listScrollable, btn_cancel, btn_submit, hostString, configPath);
		//checkBox
		checkToggleButton("从VMC同步价格", "syncPriceFromVmc", btn_cancel, btn_submit, configPath);
		checkToggleButton("POS模式", "POSMode", btn_cancel, btn_submit, configPath);
		checkToggleButton("一卡通POS模式", "onecardPos", btn_cancel, btn_submit, configPath);
		checkToggleButton("综合机是否基于库存", "comMachBaseStock", btn_cancel, btn_submit, configPath);
		checkToggleButton("售空商品往后排", "backSoldout", btn_cancel, btn_submit, configPath);
		checkToggleButton("格子机选货是否用键盘", "GridBrowseMode", btn_cancel, btn_submit, configPath);
		checkToggleButton("食品机选货是否用键盘", "SpringBrowseMode", btn_cancel, btn_submit, configPath);
		checkToggleButton("弹簧机交替出货", "ComAlternateVendout", btn_cancel, btn_submit, configPath);
		//选择框
		listViewClick("POS协议", "POSProtocol", listScrollable, btn_cancel, assertContent.POSProtocolAssert);
		listViewClick("POS串口", "POSSerial", listScrollable,btn_cancel, assertContent.POSSerialAssert);
		listViewClick("浏览模式", "browseMode", listScrollable, btn_cancel, assertContent.browseModeAssert);
		listViewClick("扫描头型号", "ScanDevModel", listScrollable, btn_cancel, assertContent.ScanDevModelAssert);
		listViewClick("扫描头连接方式", "scannConnMode", listScrollable, btn_cancel, assertContent.scannConnModeAssert);
		listViewClick("VIP系统", "VipSystem", listScrollable, btn_cancel, assertContent.VipSystemAssert);
		listViewClick("掌纹支付串口", "PalmSerial", listScrollable, btn_cancel, assertContent.PalmSerialAssert);
		editTextEdit("心跳间隔时间", 2, "HeartBeatInterval", listScrollable, btn_cancel, btn_submit, HeartBeatIntervalRandomNum, configPath);
	}
	public void checkToggleButton(String Name,String configTag, UiObject btn_cancel, UiObject btn_submit, String configPath) throws UiObjectNotFoundException {
		UiObject ToggleButton = new UiObject(new UiSelector().textContains(Name).fromParent(new UiSelector().className("android.widget.ToggleButton")));
		ToggleButton.waitForExists(2000);
		if (!ToggleButton.exists()) {
			return;
		}
		if (ToggleButton.exists() && ToggleButton.isEnabled()) {
			UIAutomatorAssistant.UiAutomatorLog("Check the "+Name);
			ToggleButton.click();
			btn_cancel.waitForExists(1500);
			btn_cancel.click();
		}
		if (ToggleButton.isChecked()) {
			if (!assertConfigs(configPath, configTag, "1")){
				UIAutomatorAssistant.UiAutomatorLog("--ERROR: "+configTag+" assert FAIL!!!");
			}
		}
		else{
			if (!assertConfigs(configPath, configTag, "0")){
				UIAutomatorAssistant.UiAutomatorLog("--ERROR: "+configTag+" assert FAIL!!!");
			}
		}
	}
	public void clickButton(String Name) throws UiObjectNotFoundException{
		UiObject btn = new UiObject(new UiSelector().text(Name));
		btn.waitForExists(2000);
		if (!btn.exists()) {
			return;
		}
		btn.waitForExists(1500);
		if (btn.exists() && btn.isEnabled() && btn.isClickable()) {
			UIAutomatorAssistant.UiAutomatorLog("Click the "+Name);
			btn.click();
		}
	}
	public void editTextEdit(String Name,int index, String configTag,UiScrollable listScrollable, UiObject btn_cancel, UiObject btn_submit, String typeText, String configPath) throws UiObjectNotFoundException{
		UiObject EditTextField = new UiObject(new UiSelector().textContains(Name).fromParent(new UiSelector().className("android.widget.EditText").index(index)));
		EditTextField.waitForExists(2000);
		if (!EditTextField.exists()) {
			return;
		}
		if (!EditTextField.exists()) {
			listScrollable.getChildByText(new UiSelector().className("android.widget.TextView"), Name);
		}
		if (EditTextField.exists() && EditTextField.isEnabled()) {
			UIAutomatorAssistant.UiAutomatorLog("Type the "+configTag);
			EditTextField.clickBottomRight();
			UIAutomatorAssistant.pressTimes(KeyEvent.KEYCODE_DEL, EditTextField.getText().length());
			EditTextField.setText(typeText);
		}
		if (btn_submit.exists() && btn_submit.isEnabled()) {
			UIAutomatorAssistant.UiAutomatorLog("Click the btn_submit");
			btn_submit.click();
		}
		if (btn_cancel.exists() && btn_cancel.isEnabled()) {
			UIAutomatorAssistant.UiAutomatorLog("Click the btn_cancel");
			btn_cancel.click();
		}
		if (!assertConfigs(configPath, configTag, typeText)){
			UIAutomatorAssistant.UiAutomatorLog("--ERROR: "+configTag+" assert FAIL!!!");
		}
	}
	public void listViewClick(String Name, String configTag,UiScrollable listScrollable ,UiObject btn_cancel, String[] content) throws UiObjectNotFoundException{
		UiObject Spinner = new UiObject(new UiSelector().textContains(Name).fromParent(new UiSelector().className("android.widget.Spinner")));
		if (!Spinner.exists()) {
			listScrollable.getChildByText(new UiSelector().className("android.widget.TextView"), Name);
		}
		//UiObject Spinner = new UiObject(new UiSelector().textContains(Name).fromParent(new UiSelector().className("android.widget.Spinner")));
		if (Spinner.exists() && Spinner.isEnabled()) {
			UIAutomatorAssistant.UiAutomatorLog("Check the "+configTag);
			Spinner.clickAndWaitForNewWindow();
		}
		UiObject ListView = new UiObject(new UiSelector().className("android.widget.ListView"));
		int count = ListView.getChildCount();
		UIAutomatorAssistant.UiAutomatorLog(configTag+"'s count:"+count);
		for(int i=0; i<count; i++){
			UiObject listView_item = new UiObject(new UiSelector().className("android.widget.TextView").index(i));
			if (listView_item.exists() && listView_item.isEnabled()) {
				UIAutomatorAssistant.UiAutomatorLog("Check the "+configTag+"_"+listView_item.getText());
				listView_item.click();
			}
			btn_cancel.waitForExists(1000);
			if (btn_cancel.exists() && btn_cancel.isEnabled()) {
			//UIAutomatorAssistant.UiAutomatorLog("Click the btn_cancel");
			btn_cancel.click();
			}
			if (!assertConfigs(configPath, configTag, content[i])){
				UIAutomatorAssistant.UiAutomatorLog("--ERROR: "+configTag+"_"+listView_item+"assert FAIL!!!");
			}
			if (Spinner.exists() && Spinner.isEnabled() && i!=count-1) {
				//UIAutomatorAssistant.UiAutomatorLog("Check the "+configTag);
				Spinner.clickAndWaitForNewWindow();
			}
		}
	}
	public boolean assertConfigs(String filePath, String ElementName, String toAssertText){
		Document filedocument=UIAutomatorAssistant.documentload(filePath);
		Element rootElm = filedocument.getRootElement();
		Element assertElm=rootElm.element(ElementName);
		String assertText=assertElm.getText().toString();
		if (!assertText.equals(toAssertText)){
			UIAutomatorAssistant.UiAutomatorLog(ElementName+"<FAILURE>");
			return false;
		}
		else{
			UIAutomatorAssistant.UiAutomatorLog(ElementName+"<Success>");
			return true;
		}
	}
}
