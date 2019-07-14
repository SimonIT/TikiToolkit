package com.minecarts.verrier.tikitoolkit.helper;

public class StringHelper {
	public static String join(String[] arr, int offset) {
		return join(arr, offset, " ");
	}

	public static String join(String[] arr, int offset, String delim) {
		StringBuilder str = new StringBuilder();

		if ((arr == null) || (arr.length == 0)) {
			return str.toString();
		}

		for (int i = offset; i < arr.length; i++) {
			str.append(arr[i]).append(delim);
		}

		return str.toString().trim();
	}
}
