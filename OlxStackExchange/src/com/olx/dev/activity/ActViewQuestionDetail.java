package com.olx.dev.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.olx.dev.R;
import com.olx.dev.service.SvStackOverFlowLoader;
import com.olx.dev.util.UtActionBarHelper;

public class ActViewQuestionDetail extends SherlockFragmentActivity {

	PullToRefreshScrollView mPullRefreshScrollView;
	ScrollView mScrollView;
	LinearLayout lay_queston_detail;
	
	private UtActionBarHelper mActionBar;
	private ProgressBar pgQuestion;
	private String mQuestionId;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_question_detail);
		
		Intent i = getIntent();
		mQuestionId = i.getStringExtra("questionId");
		Initial();
		//First Load 
		getStackDetailQuestion(true,mQuestionId);
		
	}

	
	public void Initial(){
		
		pgQuestion = (ProgressBar) findViewById(R.id.pg_question);
		lay_queston_detail = (LinearLayout)findViewById(R.id.lay_queston_detail);
		
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.scr_question_detail);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				lay_queston_detail.setVisibility(View.GONE);
				getStackDetailQuestion(true,mQuestionId);
			}
		});

		mScrollView = mPullRefreshScrollView.getRefreshableView();

		mActionBar = createActionBarHelper();
		mActionBar.init();


	}
	
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	
	}

	private void getStackDetailQuestion(boolean showProgress,String questionId){
		if(showProgress)showProgress();
		
		new SvStackOverFlowLoader(this,mPullRefreshScrollView,pgQuestion).getQuestionDetailById(questionId);
		

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
	 * Create a compatible helper that will manipulate the action bar if
	 * available.
	 */
	private UtActionBarHelper createActionBarHelper() {
		return new UtActionBarHelper(this);
	}

}

