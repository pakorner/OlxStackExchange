package com.olx.dev.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.olx.dev.R;
import com.olx.dev.adapter.AdtGetAllQuestion;
import com.olx.dev.font.FCustomFonts;
import com.olx.dev.util.UtConfig;

public class SvStackOverFlowLoader {
	private Activity ac;
	private PullToRefreshListView mPullRefreshListView;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private ProgressBar pg;
	
	public SvStackOverFlowLoader(Activity activity, PullToRefreshListView mPullRefreshListView, ProgressBar progress){
		ac = activity ;
		pg = progress;
		this.mPullRefreshListView = mPullRefreshListView;

	}
	
	public SvStackOverFlowLoader(Activity activity, PullToRefreshScrollView mPullRefreshScrollView, ProgressBar progress){
		ac = activity ;
		pg = progress;
		this.mPullRefreshScrollView = mPullRefreshScrollView;

	}
	
	public void getQuestionAll(){
		new getAllQuestionLoader().execute();
	}
	
	public void getQuestionBySearch(String criteria){
		new getQuestionBySearchLoader().execute(criteria);
	}
	
	public void getQuestionDetailById(String questionId){
		new getQuestionByIDLoader().execute(questionId);
	}
	
	 private class getAllQuestionLoader extends AsyncTask<String, Integer, JSONObject> 
		{
		    protected JSONObject doInBackground(String... progres) 
		    {  	
		    	JSONObject jsonResult = this.onLoadDateformation();
		    	return jsonResult;
		    }

		    protected void onProgressUpdate(Integer... progress) 
		    {
		    
		    }

