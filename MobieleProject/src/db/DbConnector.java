package db;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;


import android.util.Log;

public class DbConnector {

	public JSONArray GetData(String type) {
		String url = "";

		if (type.equals("users")) {
			url = "http://www.schildershoekje.be/getUsers.php";
		} else if (type.equals("aanwezigheden")) {
			url = "http://www.schildershoekje.be/getAanwezigheden.php";
		} else if (type.equals("bedragen")) {
			url = "http://www.schildershoekje.be/getBedragen.php";
		}

		HttpEntity httpEntity = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);

			httpEntity = httpResponse.getEntity();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONArray jsonArray = null;

		if (httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);

				Log.e("Entity Response  : ", entityResponse);

				jsonArray = new JSONArray(entityResponse);

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		return jsonArray;

	}

	public Boolean SetData(List<NameValuePair> params, String type) {
		System.out.println("Gepasseerd in setData");
		
		String url = "";
		
		
		if(type.equals("aanwezigheden")){

		url = "http://www.schildershoekje.be/setAanwezigheden.php";
		}else if(type.equals("bedragen")){
		url = "http://www.schildershoekje.be/setBedragen.php";
		}

		HttpEntity httpEntity = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient(); 

			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse httpResponse = httpClient.execute(httpPost);

			httpEntity = httpResponse.getEntity();
			String entityResponse = EntityUtils.toString(httpEntity);

			Log.e("Entity Response  : ", entityResponse);
			return true;

		} catch (ClientProtocolException e) {

			
			e.printStackTrace();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}