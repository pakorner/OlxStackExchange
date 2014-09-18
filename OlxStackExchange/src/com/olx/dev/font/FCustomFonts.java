package com.olx.dev.font;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class FCustomFonts {
	
	public static final String TYPEFACE_FOLDER = "fonts";
	public static final String TYPEFACE_EXTENSION_TTF = ".ttf";


	private static Hashtable<String, Typeface> sTypeFaces = new Hashtable<String, Typeface>(4);

	public static Typeface getTypeFaceFuturaLight(Context context) {
		Typeface tempTypeface = sTypeFaces.get("futuralight");

		if (tempTypeface == null) {
		    String fontPath = new StringBuilder(TYPEFACE_FOLDER).append('/').append("futuralight").append(TYPEFACE_EXTENSION_TTF).toString();
		    tempTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
		    sTypeFaces.put("futuralight", tempTypeface);
		}

		return tempTypeface;
	}
	
	public static Typeface getTypeFaceHelveticaXBlk(Context context) {
		Typeface tempTypeface = sTypeFaces.get("DB Helvethaica X Blk Cond");

		if (tempTypeface == null) {
		    String fontPath = new StringBuilder(TYPEFACE_FOLDER).append('/').append("DB Helvethaica X Blk Cond").append(TYPEFACE_EXTENSION_TTF).toString();
		    tempTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
		    sTypeFaces.put("DB Helvethaica X Blk Cond", tempTypeface);
		}

		return tempTypeface;
	}

}
