package com.olx.dev.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olx.dev.R;
import com.olx.dev.font.FCustomFonts;
import com.olx.dev.service.SvStackOverFlowLoader;
import com.olx.dev.util.UtActionBarHelper;
import com.olx.dev.util.UtDeviceControl;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

public class FmViewQuestion extends SherlockFragment  implements OnRefreshListener<ListView>{

	private DrawerLayout mDrawerLayout;
	private PullToRefreshListFragment mPullRefreshListFragment;
	private PullToRefreshListView mPullRefreshListView;
	private UtActionBarHelper mActionBar;
	private SherlockActionBarDrawerToggle mDrawerToggle;
	private ProgressBar pgQuestion;
	
	private TextView txtSmenuName;
	private TextView txtSmenuVersion;
	private TextView txtSmenuStackOverFlow;
	private TextView txtSmenuHelp;
	private TextView txtSmenuSettings;
	
	private EditText edtSearch;
	
	 

	public static Fragment newInstance() {
		Fragment f = new FmViewQuestion();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lay_main, container, false);
		 
		
		Initial(view);
		
		txtSmenuStackOverFlow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDrawerLayout.closeDrawers();
				mPullRefreshListView.setAdapter(null);
				getStackAllQuestion(true);
			}
		});
		
		//First Load 
		getStackAllQuestion(true);
		return view;
	}
	
	public void Initial(View view){
		mDrawerLayout = (DrawerLayout) view.findViewById(R.id.lay_drawer);
		pgQuestion = (ProgressBar) view.findViewById(R.id.pg_question);
	    txtSmenuName = (TextView) view.findViewById(R.id.txt_smenu_name);
		txtSmenuVersion = (TextView) view.findViewById(R.id.txt_smenu_version);
		txtSmenuStackOverFlow = (TextView) view.findViewById(R.id.txt_smenu_stackoverflow);
		txtSmenuHelp = (TextView) view.findViewById(R.id.txt_smenu_help);
		txtSmenuSettings = (TextView) view.findViewById(R.id.txt_smenu_setting);
		
		txtSmenuName.setTypeface(FCustomFonts.getTypeFaceHelveticaXBlk(getActivity()));
		txtSmenuVersion.setTypeface(FCustomFonts.getTypeFaceHelveticaXBlk(getActivity()));
		txtSmenuStackOverFlow.setTypeface(FCustomFonts.getTypeFaceHelveticaXBlk(getActivity()));
		txtSmenuHelp.setTypeface(FCustomFonts.getTypeFaceHelveticaXBlk(getActivity()));
		txtSmenuSettings.setTypeface(FCustomFonts.getTypeFaceHelveticaXBlk(getActivity()));
		
		mDrawerLayout.setDrawerListener(new DrawerListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		mPullRefreshListFragment = (PullToRefreshListFragment) getActivity().getSupportFragmentManager().findFragmentById(
				R.id.frag_ptr_list);

		// Get PullToRefreshListView from Fragment
		mPullRefreshListView = mPullRefreshListFragment.getPullToRefreshListView();

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(this);
		
		mPullRefreshListFragment.setListShown(true);

		mActionBar = createActionBarHelper();
		mActionBar.init();

	
		// ActionBarDrawerToggle provides convenient helpers for tying together
		// the
		// prescribed interactions between a top-level sliding drawer and the
		// action bar.
		mDrawerToggle = new SherlockActionBarDrawerToggle(this.getActivity(), mDrawerLayout, R.drawable.ic_drawer_light, R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		super.onCreateOptionsMenu(menu, inflater);
		
	        menu.add("Search")
	            .setIcon(R.drawable.ic_search)
	            .setActionView(R.layout.view_edt_collapsible)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

	      
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	
		if(item.getTitle().equals("Search")){
			
			  
			
			  edtSearch = (EditText)item.getActionView().findViewById(R.id.edt_search);
			  edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
			  edtSearch.setText("");
			  
			  UtDeviceControl.showSoftKeyboard(getActivity(), edtSearch);
			  
			  edtSearch.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					if (actionId == EditorInfo.IME_ACTION_SEARCH ) { 
						 UtDeviceControl.hideSoftKeyboard(getActivity());
						 getStackSearchQuestion(edtSearch.getText().toString());
						 mPullRefreshListView.setAdapter(null);
					   }
					return false;
				}
			});
		}
		/*
		 * The action bar home/up action should open or close the drawer.
		 * mDrawerToggle will take care of this.
		 */
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void getStackAllQuestion(boolean showProgress){
		if(showProgress)showProgress();
		
		new SvStackOverFlowLoader(getActivity(),mPullRefreshListView,pgQuestion).getQuestionAll();
	}
	
	private void getStackSearchQuestion(String criteria){
	
		showProgress();
		new SvStackOverFlowLoader(getActivity(),mPullRefreshListView,pgQuestion).getQuestionBySearch(criteria);
	}
	

	public void showProgress(){
		if(pgQuestion!=null && !pgQuestion.isShown()){
			pgQuestion.setVisibility(View.VISIBLE);
		}
		
	}
	
	public void hideProgress(){
		if(pgQuestion!=null&& pgQuestion.isShown()){
			pgQuestion.setVisibility(View.GONE);
		}
	}
	/**
	 * This list item click listener implements very simple view switching by
	 * changing the primary content text. The drawer is closed when a selection
	 * is made.
	 */


	/**
	 * A drawer listener can be used to respond to drawer events such as
	 * becoming fully opened or closed. You should always prefer to perform
	 * expensive operations such as drastic relayout when no animation is
	 * currently in progress, either before or after the drawer animates.
	 * 
	 * When using ActionBarDrawerToggle, all DrawerLayout listener methods
	 * should be forwarded if the ActionBarDrawerToggle is not used as the
	 * DrawerLayout listener directly.
	 */
	private class DrawerListener implements DrawerLayout.DrawerListener {
		@Override
		public void onDrawerOpened(View drawerView) {
			mDrawerToggle.onDrawerOpened(drawerView);
			mActionBar.onDrawerOpened();
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			mDrawerToggle.onDrawerClosed(drawerView);
			mActionBar.onDrawerClosed();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState) {
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}

	/**
	 * Create a compatible helper that will manipulate the action bar if
	 * available.
	 */
	private UtActionBarHelper createActionBarHelper() {
		return new UtActionBarHelper(getActivity());
	}


	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
//		mPullRefreshListView.onRefreshComplete();
		
		getStackAllQuestion(false);
	}
}