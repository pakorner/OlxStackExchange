package com.olx.dev.util;


public class UtConvertTime {

	public static String setTimeAllFormat(long millisecond){
		String resultTime = "";
		try {
			long seconds = millisecond / 1000;
			long minutes = seconds / 60;
			long hours = minutes / 60;
			resultTime = hours % 24 + " h " + minutes % 60 + " m " + seconds % 60+ " s "; 
			
		} catch (Exception e) {
		            e.printStackTrace();
		}
		return resultTime;
	}
}
