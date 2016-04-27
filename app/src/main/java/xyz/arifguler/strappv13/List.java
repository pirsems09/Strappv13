package xyz.arifguler.strappv13;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class List extends AppCompatActivity {

    private FloatingActionButton home,kayit,listele;
    String kAdi;

    private ListView listView;
    ArrayList<String> Liste = new ArrayList<String>();
    ArrayList<String> Maker = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.k_listesi);
        home = (FloatingActionButton) findViewById(R.id.fab_home);
        kayit = (FloatingActionButton) findViewById(R.id.list_kayit_ekle);
        listele = (FloatingActionButton) findViewById(R.id.list_look);

        kAdi = Login.kAdim;
        /******------foursquare dan gelen liste değerlerinin okunduğu yer--başlandı--**************/

        Bundle bundleObject = getIntent().getExtras();
        Liste = (ArrayList<String>) bundleObject.getSerializable("alinacakListe");
        Maker = (ArrayList<String>) bundleObject.getSerializable("alinacakMaker");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                Liste);

        listView.setAdapter(arrayAdapter);

        /******------foursquare dan gelen liste değerlerinin okunduğu yer--bitti--**************/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder adb = new AlertDialog.Builder(
                        List.this);
                adb.setTitle("Yapmak istediğiniz işlemi seçin ");
                adb.setMessage("Secilen Deger  "
                        + parent.getItemAtPosition(position));
                adb.setPositiveButton("Yorumla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(List.this, Comment.class);
                        Object listItem = listView.getItemAtPosition(position);
                        i.putExtra("mekan", listItem.toString());
                        startActivity(i);
                    }
                });

                adb.setNegativeButton("Haritaya Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String[] yer = Maker.get(position).split(",");
                        Foursquare.maker_x.add(Double.parseDouble(yer[0]));
                        Foursquare.maker_y.add(Double.parseDouble(yer[1]));
                        //Bundle veya başka bi yöntem ile yada dizi ye atmak onu göndermek ile yapılacak
                    }
                });
                adb.setNeutralButton("Nerede Gör", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(List.this, MapsActivity.class);
                        startActivity(i);
                    }
                });
                adb.show();


            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(), Giris.class);
                startActivity(ıntent);
            }
        });
        if (kAdi.equals("")) {
            Toast.makeText(getBaseContext(),"Lütfen Kayıt Olun !",Toast.LENGTH_LONG).show();
        } else{
            kayit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SetMessageDataToServer(getBaseContext()).execute();
                }
            });
    }
    }

    /*************************************************************************************************/
    public class SetMessageDataToServer  extends AsyncTask<String,Void,String> {

        Context context;
        public SetMessageDataToServer(Context context) {this.context = context;}

        @Override
        protected void onPreExecute()
        {}

        @Override
        protected String doInBackground(String... arg0) {
           // for(int i=0;i<Liste.size();i++){
                try{

                String un=Login.kAdim;
                String ii=Liste.get(1);
                String maker=Maker.get(1);



                    Toast.makeText(getBaseContext(),maker+ii+un,Toast.LENGTH_LONG).show();


                String link="http://arifguler.xyz/foursquare/Liste.php";

                String data  = URLEncoder.encode("USERNAME", "UTF-8") + "=" + URLEncoder.encode(ii, "UTF-8");
                data += "&" + URLEncoder.encode("LISTE", "UTF-8") + "=" + URLEncoder.encode(un, "UTF-8");
                data += "&" + URLEncoder.encode("MAKER", "UTF-8") + "=" + URLEncoder.encode(maker, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return  sb.toString();
            }

            catch(Exception e){
                Log.e("ComingMesaage", "doInBackground:" + e.toString());
            }//}
            return "ok";
        }

        @Override
        protected void onPostExecute(String result)
        {
            if(result.equals("ok"))
            {
                Toast.makeText(getBaseContext(), "Kayıt Yapildi !" + result,
                        Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getBaseContext(), "Istenmeyen Hatalar ile Karsilasildi" + result,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    /*************************************************************************************************/



}
