package nl.stefhost.viewfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Voorwaarden extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voorwaarden);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    public void terug(View view){
        finish();
    }

}
