#什么值得买 自动签到 Android端
这个插件安装后，每天进入什么值得买APP可以自动签到，每天一次。
前往[**Release**](https://github.com/tttony3/smzdm/releases/)下载最新可用版本。
> 使用方法：安装app,进入app点击设置,在辅助功能-服务中，开启smzdm
  
##主要实现原理
找到“签到抽大奖”按钮，并点击
部分代码
```java
public class SmzdmService extends AccessibilityService {
    private AccessibilityNodeInfo rootNodeInfo;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        /*找到“签到抽大奖”按钮,并点击*/
        List<AccessibilityNodeInfo> list=event.getSource().findAccessibilityNodeInfosByViewId("com.smzdm.client.android:id/usercenter_login");
        list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }
}
```
## 版权说明
项目使用MIT许可证。在理解可能的风险后，你可以将代码用于任何用途。
