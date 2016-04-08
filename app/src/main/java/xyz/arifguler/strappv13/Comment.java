package xyz.arifguler.strappv13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Comment extends AppCompatActivity {
Bundle bundle=new Bundle();
    EditText yorum;
    TextView textView;
    Button save;
    String cm,cy;
    static String un="unregistered";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        textView=(TextView)findViewById(R.id.textView10);
        yorum=(EditText)findViewById(R.id.yorum_text);
        save=(Button)findViewById(R.id.c_save);

        Bundle paket  = new  Bundle();
        paket = getIntent().getExtras();
        cy= paket.getString("mekan");

        String un=sharedpreferences.getString("kAdi", null);

        Toast.makeText(getBaseContext(),un.toString()+
                "fwfw",Toast.LENGTH_LONG).show();
        textView.setText(cy+ " mekani hakkinda düşüncelerinizi burada kayit altina alabilirsiniz ");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cm = yorum.getText().toString();
                new VeriyiYolla(getBaseContext()).execute();

            }
        });

    }

    public class VeriyiYolla  extends AsyncTask<String,Void,String> {
        Context context;
        public VeriyiYolla(Context context) {this.context = context;}


        @Override
        protected void onPreExecute()
        {}
        @Override
        protected String doInBackground(String... arg0) {

            try{
                String link="http://arifguler.xyz/Comment.php";

                String data = URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(cm, "UTF-8");
                data += "&" + URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(cy, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(un, "UTF-8");

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
                Toast.makeText(getBaseContext(), "Kayit Yapıldı. ", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),List.class);
                startActivity(intent);

            }
            else
            {
                Toast.makeText(getBaseContext(), "Kayit Yapilmadi Tekrar Deneyin!", Toast.LENGTH_LONG).show();
            }
        }
    }


}
