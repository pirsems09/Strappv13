package xyz.arifguler.strappv13;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Comment_List extends AppCompatActivity {
    ListView listView;
    String un="";
    public List<String> profillist = new ArrayList<String>();
    private String url = "http://arifguler.xyz/foursquare/CommentList.php";
    private String jsonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment__list);

        listView = (ListView) findViewById(R.id.comment_list);
        un=Login.kAdim;
        accessWebService();

    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            List<NameValuePair> eklenecek =new ArrayList<NameValuePair>(0);
            eklenecek.add(new BasicNameValuePair("username",un));

            try {
                httppost.setEntity(new UrlEncodedFormEntity(eklenecek,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url});
    }

    //JSon Olarak Veri Cekme

    public void ListDrwaer() {
        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");
            for (int i = 0; i < jsonMainNode.length(); i++) {

                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String yorum = jsonChildNode.optString("yorum")+"\n"+jsonChildNode.optString("companyName");

                profillist.add(yorum);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        profillist );

                listView.setAdapter(arrayAdapter);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
