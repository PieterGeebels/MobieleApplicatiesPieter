package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;


import java.io.PrintWriter;
import java.util.Map;

import service.Facade;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class UploadFile extends AsyncTask<Void, Void, Boolean> {

	
	private DropboxAPI<?> dropbox;
	private String path;
	private Context context;
	private String soort;
	
	private Facade facade;
	private User user;

	public UploadFile(Context context, DropboxAPI<?> dropbox,
			String path,String soort, int sessionID) {
		this.context = context.getApplicationContext();
		this.dropbox = dropbox;
		this.path = path;
		this.soort=soort;		
		this.facade = Facade.getInstance();
		this.user = this.facade.getUser(sessionID);
	}
	@Override
	protected Boolean doInBackground(Void... params) {
		final File tempDir = context.getCacheDir();
		File tempFile;
		FileWriter fr;
		try {
			tempFile = File.createTempFile("file", ".txt", tempDir);
			fr = new FileWriter(tempFile);
			fr.write("Hierin komt de factuur & aanmaning" + System.getProperty("line.separator"));
			
			
			for (Map.Entry<String, Integer> entry : this.user.getBedragen().entrySet()){
				fr.write(entry.getKey()+ "      "+entry.getValue()+"€" + System.getProperty("line.separator"));
			}
			
			fr.close();

			FileInputStream fileInputStream = new FileInputStream(tempFile);
			if(soort.equals("factuur")){
				
			dropbox.putFile(path + "factuur" + "_" + this.user.getNaam() +".txt", fileInputStream,
					tempFile.length(), null, null);}
			
			tempFile.delete();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DropboxException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			Toast.makeText(context, "File Uploaded Successfully!",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, "Failed to upload file", Toast.LENGTH_LONG)
					.show();
		}
	}
}