package com.sky.magic.util;

// TODO 按等级输出日志
public class MLog {
	public static void log(String tag, String log) {
		System.out.println(tag+ ": "+log);
	}
	
	public static void log(String tag, String log, String name) {
		System.out.println("[" + name + "] " + log + " (" + tag + ")");
	}
}
