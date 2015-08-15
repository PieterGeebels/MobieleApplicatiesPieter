package db;

import java.io.IOException;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
 
import android.util.Log;
 
public class DbConnector {
	
        public JSONArray GetData(String type){
        	String url = "";
        	
        	if(type.equals("users")){         
        		url = "http://www.schildershoekje.be/getUsers.php"; 
        	}else if(type.equals("aanwezigheden")){
        		url = "http://www.schildershoekje.be/getAanwezigheden.php";
        	}else if(type.equals("bedragen")){
        		url = "http://www.schildershoekje.be/getBedragen.php";
        	}
        
 
        HttpEntity httpEntity = null;
 
        try
        {
 
            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);
 
            HttpResponse httpResponse = httpClient.execute(httpGet);
 
            httpEntity = httpResponse.getEntity();
 
 
 
        } catch (ClientProtocolException e) {
 
            // Signals error in http protocol
            e.printStackTrace();
 
            //Log Errors Here
 
 
 
        } catch (IOException e) {
            e.printStackTrace();
        }
 
 
        // Convert HttpEntity into JSON Array
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
}