package com.yahoo.jgc.imagegrid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	Context mContext;
	JSONArray mGoogleImageResults;
	
	public ImageAdapter(Context c) {
		mContext = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Uri uriFromPosition(int pos) {
		try {
    		JSONObject result = (JSONObject) mGoogleImageResults.get(0);
    		return Uri.parse(result.getString("tbUrl"));
    	} catch(JSONException e) {
    		Log.i("info", "caught exception: " + e);
    		return Uri.EMPTY;
    	}	
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageURI(uriFromPosition(position));
        return imageView;
	}

	public void setResults(JSONArray results) {
		mGoogleImageResults = results;
		this.notifyDataSetChanged();
	}

}
