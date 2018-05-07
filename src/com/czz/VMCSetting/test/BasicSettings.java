package com.czz.VMCSetting.test;



import java.util.Arrays;

import org.dom4j.Document;
import org.dom4j.Element;


import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.icu.text.StringPrepParseException;
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
		//进入基本配置
		//验证是否与配置文件相同
		clickButton("基本配置");
		UiScrollable ViewListScrollable = new UiScrollable(new UiSelector().scrollable(true));
		//
		verifyEditText_OnlyText("机构设置", 1,"org-name", smartvmcfgPath, ViewListScrollable);
		verifyEditText_OnlyText("服务器地址设置", 3, "server-address", configPath, ViewListScrollable);
		verifyText_OnlyText("机构名称", "org-name", smartvmcfgPath, ViewListScrollable);
		verifyText_OnlyText("服务器地址", "server-address", configPath, ViewListScrollable);
		verifyText_Array("从VMC同步价格", "syncPriceFromVmc", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("POS模式", "POSMode", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("一卡通POS模式", "onecardPos", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("综合机是否基于库存", "comMachBaseStock", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("售空商品往后排", "backSoldout", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("格子机选货是否用键盘", "GridBrowseMode", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("食品机选货是否用键盘", "SpringBrowseMode", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("弹簧机交替出货", "ComAlternateVendout", configPath, assertContent.YesAndNoCN, assertContent.YesAndNo, ViewListScrollable);
		verifyText_Array("POS协议", "POSProtocol", configPath, assertContent.POSProtocolAssertCN, assertContent.POSProtocolAssert, ViewListScrollable);
		verifyText_Array("POS串口", "POSSerial", configPath, assertContent.POSSerialAssert, assertContent.POSSerialAssert, ViewListScrollable);
		verifyText_Array("浏览模式", "browseMode", configPath, assertContent.browseModeAssertCN, assertContent.browseModeAssert, ViewListScrollable);
		verifyText_Array("扫描头型号", "ScanDevModel", configPath, assertContent.ScanDevModelAssert, assertContent.ScanDevModelAssert, ViewListScrollable);
		verifyText_Array("扫描头连接方式", "scannConnMode", configPath, assertContent.scannConnModeAssertCN, assertContent.scannConnModeAssert, ViewListScrollable);
		verifyText_Array("VIP系统", "VipSystem", configPath, assertContent.VipSystemAssertCN, assertContent.VipSystemAssert, ViewListScrollable);
		verifyText_Array("掌纹支付串口", "PalmSerial", configPath, assertContent.PalmSerialAssert, assertContent.PalmSerialAssert, ViewListScrollable);
		verifyText_OnlyText("心跳间隔时间", "HeartBeatInterval", configPath, ViewListScrollable);
		
		//		//展开高级设置
		clickButton("高级设置");
		UiScrollable AdvanceListScrollable = new UiScrollable(new UiSelector().scrollable(true));
		//输入框
		editTextEdit("机构设置", 1, "org-name", AdvanceListScrollable, btn_cancel, btn_submit, orgNameString, smartvmcfgPath);
		editTextEdit("服务器地址设置", 3, "server-address", AdvanceListScrollable, btn_cancel, btn_submit, hostString, configPath);
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
		listViewClick("POS协议", "POSProtocol", AdvanceListScrollable, btn_cancel, assertContent.POSProtocolAssert);
		listViewClick("POS串口", "POSSerial", AdvanceListScrollable,btn_cancel, assertContent.POSSerialAssert);
		listViewClick("浏览模式", "browseMode", AdvanceListScrollable, btn_cancel, assertContent.browseModeAssert);
		listViewClick("扫描头型号", "ScanDevModel", AdvanceListScrollable, btn_cancel, assertContent.ScanDevModelAssert);
		listViewClick("扫描头连接方式", "scannConnMode", AdvanceListScrollable, btn_cancel, assertContent.scannConnModeAssert);
		listViewClick("VIP系统", "VipSystem", AdvanceListScrollable, btn_cancel, assertContent.VipSystemAssert);
		listViewClick("掌纹支付串口", "PalmSerial", AdvanceListScrollable, btn_cancel, assertContent.PalmSerialAssert);
		editTextEdit("心跳间隔时间", 2, "HeartBeatInterval", AdvanceListScrollable, btn_cancel, btn_submit, HeartBeatIntervalRandomNum, configPath);
	}
	public void verifyText_OnlyText(String Name, String configTag, String configPath,UiScrollable ListScrollable) throws UiObjectNotFoundException{
		UiObject textResult = new UiObject(new UiSelector().text(Name).fromParent(new UiSelector().className("android.widget.TextView").index(2)));
		if (!textResult.exists()) {
			ListScrollable.getChildByText(new UiSelector().className("android.widget.TextView"), Name);
		}
		textResult.waitForExists(2000);
		if (!textResult.exists()) {
			return;
		}
		String verifyText = textResult.getText();
		if ("HeartBeatInterval".equals(configTag)) {
			String[] sourceStrArray = verifyText.split("  ");
			verifyText = sourceStrArray[0];
		}
		assertConfigs(configPath, configTag, verifyText);
	}
	public void verifyEditText_OnlyText(String Name, int index, String configTag, String configPath,UiScrollable ListScrollable) throws UiObjectNotFoundException{
		UiObject EditTextResult = new UiObject(new UiSelector().textContains(Name).fromParent(new UiSelector().className("android.widget.EditText").index(index)));
		if (!EditTextResult.exists()) {
			ListScrollable.getChildByText(new UiSelector().className("android.widget.TextView"), Name);
		}
		EditTextResult.waitForExists(2000);
		if (!EditTextResult.exists()) {
			return;
		}
		String verifyText = EditTextResult.getText();
		assertConfigs(configPath, configTag, verifyText);
	}
	public void verifyText_Array(String Name, String configTag, String configPath,String[] waitVerify,String[] targetVerify,UiScrollable ListScrollable) throws UiObjectNotFoundException{
		UiObject textResult = new UiObject(new UiSelector().text(Name).fromParent(new UiSelector().className("android.widget.TextView").index(2)));
		if (!textResult.exists()) {
			ListScrollable.getChildByText(new UiSelector().className("android.widget.TextView"), Name);
		}
		textResult.waitForExists(2000);
		if (!textResult.exists()) {
			return;
		}
		Document filedocument=UIAutomatorAssistant.documentload(configPath);
		Element rootElm = filedocument.getRootElement();
		Element assertElm=rootElm.element(configTag);
		String assertText=assertElm.getText().toString();
		int waitPosition = Arrays.binarySearch(waitVerify, textResult.getText());
		int configPosition = Arrays.binarySearch(targetVerify, assertText);
		UIAutomatorAssistant.UiAutomatorLog(textResult.getText()+"----"+assertText);
		if (waitPosition==configPosition) {
			UIAutomatorAssistant.UiAutomatorLog(configTag+"<Success>");
		}
		else{
			UIAutomatorAssistant.UiAutomatorLog(configTag+"<FAILURE>");
		}
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
	public void editTextEdit(String Name,int index, String configTag,UiScrollable ListScrollable, UiObject btn_cancel, UiObject btn_submit, String typeText, String configPath) throws UiObjectNotFoundException{
		UiObject EditTextField = new UiObject(new UiSelector().textContains(Name).fromParent(new UiSelector().className("android.widget.EditText").index(index)));
		if (!EditTextField.exists()) {
			ListScrollable.getChildByText(new UiSelector().className("android.widget.TextView"), Name);
		}
		EditTextField.waitForExists(2000);
		if (!EditTextField.exists()) {
			return;
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
	public void listViewClick(String Name, String configTag,UiScrollable ListScrollable ,UiObject btn_cancel, String[] content) throws UiObjectNotFoundException{
		UiObject Spinner = new UiObject(new UiSelector().textContains(Name).fromParent(new UiSelector().className("android.widget.Spinner")));
		if (!Spinner.exists()) {
			ListScrollable.getChildByText(new UiSelector().className("android.widget.TextView"), Name);
		}
		Spinner.waitForExists(2000);
		if (!Spinner.exists()) {
			return;
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
