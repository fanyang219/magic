package com.sky.magic.util;

// TODO ���ȼ������־
public class MLog {
	public static void log(String tag, String log) {
		System.out.println(tag+ ": "+log);
	}
	
	public static void log(String tag, String log, String name) {
		System.out.println("[" + name + "] " + log + " (" + tag + ")");
	}
}
