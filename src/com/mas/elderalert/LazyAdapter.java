package com.mas.elderalert;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private HashMap<String, String> relatives;
	private static LayoutInflater inflater=null;
	//    public ImageLoader imageLoader; 

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data=d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//        imageLoader=new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.list_row, null);

		TextView name = (TextView)vi.findViewById(R.id.title); // title
		TextView status = (TextView)vi.findViewById(R.id.artist); // artist name
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

		relatives = new HashMap<String, String>();
		relatives = data.get(position);

		// Setting all values in listview
		name.setText(relatives.get("name"));
		status.setText(relatives.get("status"));

		if(relatives.get("color").equals("elevate"))
			vi.setBackgroundResource(android.R.color.holo_red_light);
		else
			vi.setBackgroundResource(R.color.abs__background_holo_light);

		vi.setOnTouchListener(new View.OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction()==MotionEvent.ACTION_DOWN)
					if(LazyAdapter.this.relatives.get("color").equals("elevate"))
						v.setBackgroundResource(android.R.color.holo_red_dark);
					else
						v.setBackgroundResource(R.color.abs__holo_blue_light);
				else{
					if(LazyAdapter.this.relatives.get("color").equals("elevate"))
						v.setBackgroundResource(android.R.color.holo_red_light);
					else
						v.setBackgroundResource(R.color.abs__background_holo_light);

				}
				return true;
			}
		});
		//        duration.setText(song.get(CustomizedListView.KEY_DURATION));
		//        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
		return vi;
	}
	

}
