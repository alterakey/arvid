package com.gmail.altakey.arvid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	public void onSelectFolder(View view)
	{
		try
		{
			Intent intent = new Intent("org.openintents.action.PICK_DIRECTORY");
			intent.setData(Uri.parse("file:///sdcard"));
			intent.putExtra("org.openintents.extra.TITLE", "Please select a folder");
			intent.putExtra("org.openintents.extra.BUTTON_TEXT", "Use this folder");
			startActivityForResult(intent, 1);
		}
		catch (ActivityNotFoundException e)
		{
			Toast.makeText(this, R.string.err_no_file_manager, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.d("MA.oAR", String.format("req: %d, res: %d, data: %s", requestCode, resultCode, data == null ? "(null)" : data.getData()));
	}
}
