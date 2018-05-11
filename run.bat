adb shell rm /data/local/tmp/VMCtest.jar
adb push E:\workspace-mars\VMCtest\bin\VMCtest.jar /data/local/tmp/
adb shell uiautomator runtest VMCtest.jar -c com.czz.VMCSetting.test.VMCSettingTest
cmd /k