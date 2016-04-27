package xyz.arifguler.strappv13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class DetailSearch extends AppCompatActivity {
    Button ara;
    EditText anahtarkelime,anahtarsehir;
    RadioButton otel,camii,cafe,restoran;
    static String bundle="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        ara=(Button)findViewById(R.id.search);
        anahtarkelime=(EditText)findViewById(R.id.anahtarkelime);
        anahtarsehir=(EditText)findViewById(R.id.sehir);
        otel=(RadioButton)findViewById(R.id.otel);
        camii=(RadioButton)findViewById(R.id.camii);
        cafe=(RadioButton)findViewById(R.id.cafe);
        restoran=(RadioButton)findViewById(R.id.restoran);

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u = anahtarkelime.getText().toString();
                String k = anahtarsehir.getText().toString();
                if (u.equals("") && k.equals("")) {

                    Toast.makeText(getBaseContext(), anahtarkelime.getText() +
                            "Bir kelime veya şehir girin lütfen", Toast.LENGTH_LONG).show();

                } else {

                    Intent intent = new Intent(DetailSearch.this, Foursquare.class);
                    intent.putExtra("arama", u.toString());
                    intent.putExtra("sehir", k.toString());
                    intent.putExtra("ekle", bundle.toString());


                    startActivity(intent);
                }

            }


        });


    }


    public void onRadioButtonClicked(View view){

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.otel:
                if (checked){bundle="otel";}
                break;
            case R.id.cafe:
                if (checked){bundle="cafe";}
                break;
            case R.id.camii:
                if (checked){bundle="camii";}
                break;
            case R.id.restoran:
                if (checked){bundle="restoran";}
                break;

        }

    }

}