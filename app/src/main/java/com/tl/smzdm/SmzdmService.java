package com.tl.smzdm;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;


public class SmzdmService extends AccessibilityService {
    private AccessibilityNodeInfo rootNodeInfo;
    private int previousDate = 0;
    private int nowDate =0;
    SharedPreferences sp;

    void lookChild(AccessibilityNodeInfo rootNodeInfo, int lv) {
        if (rootNodeInfo == null) return;
        int n = rootNodeInfo.getChildCount();
        if (n > 0) {
            Log.e("rootNodeInfo " + lv, "text:" + rootNodeInfo.getText() + " desc: " + rootNodeInfo.getContentDescription() + " count:" + rootNodeInfo.getChildCount() + " ClassName:" + rootNodeInfo.getClassName());
            for (int i = 0; i < n; i++) {
                lookChild(rootNodeInfo.getChild(i), lv + 1);
            }
        } else {
            Log.e("rootNodeInfo " + lv, "text:" + rootNodeInfo.getText() + " desc: " + rootNodeInfo.getContentDescription() + " count:" + rootNodeInfo.getChildCount() + " ClassName:" + rootNodeInfo.getClassName());
        }

    }

    /**
     * AccessibilityEvent的回调方法
     *
     * @param event 事件
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        previousDate = sp.getInt("date", 0);
        String now = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        nowDate = Integer.valueOf(now);
        if (nowDate-previousDate == 0) return;

        rootNodeInfo = event.getSource();
        if (rootNodeInfo == null) return;

   //     lookChild(rootNodeInfo, 0);

        List<AccessibilityNodeInfo> list = event.getSource().findAccessibilityNodeInfosByViewId("com.smzdm.client.android:id/usercenter_login");
        if (!list.isEmpty()) {
            list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            sp.edit().putInt("date", nowDate).apply();

        }


    }


    @Override
    public void onInterrupt() {

    }

    @Override
    public void onServiceConnected() {
        sp = getSharedPreferences("smzdm", 0);
        super.onServiceConnected();
    }


}
