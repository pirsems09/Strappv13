package xyz.arifguler.strappv13;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    public static ArrayList<Double> maker_x = new ArrayList<Double>();
    public static ArrayList<Double> maker_y = new ArrayList<Double>();
    public static ArrayList<String> yeniListe = new ArrayList<String>();
    public static ArrayList<String> list_maker = new ArrayList<String>();

    public java.util.List<String> yerler = new ArrayList<String>();
    public java.util.List<String> listTitle = new ArrayList<String>();
    ArrayAdapter<String> anaAdapter;
    static String[]yer_adi=new String[30];


    final String CLIENT_ID = "OC3QRT4TYKY3O2AXHTZRCQVX2DEHVC0FVEZ0BMLAGLWX2Q5X";
    final String CLIENT_SECRET = "MTGLKTN105UYFMWSPJJ22FITRCBGG5WR5UFJ4MXILRGEPFFD";

    final String latitude = "38.496480";
    final String longtitude = "39.219903";

    /*--------------------uyelerden gelen detaylar için bundle aracları----------------------*/
    String arama,sehir;
    String gelen;
    Button listemigör,haritamabak;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foursquare);

        Bundle paket  = new  Bundle();
        paket = getIntent().getExtras();
        sehir= paket.getString("sehir");
        arama= paket.getString("arama");
        gelen= paket.getString("ekle");

        listemigör=(Button)findViewById(R.id.list_go);
        haritamabak=(Button)findViewById(R.id.map_go);

        new fourquare().execute();

        haritamabak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Foursquare.this, MapsActivity.class);
                startActivity(intent);

            }
        });
        listemigör.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Foursquare.this,List.class);
                intent.putStringArrayListExtra("alinacakListe",yeniListe);
                intent.putStringArrayListExtra("alinacakMaker",list_maker);
                startActivity(intent);

            }
        });

    }

    private class fourquare extends AsyncTask<View, Void, String> {

        String temp;

        @Override
        protected String doInBackground(View... urls) {
            if(sehir.toString()!=""){
                if(arama.toString().length()>=2){
                    //sehir ve arama
                    temp = makeCall("https://api.foursquare.com/v2/venues/search?near="+sehir+"&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20160316"+"&query="+arama);
                }
                else{
                    //arama yok sehir ve radio
                    temp = makeCall("https://api.foursquare.com/v2/venues/search?near="+sehir+"&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20160316"+"&query="+gelen);
                }
            }

            else{
                Toast.makeText(getBaseContext(),"ikisinide.......",Toast.LENGTH_LONG).show();
            }
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

                    listTitle.add(i, venuesList.get(i).getName());
                    yerler.add(venuesList.get(i).getLat() + ","+ venuesList.get(i).getLng());

                }
                anaAdapter = new ArrayAdapter<String>(Foursquare.this, R.layout.row_layout, R.id.listText, listTitle);
                setListAdapter(anaAdapter);
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
                                if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")) {
                                        poi.setLat(jsonArray.getJSONObject(i).getJSONObject("location").getDouble("lat"));
                                    poi.setLng(jsonArray.getJSONObject(i).getJSONObject("location").getDouble("lng"));

                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("city")) {
                                        poi.setCity(jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                    }
                                }

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
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Yapmak istediğiniz işlemi seçin ");


        builder.setPositiveButton("Haritaya Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(String.valueOf(yerler.get(position)).equals("0.0,0.0")){
                    Toast.makeText(getBaseContext(),"Belli Bir Adres Bulanamadı !",Toast.LENGTH_LONG).show();
                }
                else{
                    String[] yer =yerler.get(position).split(",");
                    maker_x.add(Double.parseDouble(yer[0]));
                    maker_y.add(Double.parseDouble(yer[1]));

                }

            }
        });


        builder.setNeutralButton("Listeme Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                yeniListe.add(l.getItemAtPosition(position).toString());
                list_maker.add(yerler.get(position));

            }
        });
        builder.show();
        super.onListItemClick(l, v, position, id);
     }
}
