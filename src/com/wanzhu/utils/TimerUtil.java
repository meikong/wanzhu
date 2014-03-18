package com.wanzhu.utils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {
	public static void scheduleTask(TimerTask task, Date date, long interval) {
		Timer timer = new Timer();
		timer.schedule(task, date, interval);
	}
}
