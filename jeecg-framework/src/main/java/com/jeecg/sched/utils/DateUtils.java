package com.jeecg.sched.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 去掉时分秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date trimTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 去掉分秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date trimMS(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 改变周
	 * 
	 * @param date
	 * @param dayOfWeek
	 * @return
	 */
	public static Date changeWeek(Date date, int dayOfWeek) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		return calendar.getTime();
	}

	/**
	 * 改变日
	 * 
	 * @param date
	 * @param dayOfMonth
	 * @return
	 */
	public static Date changeDate(Date date, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return calendar.getTime();
	}

	/**
	 * 计算某个日期相隔几天后的日期
	 * 
	 * @param interval
	 * @param date
	 * @return
	 */
	public static Date nextDate(int interval, Date... date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date.length == 1 ? date[0] : new Date());
		calendar.add(Calendar.DATE, interval);
		return calendar.getTime();
	}

	/**
	 * 计算某个日期相隔几周后的日期
	 * 
	 * @param interval
	 * @param date
	 * @return
	 */
	public static Date nextWeek(int interval, Date... date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date.length == 1 ? date[0] : new Date());
		calendar.add(Calendar.WEEK_OF_YEAR, interval);
		return calendar.getTime();
	}

	/**
	 * 计算某个日期相隔几月后的日期
	 * 
	 * @param interval
	 * @param date
	 * @return
	 */
	public static Date nextMonth(int interval, Date... date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date.length == 1 ? date[0] : new Date());
		calendar.add(Calendar.MONTH, interval);
		return calendar.getTime();
	}

}
