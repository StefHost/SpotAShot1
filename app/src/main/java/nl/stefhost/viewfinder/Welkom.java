package nl.stefhost.viewfinder;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class Welkom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welkom);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        View view = findViewById(R.id.imageView1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotationY", 0.0f, 360f);
        objectAnimator.setDuration(3600);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

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
