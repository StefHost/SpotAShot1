package nl.stefhost.spotashot;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Welkom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welkom);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        TextView textView1 = (TextView) findViewById(R.id.textView2);
        TextView textView2 = (TextView) findViewById(R.id.textView3);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
    }

    public void registreren(View view){
        Intent registreren = new Intent(this, Registreren.class);
        startActivity(registreren);
        finish();
    }

    public void inloggen(View view){
        Intent inloggen = new Intent(this, Inloggen.class);
        startActivity(inloggen);
        finish();
    }

}
