package com.olx.dev.util;

import android.app.Activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class UtActionBarHelper {
	private final ActionBar mActionBar;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private Activity ac;

	public UtActionBarHelper(Activity activity) {
		ac = activity;
		mActionBar = ((SherlockFragmentActivity)activity).getSupportActionBar();
	}

	public void init() {
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		mTitle = mDrawerTitle = ac.getTitle();
	}

	/**
	 * When the drawer is closed we restore the action bar state reflecting
	 * the specific contents in view.
	 */
	public void onDrawerClosed() {
		mActionBar.setTitle(mTitle);
	}

	/**
	 * When the drawer is open we set the action bar to a generic title. The
	 * action bar should only contain data relevant at the top level of the
	 * nav hierarchy represented by the drawer, as the rest of your content
	 * will be dimmed down and non-interactive.
	 */
	public void onDrawerOpened() {
		mActionBar.setTitle(mDrawerTitle);
	}
}
