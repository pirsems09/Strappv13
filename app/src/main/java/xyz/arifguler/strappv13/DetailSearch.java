package xyz.arifguler.strappv13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class DetailSearch extends AppCompatActivity {
    Button ara;
    EditText anahtarkelime,anahtarsehir;
    CheckBox otel,camii,cafe,eylence, yemek, alisveris;

    private Bundle bundle =new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        ara=(Button)findViewById(R.id.search);
        anahtarkelime=(EditText)findViewById(R.id.anahtarkelime);
        anahtarsehir=(EditText)findViewById(R.id.sehir);

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u = anahtarkelime.getText().toString();
                String k = anahtarsehir.getText().toString();

                if (u.equals("") || k.equals("")) {

                    Toast.makeText(getBaseContext(), anahtarkelime.getText() +
                            "Bir kelime veya şehir girin lütfen", Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(DetailSearch.this, Foursquare.class);
                    intent.putExtra("arama", u.toString());
                    intent.putExtra("sehir", k.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }


        });


    }
//burayı radyıuton yap

    public void checboxicin(View view){

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked){bundle.putString("otel","otel");}

            case R.id.checkBox2:
                if (checked){bundle.putString("cafe","cafe");}
                // cafe
            case R.id.checkBox3:
                if (checked){bundle.putString("camii","camii");}
                //camii
            case R.id.checkBox4:
                if (checked){bundle.putString("eylence","eylence");}
                //eylence
            case R.id.checkBox5:
                if (checked){bundle.putString("yemek","yemek");}
                //yemek
            case R.id.checkBox6:
                if (checked){bundle.putString("alisveris","alisveris");}
                //alısveris
                // TODO: Veggie sandwich
        }

    }

}