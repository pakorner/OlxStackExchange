package com.olx.dev.util;

public class UtConfig {
	private static String VERSION_API = "2.2";
	public static final String LINK_ALL_QUESTION = "http://api.stackexchange.com/"+VERSION_API+"/questions?order=desc&sort=activity&site=stackoverflow";
	public static final String LINK_SEARCH_QUESTION = "http://api.stackexchange.com/"+VERSION_API+"/search?order=desc&sort=activity&site=stackoverflow&intitle=";
	public static final String LINK_DETAIL_QUESTION = "http://api.stackexchange.com/"+VERSION_API+"/questions/$?order=desc&sort=activity&site=stackoverflow&filter=withbody";
}
