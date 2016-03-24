package xyz.arifguler.strappv13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Giris extends AppCompatActivity {
    Button g_login,g_register,g_four,g_map,g_liste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        g_login=(Button)findViewById(R.id.g_login);
        g_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activityChangeIntent = new Intent(Giris.this, Login.class);
                startActivity(activityChangeIntent);
            }
        });
        g_register=(Button)findViewById(R.id.g_kayit);
        g_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activityChangeIntent = new Intent(Giris.this, Register.class);
                startActivity(activityChangeIntent);
            }
        });
        g_four=(Button)findViewById(R.id.g_bak);
        g_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activityChangeIntent = new Intent(Giris.this, DetailSearch.class);
                startActivity(activityChangeIntent);
            }
        });
        g_map=(Button)findViewById(R.id.g_map);
        g_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activityChangeIntent = new Intent(Giris.this, MapsActivity.class);
                startActivity(activityChangeIntent);
            }
        });
        g_liste=(Button)findViewById(R.id.g_listem);
        g_liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activityChangeIntent = new Intent(Giris.this, List.class);
                startActivity(activityChangeIntent);
            }
        });

    }


}
