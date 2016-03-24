package xyz.arifguler.strappv13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    public java.util.List<String> profillist = new ArrayList<String>();

    private ListView  listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView=(ListView)findViewById(R.id.k_listesi);

        profillist.add("Ankara");
        profillist.add("İzmir");
        profillist.add("İstanbul");
        profillist.add("Aydın");
        profillist.add("Muğla");
        profillist.add("Denizli");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                profillist );

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

               AlertDialog.Builder adb = new AlertDialog.Builder(
                        List.this);
                adb.setTitle("Yapmak istediğiniz işlemi seçin ");
                adb.setMessage("Secilen Deger  "
                        + parent.getItemAtPosition(position));
                adb.setPositiveButton("Cık", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                adb.setNegativeButton("Haritaya Ekle       ", new DialogInterface.OnClickListener() {
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

    }

}
