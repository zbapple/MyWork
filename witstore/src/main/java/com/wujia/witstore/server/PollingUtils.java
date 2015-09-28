package com.wujia.witstore.server;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;


/***********************************************************
 * @ClassName: PollingUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年7月29日 下午5:41:58
 ************************************************************/
public class PollingUtils {

	/**
	 * @param context
	 * @param seconds
	 * @param cls
	 * @param action
	 */
	public static void startPollingService(Context context, int seconds, Class<?> cls,String action) {
		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, cls);
		intent.setAction(action);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		long triggerAtTime = SystemClock.elapsedRealtime();
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
				seconds * 1000, pendingIntent);
	}

	/**
	 *
	 * @param context
	 * @param cls
	 * @param action
	 */
	public static void stopPollingService(Context context, Class<?> cls,String action) {
		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, cls);
		intent.setAction(action);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.cancel(pendingIntent);
	}


	/**
	 * @Title: regReceiver
	 * @Description: 动态注册广播接收器
	 * @param  context
	 * @param  msgReceiver
	 * @param  action
	 */
	public static void regReceiver(Context context,BroadcastReceiver msgReceiver,String action){
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(action);
		context.registerReceiver(msgReceiver, intentFilter);
	}

}
