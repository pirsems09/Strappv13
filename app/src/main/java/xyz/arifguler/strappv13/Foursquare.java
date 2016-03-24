package xyz.arifguler.strappv13;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Foursquare extends ListActivity {

    ArrayList<FoursquareVenue> venuesList;
    public java.util.List<String> secenekler = new ArrayList<String>();
    public java.util.List<String> yerler = new ArrayList<String>();
    public java.util.List<String> listTitle = new ArrayList<String>();
    ArrayAdapter<String> myAdapter;

    final String CLIENT_ID = "OC3QRT4TYKY3O2AXHTZRCQVX2DEHVC0FVEZ0BMLAGLWX2Q5X";
    final String CLIENT_SECRET = "MTGLKTN105UYFMWSPJJ22FITRCBGG5WR5UFJ4MXILRGEPFFD";

    final String latitude = "38.496480";
    final String longtitude = "39.219903";

    /*--------------------uyelerden gelen detaylar için bundle aracları----------------------*/
    String arama,sehir;
    String alisveris,eylence,yemek,cafe,camii,otel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foursquare);

        Bundle paket  = new  Bundle();
        paket = getIntent().getExtras();
        sehir= paket.getString("sehir");
        arama= paket.getString("arama");
        cafe= paket.getString("cafe");
        camii= paket.getString("camii");
        yemek= paket.getString("yemek");
        eylence= paket.getString("eylence");
        alisveris= paket.getString("alisveris");
        otel= paket.getString("otel");

        //             temp = makeCall("https://api.foursquare.com/v2/venues/search?near="+arama+"&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20160316"+"&q="+"Mosque");

        new fourquare().execute();
        new fourquare().execute();


    }

    private class fourquare extends AsyncTask<View, Void, String> {

        String temp;

        @Override
        protected String doInBackground(View... urls) {
        String ekle="";
            if(otel!="")
            {
                ekle+="q=Hotel";
            }
            if(cafe!="")
            {
                ekle+="&q=Café";
            }
            if(camii!="")
            {
                ekle+="&q=Mosque";
            }
            if(eylence!="")
            {
                ekle+="&q=Fun";
            }
            if(yemek!="")
            {
                ekle+="&q=Food";
            }
            if(alisveris!="")
            {
                ekle+=alisveris;
            }


            temp = makeCall("https://api.foursquare.com/v2/venues/explore?mode=url&near=" + arama + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20160316" + "&q=Mosque");

            return "";
        }

        @Override
        protected void onPreExecute() {
                // we can start a progress bar here
        }

        @Override
        protected void onPostExecute(String result) {
            if (temp == null) {
                Toast.makeText(getBaseContext(),"Sonuç Yok",Toast.LENGTH_LONG).show();

            } else {

                venuesList = (ArrayList<FoursquareVenue>) parseFoursquare(temp);

                for (int i = 0; i < venuesList.size(); i++) {
                    for (int j = i; j < venuesList.size(); j++) {

                        if (listTitle.contains(venuesList.get(j).getName())==false) {
                            listTitle.add(j, venuesList.get(j).getName());
                        }
                    }

                }
                    //yerler.add(i, venuesList.get(i).getLat() + ""+ venuesList.get(i).getLng());

                myAdapter = new ArrayAdapter<String>(Foursquare.this, R.layout.row_layout, R.id.listText, listTitle);
                setListAdapter(myAdapter);
            }

        }
    }

    public static String makeCall(String url) {

        StringBuffer buffer_string = new StringBuffer(url);
        String replyString = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(buffer_string.toString());

        try {
            HttpResponse response = httpclient.execute(httpget);
            InputStream is = response.getEntity().getContent();

            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(20);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            replyString = new String(baf.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyString.trim();
    }

    private static ArrayList<FoursquareVenue> parseFoursquare(final String response) {

        ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoursquareVenue poi = new FoursquareVenue();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).getString("name"));

                            //************konum bilgisinin diziye atıldığı yer*****Başla*****//
                               /* if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")) {
                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("lat")) {
                                       // poi.setAddress(jsonArray.getJSONObject(i).getJSONObject("location").getString("address"));
                                        poi.setLat(jsonArray.getJSONObject(i).getJSONObject("location").getDouble("lat"));
                                        poi.setLng(jsonArray.getJSONObject(i).getJSONObject("location").getDouble("lng"));
                                    }
                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("city")) {
                                        poi.setCity(jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                    }
                                }*/

                            temp.add(poi);

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<FoursquareVenue>();
        }
        return temp;

    }


    @Override
    protected void onListItemClick(final ListView l, View v, final int position, long id)
    {
        //String selection = l.getItemAtPosition(position).toString();
        //Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
        //String item=(String)getListAdapter().getItem(position);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Yapmak istediğiniz işlemi seçin " + l.getCount());
        builder.setPositiveButton("Haritaya Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> maker_listesi = new ArrayList<String>();
                maker_listesi.add(l.getItemAtPosition(position).toString());
            }
        });
        builder.setNeutralButton("Listeme Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Foursquare.this, MapsActivity.class);
                startActivity(i);
            }
        });
        builder.show();

        super.onListItemClick(l, v, position, id);
        //
    }
    /// Aşağıdaki kod parçacıcğı listview e tıklama sonucu gerçekleşmesini istediğim durumda ikinci altarnatif olaarak duracaktır

/*
  @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String item=(String)getListAdapter().getItem(position);
        String[] array = item.split("(:[0-9])");
        String la,lang,first = null,sec = null;

        for(String str : array)
        {
            System.out.println(str.replace(':',' '));
            la = str.substring(str.lastIndexOf(":"));
            lang= str.substring(str.lastIndexOf(";"));
            first=la.substring(1,15);
            sec=lang.substring(1,15);
        }

        Toast.makeText(getApplicationContext(),first+sec, Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Forsquare.this, MapsActivity.class);
        intent.putExtra("ilk", first.toString());
        intent.putExtra("son", sec.toString());
        startActivity(intent);

    }
 */

}
