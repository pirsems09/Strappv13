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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {

    EditText userNameET, passWordET;
    Button btnSignUp,btnSignRegister;
    String pw, un;
    public static String kAdim="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameET = (EditText) findViewById(R.id.lusername);
        passWordET = (EditText) findViewById(R.id.lpassword);
        btnSignUp = (Button) findViewById(R.id.lgiris);
        btnSignRegister = (Button) findViewById(R.id.lkayitol);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNameET.getText().length() != 0 ) {
                    if (passWordET.getText().length() != 0) {

                        un = userNameET.getText().toString();
                        pw = passWordET.getText().toString();

                        new DataToServer(getBaseContext()).execute();

                    }
                }
            }
        });

        btnSignRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
    public class DataToServer  extends AsyncTask<String,Void,String> {

        Context context;

        public DataToServer(Context context) {this.context = context;}

        @Override
        protected void onPreExecute()
        {}

        @Override
        protected String doInBackground(String... arg0) {

            try{
                String link="http://arifguler.xyz/foursquare/loginn.php";

                String data = URLEncoder.encode("USERNAME", "UTF-8") + "=" + URLEncoder.encode(un, "UTF-8");
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
            return "not";
        }

        @Override
        protected void onPostExecute(String result)
        {
            if(result.equals("ok"))
            {
                Intent intent = new Intent(getApplicationContext(),Profil.class);

                kAdim=un.toString();
                Toast.makeText(getBaseContext(),kAdim.toString(),Toast.LENGTH_LONG).show();
                intent.putExtra("kAdi", un.toString());
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getBaseContext(), "Giris Yapilmadi Tekrar Deneyin!", Toast.LENGTH_LONG).show();

                userNameET.setText("");
                passWordET.setText("");

            }
        }
    }

}
