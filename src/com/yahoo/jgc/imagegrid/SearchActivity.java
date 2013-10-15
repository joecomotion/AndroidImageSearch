package com.yahoo.jgc.imagegrid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	GridView gvResults;
	EditText etSearch; 
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	long mStart = 0;
	String sSize = "Any", 
		   sColor, sType, sSite;
	static int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		bindView();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position, long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("url", imageResult.getURL());
				startActivity(i);	
			}
		});
	}

	private void bindView() {
		gvResults = (GridView) findViewById(R.id.gvResults);
		etSearch = (EditText)findViewById(R.id.etSearch);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	

	public void onSearch(View v) {
		search();
	}
	
	public void onSettingsClick(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		i.putExtra("size", sSize);
		i.putExtra("color", sColor);
		i.putExtra("type", sType);
		i.putExtra("site", sSite);		
		startActivityForResult(i, REQUEST_CODE);	
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		sSize = data.getStringExtra("size");
	}
	
	private void search() {
		AsyncHttpClient client = new AsyncHttpClient();
		String query = etSearch.getText().toString(),
			   url;
		
		url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&v=1.0&" + 
		      "start=" + mStart + "&q=" + Uri.encode(query); 
		
		if (sSize.contentEquals("Any") != true) {
			url += "&imgsz=" + Uri.encode(query);
		}
		
		Toast.makeText(this, "Searching for '" + query + "'", Toast.LENGTH_SHORT).show();

		
		client.get(url, new JsonHttpResponseHandler() {
		    @Override
		    public void onSuccess(JSONObject response) {
		    	try {
		    		JSONArray results = response.getJSONObject("responseData")
		    							    	.getJSONArray("results");
		    		imageResults.clear();
		    		imageAdapter.addAll(ImageResult.fromJSONArray(results));
		    		Log.i("info", "image results: " + imageResults.toString());

		    		//adapter.setResults(results);		    		
		    	} catch(JSONException e) {
		    		Log.i("info", "caught exception:: " + e);
		    	}	
		    }
		});
		
	}
	
	public void onMore(View v) {
		mStart += 8;
		search();
	}

}
