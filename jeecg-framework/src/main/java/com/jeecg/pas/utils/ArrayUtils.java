package com.jeecg.pas.utils;

public class ArrayUtils {

	public static Integer[] str2Int(String[] strs) {
		int length = strs.length;
		Integer[] ints = new Integer[length];
		for (int i = 0; i < length; i++) {
			ints[i] = Integer.valueOf(strs[i]);
		}
		return ints;
	}

	public static String[] Int2Str(Integer[] ints) {
		int length = ints.length;
		String[] strs = new String[length];
		for (int i = 0; i < length; i++) {
			strs[i] = String.valueOf(ints[i]);
		}
		return strs;
	}

}
