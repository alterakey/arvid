package com.gmail.altakey.arvid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;

import java.util.List;
import java.util.LinkedList;

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

	private List<String> searchFolders = new LinkedList<String>();

	private void updateSearchFolderList()
	{
		StringBuilder builder = new StringBuilder();
		for (String s : this.searchFolders)
		{
			builder.append(s);
			builder.append("\n");
		}
		
		TextView view = (TextView)findViewById(R.id.main_folder_list);
		view.setText(builder.toString());
	}

	private void addToSearchFolder(String folder)
	{
		if (folder == null)
			return;

		this.searchFolders.add(folder);
		this.updateSearchFolderList();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (data != null)
		{
			Log.d("MA.oAR", String.format("req: %d, res: %d, data: %s", requestCode, resultCode, data.getData()));

			Uri content = data.getData();
			if (content.getScheme().equals("file"))
				this.addToSearchFolder(content.getPath());
		}
	}
}