		    protected void onPostExecute(JSONObject json) 
		    {
		    	 if(json != null){
		    		 
		    		 final ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>(); 
		    	 
         			try {
         				  	
         				if (!json.isNull("items")) {
								JSONArray c = json.getJSONArray("items");
		           				for(int i=0;i<c.length();i++){
		           					HashMap<String, String> hash = new HashMap<String, String>();
		           					JSONObject jMatch = c.getJSONObject(i);
		           					String question = jMatch.getString("title");
		           					String score = jMatch.getString("score");
		           					String answer = jMatch.getString("answer_count");
		           					String platform = jMatch.isNull("tags")?"":jMatch.getJSONArray("tags").getString(0);
		           					String time = jMatch.getString("creation_date");
		           					String questionId = jMatch.getString("question_id");
		           					hash.put("question",question);
		           					hash.put("score",score);
		           					hash.put("answer", answer);	
		           					hash.put("platform", platform);
		           					hash.put("time", time);
		           					hash.put("questionId", questionId);
		           					arrayList.add(hash);   
		           				}          				     				
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
         			
         			ListView actualListView = mPullRefreshListView.getRefreshableView();
         			AdtGetAllQuestion adapter = new AdtGetAllQuestion(ac, arrayList);
         			actualListView.setAdapter(adapter);
         			mPullRefreshListView.onRefreshComplete();
         			
         			if(pg!=null && pg.isShown()){
         				pg.setVisibility(View.GONE);
         			}
		    	 }
		    }
		    
		    private JSONObject onLoadDateformation() 
		    {
		    	JSONObject json = null;   
		    	try {
					 
		    			HttpUriRequest request = new HttpGet(UtConfig.LINK_ALL_QUESTION);
		    		    request.addHeader("Accept-Encoding", "gzip");
		    		    HttpClient httpClient = new DefaultHttpClient();
		    		    HttpResponse response = httpClient.execute(request);

		    		    InputStream instream = response.getEntity().getContent();
		    		    org.apache.http.Header contentEncoding = response.getFirstHeader("Content-Encoding");

		    		   
		    		    if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
		    		        BufferedReader rd = new BufferedReader(new InputStreamReader(new GZIPInputStream(instream)));
		    		        String jsonText = readAll(rd);
		    		          if (jsonText.contains("</div>")) {
		    		              json = new JSONObject(jsonText.split("</div>")[1]);
		    		          } else {
		    		        	  
		    		              json = new JSONObject(jsonText);
		    		          }
		    		    }
		    		     
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    return json;
		    }
	    }
	 
	 private class getQuestionBySearchLoader extends AsyncTask<String, Integer, JSONObject> 
		{
		    protected JSONObject doInBackground(String... criteria) 
		    {  	
		    	JSONObject jsonResult = this.onLoadDateformation(criteria[0]);
		    	return jsonResult;
		    }

		    protected void onProgressUpdate(Integer... progress) 
		    {
		    
		    }

		    protected void onPostExecute(JSONObject json) 
		    {
		    	 if(json != null){
		    		 
		    		 final ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>(); 
		    	 
      			try {
      				  	
      				if (!json.isNull("items")) {
								JSONArray c = json.getJSONArray("items");
		           				for(int i=0;i<c.length();i++){
		           					HashMap<String, String> hash = new HashMap<String, String>();
		           					JSONObject jMatch = c.getJSONObject(i);
		           					String question = jMatch.getString("title");
		           					String score = jMatch.getString("score");
		           					String answer = jMatch.getString("answer_count");
		           					String platform = jMatch.isNull("tags")?"":jMatch.getJSONArray("tags").getString(0);
		           					String time = jMatch.getString("creation_date");
		           					String questionId = jMatch.getString("question_id");
		           					hash.put("question",question);
		           					hash.put("score",score);
		           					hash.put("answer", answer);	
		           					hash.put("platform", platform);
		           					hash.put("time", time);
		           					hash.put("questionId", questionId);
		           					arrayList.add(hash);   
		           				}          				     				
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
      			
      			ListView actualListView = mPullRefreshListView.getRefreshableView();
      			AdtGetAllQuestion adapter = new AdtGetAllQuestion(ac, arrayList);
      			actualListView.setAdapter(adapter);
      			mPullRefreshListView.onRefreshComplete();
      			
      			if(pg!=null && pg.isShown()){
      				pg.setVisibility(View.GONE);
      			}
		    	 }
		    }
		    
		    private JSONObject onLoadDateformation(String criteria) 
		    {
		    	JSONObject json = null;   
		    	try {
					 
		    			HttpUriRequest request = new HttpGet(UtConfig.LINK_SEARCH_QUESTION+criteria);
		    		    request.addHeader("Accept-Encoding", "gzip");
		    		    HttpClient httpClient = new DefaultHttpClient();
		    		    HttpResponse response = httpClient.execute(request);

		    		    InputStream instream = response.getEntity().getContent();
		    		    org.apache.http.Header contentEncoding = response.getFirstHeader("Content-Encoding");

		    		   
		    		    if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
		    		        BufferedReader rd = new BufferedReader(new InputStreamReader(new GZIPInputStream(instream)));
		    		        String jsonText = readAll(rd);
		    		          if (jsonText.contains("</div>")) {
		    		              json = new JSONObject(jsonText.split("</div>")[1]);
		    		          } else {
		    		        	  
		    		              json = new JSONObject(jsonText);
		    		          }
		    		    }
		    		     
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    return json;
		    }
	    }
	 
	 private class getQuestionByIDLoader extends AsyncTask<String, Integer, JSONObject> 
		{
		 
		 	LinearLayout lay_queston_detail;
		 	TextView txt_question;
	        TextView txt_score;
	        TextView txt_answer; 
	        TextView txt_platform;
	        TextView txt_body;
	        
		    protected JSONObject doInBackground(String... id) 
		    {  	
		    	JSONObject jsonResult = this.onLoadDateformation(id[0]);
		    	return jsonResult;
		    }

		    protected void onProgressUpdate(Integer... progress) 
		    {
		    
		    }

		    protected void onPostExecute(JSONObject json) 
		    {
		    	 if(json != null){
		    		 
		    		 final ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>(); 
		    	 
   			try {
   				  	
   				if (!json.isNull("items")) {
								JSONArray c = json.getJSONArray("items");
		           				for(int i=0;i<c.length();i++){
		           					HashMap<String, String> hash = new HashMap<String, String>();
		           					JSONObject jMatch = c.getJSONObject(i);
		           					String question = jMatch.getString("title");
		           					String score = jMatch.getString("score");
		           					String answer = jMatch.getString("answer_count");
		           					String platform = jMatch.isNull("tags")?"":jMatch.getJSONArray("tags").getString(0);
		           					String time = jMatch.getString("creation_date");
		           					String body = jMatch.getString("body");
		           					
		           					hash.put("question",question);
		           					hash.put("score",score);
		           					hash.put("answer", answer);	
		           					hash.put("platform", platform);
		           					hash.put("time", time);
		           					hash.put("body", body);
		           					arrayList.add(hash);   
		           				}          				     				
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
   			
			   			lay_queston_detail = (LinearLayout)ac.findViewById(R.id.lay_queston_detail);
			   			
			   			txt_question = (TextView)ac.findViewById(R.id.txt_question);
			        	txt_score = (TextView)ac.findViewById(R.id.txt_score);
			        	txt_answer = (TextView)ac.findViewById(R.id.txt_answer);
			        	txt_platform = (TextView)ac.findViewById(R.id.txt_platform);
			        	txt_body = (TextView)ac.findViewById(R.id.txt_question_detail);
			        	
			        	txt_score.setTypeface(FCustomFonts.getTypeFaceFuturaLight(ac));
			        	txt_answer.setTypeface(FCustomFonts.getTypeFaceFuturaLight(ac));
			        	txt_platform.setTypeface(FCustomFonts.getTypeFaceFuturaLight(ac));
			        	
			        	txt_question.setText(arrayList!=null?arrayList.get(0).get("question"):"");
			        	txt_score.setText(arrayList!=null?arrayList.get(0).get("score"):"");
			        	txt_answer.setText(arrayList!=null?arrayList.get(0).get("answer"):"");
			        	txt_platform.setText(arrayList!=null?arrayList.get(0).get("platform"):"");
			        	txt_body.setText(Html.fromHtml(arrayList!=null?arrayList.get(0).get("body"):""));
			   			
			   			mPullRefreshScrollView.onRefreshComplete();
   			
			   			if(pg!=null && pg.isShown()){
			   				pg.setVisibility(View.GONE);
			   				lay_queston_detail.setVisibility(View.VISIBLE);
			   			}
		    	 }
		    }
		    
		    private JSONObject onLoadDateformation(String id) 
		    {
		    	JSONObject json = null;   
		    	try {
					 
		    			HttpUriRequest request = new HttpGet(UtConfig.LINK_DETAIL_QUESTION.replace("$", id));
		    		    request.addHeader("Accept-Encoding", "gzip");
		    		    HttpClient httpClient = new DefaultHttpClient();
		    		    HttpResponse response = httpClient.execute(request);

		    		    InputStream instream = response.getEntity().getContent();
		    		    org.apache.http.Header contentEncoding = response.getFirstHeader("Content-Encoding");

		    		   
		    		    if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
		    		        BufferedReader rd = new BufferedReader(new InputStreamReader(new GZIPInputStream(instream)));
		    		        String jsonText = readAll(rd);
		    		          if (jsonText.contains("</div>")) {
		    		              json = new JSONObject(jsonText.split("</div>")[1]);
		    		          } else {
		    		        	  
		    		              json = new JSONObject(jsonText);
		    		          }
		    		    }
		    		     
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    return json;
		    }
	    }
	 
	  private String readAll(BufferedReader rd) throws IOException {
          StringBuilder sb = new StringBuilder();
           String inputLine;

           while ((inputLine = rd.readLine()) != null){
              sb.append(inputLine);
           }

           return sb.toString();
   }
	
	
}
