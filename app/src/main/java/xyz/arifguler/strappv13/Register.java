package xyz.arifguler.strappv13;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {
    TextView sorgu;
    EditText isim, soyisim, username, mail, password,gelen;
    Button register;
    String ii,si, un, ml, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        isim = (EditText) findViewById(R.id.rAdi);
        soyisim = (EditText) findViewById(R.id.rsoyadi);
        username = (EditText) findViewById(R.id.rKullaniciAdi);
        mail = (EditText) findViewById(R.id.rMail);
        password = (EditText) findViewById(R.id.rSifre);
        register = (Button) findViewById(R.id.rregister);

        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (    isim.getText().length() != 0 &&
                        username.getText().length() != 0 &&
                        mail.getText().length() != 0 &&
                        password.getText().length() != 0 &&
                        soyisim.getText().length() != 0
                        )
                {

                    ii = isim.getText().toString();
                    un = username.getText().toString();
                    ml = mail.getText().toString();
                    pw = password.getText().toString();
                    si = soyisim.getText().toString();

                    new SetMessageDataToServer(getBaseContext()).execute();

                }

                else {
                    Toast.makeText(getBaseContext(), "Bos Birakmayiniz !",
                            Toast.LENGTH_LONG).show();
                }


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
                String link="http://arifguler.xyz/Register.php";

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
