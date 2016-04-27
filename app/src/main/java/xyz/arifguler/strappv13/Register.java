package xyz.arifguler.strappv13;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class Register extends AppCompatActivity {
    TextView sorgu;
    EditText isim, soyisim, username, mail, password,toplam;
    Button register;
    private FloatingActionButton home;
    String ii,si, un, ml, pw;
    ArrayList<String>  islem = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
/***********************************-----------------*****************************/
        islem.add("+");
        islem.add("-");islem.add("*");
        Random rand = new Random();
        String iis=islem.get(rand.nextInt(3));
        int sayi1=rand.nextInt(50)*2;
        int sayi2=rand.nextInt(50);
        toplam=(EditText)findViewById(R.id.toplam);
        sorgu= (TextView) findViewById(R.id.sorgu);
        sorgu.setText(sayi1 + iis.toString() + sayi2+"  isleminin sonucunu giriniz !");
        final int sonuc;
        int sonuc1 = 0;
        if(iis=="+"){
            sonuc1 =sayi1+sayi2;}
        if(iis=="-"){
            sonuc1 =sayi1-sayi2;}
        if(iis=="*"){
            sonuc1 =sayi1*sayi2;}
        sonuc = sonuc1;

        home=(FloatingActionButton)findViewById(R.id.fab_register);
        isim = (EditText) findViewById(R.id.rAdi);
        soyisim = (EditText) findViewById(R.id.rsoyadi);
        username = (EditText) findViewById(R.id.rKullaniciAdi);
        mail = (EditText) findViewById(R.id.rMail);
        password = (EditText) findViewById(R.id.rSifre);
        register = (Button) findViewById(R.id.rregister);
        //if(String.valueOf(sonuc)==toplam.getText().toString()){

        //}
        //  else         Toast.makeText(getBaseContext(),"anlis oldu",Toast.LENGTH_LONG).show();

        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (isim.getText().length() != 0 &&
                        username.getText().length() != 0 &&
                        mail.getText().length() != 0 &&
                        password.getText().length() != 0 &&
                        soyisim.getText().length() != 0 &&
                        sonuc== Integer.valueOf(toplam.getText().toString())
                        ) {
                    ii = isim.getText().toString();
                    un = username.getText().toString();
                    ml = mail.getText().toString();
                    pw = password.getText().toString();
                    si = soyisim.getText().toString();

                    new SetMessageDataToServer(getBaseContext()).execute();
                } else {
                    Toast.makeText(getBaseContext(), "Bos Birakmayiniz !",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(), Giris.class);
                startActivity(ıntent);
            }
        });
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
            try{
                String link="http://arifguler.xyz/foursquare/Register.php";

                String data  = URLEncoder.encode("ISIM", "UTF-8") + "=" + URLEncoder.encode(ii, "UTF-8");
                data += "&" + URLEncoder.encode("USERNAME", "UTF-8") + "=" + URLEncoder.encode(un, "UTF-8");
                data += "&" + URLEncoder.encode("MAIL", "UTF-8") + "=" + URLEncoder.encode(ml, "UTF-8");
                data += "&" + URLEncoder.encode("SOYISIM", "UTF-8") + "=" + URLEncoder.encode(si, "UTF-8");
                data += "&" + URLEncoder.encode("PASSWORD", "UTF-8") + "=" + URLEncoder.encode(pw, "UTF-8");

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
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String result)
        {
            if(result.equals("ok"))
            {
                Toast.makeText(getBaseContext(), "Kayıt Yapildi !" + result,
                        Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);

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