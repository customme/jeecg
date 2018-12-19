package com.jeecg.sched.utils;

public class ArrayUtils {

	public static Integer[] str2Int(String[] strs) {
		int length = strs.length;
		Integer[] ints = new Integer[length];
		for (int i = 0; i < length; i++) {
			ints[i] = Integer.valueOf(strs[i]);
		}
		return ints;
	}

}
