package xyz.arifguler.strappv13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    public java.util.List<String> profillist = new ArrayList<String>();
    private FloatingActionButton home;
    private ListView listView;
    String kAdi="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.k_listesi);
        home = (FloatingActionButton) findViewById(R.id.fab_list);

        profillist.add("Ankara");
        profillist.add("İzmir");
        profillist.add("İstanbul");
        profillist.add("Aydın");
        profillist.add("Muğla");
        profillist.add("Denizli");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                profillist);

        listView.setAdapter(arrayAdapter);


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
                       /* if (username == "null") {
                            Toast.makeText(getBaseContext(), username+
                                    " Bu özellik için Kayıt Kullanıcı olmalısınız  ", Toast.LENGTH_LONG).show();
                        } else {*/
                            Intent i = new Intent(List.this, Comment.class);
                            Object listItem = listView.getItemAtPosition(position);
                            i.putExtra("mekan", listItem.toString());
                            i.putExtra("kAdi", kAdi);
                            startActivity(i);

                    }
                });

                adb.setNegativeButton("Haritaya Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                // Object listItem = listView.getItemAtPosition(position);

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


}
