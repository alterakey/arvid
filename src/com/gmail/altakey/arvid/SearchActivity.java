package com.gmail.altakey.arvid;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SearchActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// Handle the normal search query case
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.d("SA.oC", String.format("would do search with query: %s", query));
		} else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			// Handle a suggestions click (because the suggestions all use ACTION_VIEW)
			Uri data = intent.getData();
			Log.d("SA.oC", String.format("viewing: %s", data.toString()));
		}
    }
}
