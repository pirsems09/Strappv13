package xyz.arifguler.strappv13;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class Profil extends Activity {

    public List<String> profillist = new ArrayList<String>();

    private String jsonResult;
    private FloatingActionButton loca,comment,fours;
    Button cikis;
    private ListView listView;
    ImageView iv;
    int REQUEST_CODE=1;

    private String url = "http://arifguler.xyz/select.php";
    String kullaniciAdiKontrolu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        comment=(FloatingActionButton)findViewById(R.id.fab);
        loca=(FloatingActionButton)findViewById(R.id.fab2);
        fours=(FloatingActionButton)findViewById(R.id.fab3);
        iv=(ImageView)findViewById(R.id.img);
        cikis=(Button)findViewById(R.id.cikis);
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent ıntent = new Intent(getApplicationContext(),Giris.class);
                startActivity(ıntent);
            }
        });

        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(ıntent);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //yorumlar için aktivity oluşturulduğunda yönlendirme yapılacak

                Intent ıntent = new Intent(getApplicationContext(),Comment.class);
                //ıntent.putExtra("",listView.getPositionForView(v));
                startActivity(ıntent);
            }
        });
        fours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(),DetailSearch.class);
                ıntent.putExtra("kAdi",kullaniciAdiKontrolu);
                startActivity(ıntent);
            }
        });
        //resim ekleme-------------
       iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               if (i.resolveActivity(getPackageManager()) != null) {
                   startActivityForResult(i,REQUEST_CODE);
               }
           }
       });

        listView = (ListView) findViewById(R.id.list);
        accessWebService();

        /********************************************/

        Bundle paketim  = new  Bundle();
        paketim = getIntent().getExtras();
        kullaniciAdiKontrolu= paketim.getString("kAdi");

        /********************************************/

    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            List<NameValuePair> eklenecek =new ArrayList<NameValuePair>(0);
            eklenecek.add(new BasicNameValuePair("USERNAME",kullaniciAdiKontrolu));

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
                String isim = jsonChildNode.optString("firstname");
                String username = jsonChildNode.optString("username");
                String mail = jsonChildNode.optString("mail");
                String soyisim = jsonChildNode.optString("lastname");
                String password = jsonChildNode.optString("password");


                profillist.add(username);
                profillist.add(isim);
                profillist.add(soyisim);
                profillist.add(mail);
                profillist.add(password);

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

    public void onActivityResult(int requestcode, int resultcode,Intent data){
        if (requestcode==REQUEST_CODE){
            if(resultcode==RESULT_OK){

                Bundle bundle= new Bundle();
                bundle=data.getExtras();
                Bitmap bitmap;
                bitmap=(Bitmap)bundle.get("data");
                iv.setImageBitmap(bitmap);
                byte[] decodedString = Base64.decode("", Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
        }

    }



}