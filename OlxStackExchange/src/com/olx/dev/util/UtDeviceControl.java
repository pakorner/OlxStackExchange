package com.olx.dev.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UtDeviceControl {

	public static void hideSoftKeyboard(Activity ac) {
	    if(ac.getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) ac.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(ac.getCurrentFocus().getWindowToken(), 0);
	    }
	}

	/**
	 * Shows the soft keyboard
	 */
	public static void showSoftKeyboard(Activity ac,View view) {
	    InputMethodManager inputMethodManager = (InputMethodManager) ac.getSystemService(Context.INPUT_METHOD_SERVICE);
	    view.requestFocus();
	    inputMethodManager.showSoftInput(view, 0);
	}
	
}
