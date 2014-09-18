package com.olx.dev.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.olx.dev.R;
import com.olx.dev.activity.ActViewQuestionDetail;
import com.olx.dev.font.FCustomFonts;

public class AdtGetAllQuestion extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, String>> d;
	

	public AdtGetAllQuestion(Context context, ArrayList<HashMap<String, String>> data) {
		mContext = context;
		d = data;}
	
	@Override
	  public int getCount() {
	   return d.size();
	  }

	  @Override
	  public Object getItem(int position) {
	   return d.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
	   return position;
	  }

	  @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		LayoutInflater inflater;
		  if (convertView==null) {
				inflater =LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.row_question, null);
	        	holder = new ViewHolder();
	        	
	        	holder.txt_question = (TextView)convertView.findViewById(R.id.txt_question);
	        	holder.txt_score = (TextView)convertView.findViewById(R.id.txt_score);
	        	holder.txt_answer = (TextView)convertView.findViewById(R.id.txt_answer);
	        	holder.txt_platform = (TextView)convertView.findViewById(R.id.txt_platform);
	        	
	        	
	        	holder.txt_score.setTypeface(FCustomFonts.getTypeFaceFuturaLight(mContext));
	        	holder.txt_answer.setTypeface(FCustomFonts.getTypeFaceFuturaLight(mContext));
	        	holder.txt_platform.setTypeface(FCustomFonts.getTypeFaceFuturaLight(mContext));
	        	convertView.setTag(holder);
		}	  
		  else{
	        	holder =(ViewHolder)convertView.getTag();
		  }
		  
		  try{
			   
			   holder.txt_question.setText(d.get(position).get("question"));
			   holder.txt_score.setText(d.get(position).get("score"));
			   holder.txt_answer.setText(d.get(position).get("answer"));
			   holder.txt_platform.setText(d.get(position).get("platform"));
			   
			   convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(mContext, ActViewQuestionDetail.class);
					i.putExtra("questionId", d.get(position).get("questionId").toString());
					mContext.startActivity(i);
					
				}
			});
		  }catch (Exception e){
			  
		  }
	   return convertView;
	  }
	  
	   static class ViewHolder{
	
	        TextView txt_question;
	        TextView txt_score;
	        TextView txt_answer; 
	        TextView txt_platform;
	    }
}