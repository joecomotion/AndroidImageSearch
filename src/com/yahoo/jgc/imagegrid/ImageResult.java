package com.yahoo.jgc.imagegrid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	String url;
	String thumbURL;
	
	public ImageResult(JSONObject json) {
		try {
			this.url = json.getString("url");
			this.thumbURL = json.getString("tbUrl");
			
		} catch (JSONException e){
			this.url = null;
			this.thumbURL = null;
		}
		
	}
	
	public String getURL() {
		return url;
	}
	
	public String getThumbURL() {
		return thumbURL;
	}
	
	public String toString() {
		return this.thumbURL;
	}

	public static ArrayList<ImageResult> fromJSONArray(JSONArray json) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for(int i = 0; i < json.length(); i++) {
			try {
				results.add(new ImageResult(json.getJSONObject(i)));
			} catch (JSONException e){
				e.printStackTrace();
			}
		}
		
		return results;
	}
}
