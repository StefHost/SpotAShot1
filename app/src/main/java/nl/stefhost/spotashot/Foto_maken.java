package nl.stefhost.spotashot;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import nl.stefhost.spotashot.functies.laad_plaatje;

public class Foto_maken extends AppCompatActivity {

    public String id;
    public String naam_speler;
    public String naam_tegenstander;
    public String kleur_speler;
    public String kleur_tegenstander;
    public String beurt_tellen;

    public String nummer;
    public String resultaat;
    public String flitser;
    public File foto;
    public Context context;
    public android.app.ProgressDialog ProgressDialog;

    int tijd_uren;
    int tijd_minuten;
    int tijd_seconden;
    public Handler handler;

    @SuppressWarnings("deprecation")
    static Camera mCamera;
    Camera_preview mPreview;
    public FrameLayout preview;

    public boolean selfie = false;
    public boolean foto_gemaakt = false;

    float x1;
    float x2;
    float y1;
    float y2;

    private static final int CAMERA = 0;
    public String toestemming_camera;

    InterstitialAd mInterstitialAd1;
    InterstitialAd mInterstitialAd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        getWindow().setBackgroundDrawableResource(R.drawable.logo);

        float dpi  = getResources().getDisplayMetrics().density;
        Log.d("SAS", ""+dpi);

        String model = Build.MODEL;
        Log.d("SAS", ""+model);

        if (dpi == 4.0 || Build.MODEL.equals("SM-G925F")){
            setContentView(R.layout.activity_foto_maken_4dpi);
        }else{
            setContentView(R.layout.activity_foto_maken_3dpi);
        }

        context = Foto_maken.this;

        ImageView border = (ImageView) findViewById(R.id.border);
        if (border != null) {
            border.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN: {
                            x1 = event.getX();
                            y1 = event.getY();
                            //Log.d("Viewfinder", "x1: "+x1+" y1: "+y1);
                            break;
                        }
                        case MotionEvent.ACTION_MOVE: {
                            x2 = event.getX();
                            y2 = event.getY();

                            if (x2 > x1) {
                                if (x2 - x1 > 100) {
                                    zoomen("in");
                                }
                            } else if (x2 < x1) {
                                if (x1 - x2 > 100) {
                                    zoomen("uit");
                                }
                            }

                            if (y2 > y1) {
                                if (y2 - y1 > 100) {
                                    zoomen("uit");
                                }
                            } else if (y2 < y1) {
                                if (y1 - y2 > 100) {
                                    zoomen("in");
                                }
                            }
                            break;
                        }
                    }
                    return true;
                }
            });
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA);
            toestemming_camera = "nee";
        }else{
            toestemming_camera = "ja";

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            String tip3 = sharedPreferences.getString("tip_3", "0");
            if (!tip3.equals("JA")){
                int aantal = Integer.parseInt(tip3);
                aantal++;
                if (aantal == 2){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tip_3", "JA");
                    editor.apply();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View view = getLayoutInflater().inflate(R.layout.dialog_tips_nieuw, null);
                    builder.setView(view);
                    TextView textView = (TextView) view.findViewById(R.id.textView);
                    textView.setText(getString(R.string.tip_3));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    builder.setCancelable(false);
                    builder.show();
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tip_3", ""+aantal);
                    editor.apply();
                }
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_spel", "0");
        editor.apply();

        mInterstitialAd1 = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd1.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd1.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null){
                    Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
                }else{
                    ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.foto_maken_melding_4), getString(R.string.een_ogenblik_geduld), true, false);
                    new foto_cancelen().execute();
                }
            }
        });

        mInterstitialAd2 = new InterstitialAd(this);
        mInterstitialAd2.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd2.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

        String reclame = getString(R.string.reclame);

        String gekocht = sharedPreferences.getString("gekocht", "");

        if (!gekocht.equals("ja")) {
            requestNewInterstitial();
        }

        spel_laden();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest1 = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mInterstitialAd1.loadAd(adRequest1);
        mInterstitialAd2.loadAd(adRequest2);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toestemming_camera = "ja";
                    onResume();
                }else{
                    toestemming_camera = "nee";
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.spel_melding_2), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (toestemming_camera.equals("ja")) {
            mCamera = getCameraInstance();
            mPreview = new Camera_preview(context, mCamera, "nee");
            preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            flitser = sharedPreferences.getString("flitser", "auto");

            ImageView imageView_flitser = (ImageView) findViewById(R.id.flitser);
            Camera.Parameters params = mCamera.getParameters();

            if (flitser.equals("aan")){
                params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                mCamera.setParameters(params);
                imageView_flitser.setImageResource(R.drawable.flitser_aan);
            }else if (flitser.equals("uit")){
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                imageView_flitser.setImageResource(R.drawable.flitser_uit);
            }else{
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                mCamera.setParameters(params);
                imageView_flitser.setImageResource(R.drawable.flitser_auto);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (toestemming_camera.equals("ja")) {
            preview.removeView(mPreview);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_spel", "");
        editor.apply();
    }

    int zoom = 0;

    public void zoomen(String keuze){

        Camera.Parameters params = mCamera.getParameters();
        int max_zoom = params.getMaxZoom();

        if (keuze.equals("in")){
            zoom = zoom +1;
        }else{
            zoom = zoom -1;
        }
        if (zoom > max_zoom){
            zoom = max_zoom;
        }else if (zoom < 0){
            zoom = 0;
        }
        params.setZoom(zoom);
        mCamera.setParameters(params);
    }

    public void spel_laden(){

        naam_speler = getIntent().getExtras().getString("naam_speler");
        naam_tegenstander = getIntent().getExtras().getString("naam_tegenstander");
        String punten = getIntent().getExtras().getString("punten");
        kleur_speler = getIntent().getExtras().getString("kleur_speler");
        kleur_tegenstander = getIntent().getExtras().getString("kleur_tegenstander");
        String score_speler = getIntent().getExtras().getString("score_speler");
        String score_tegenstander = getIntent().getExtras().getString("score_tegenstander");
        String beoordelen_speler = getIntent().getExtras().getString("beoordelen_speler");
        String beoordelen_tegenstander = getIntent().getExtras().getString("beoordelen_tegenstander");
        String chat = getIntent().getExtras().getString("chat");
        id = getIntent().getExtras().getString("id");
        String onderwerp = getIntent().getExtras().getString("onderwerp");
        String onderwerp_nummer = getIntent().getExtras().getString("onderwerp_nummer");
        String tijd = getIntent().getExtras().getString("tijd");
        String kleur = getIntent().getExtras().getString("kleur");
        nummer = getIntent().getExtras().getString("nummer");
        String profielfoto = getIntent().getExtras().getString("profielfoto");
        String beurt = getIntent().getExtras().getString("beurt");
        String speltype = getIntent().getExtras().getString("speltype");
        beurt_tellen = getIntent().getExtras().getString("beurt_tellen");

        final ImageView imageView_foto_tegenstander = (ImageView) findViewById(R.id.foto_tegenstander);
        ImageView imageView_punten_speler_1 = (ImageView) findViewById(R.id.punten_speler_1);
        ImageView imageView_punten_speler_2 = (ImageView) findViewById(R.id.punten_speler_2);
        ImageView imageView_punten_speler_3 = (ImageView) findViewById(R.id.punten_speler_3);
        ImageView imageView_punten_tegenstander_1 = (ImageView) findViewById(R.id.punten_tegenstander_1);
        ImageView imageView_punten_tegenstander_2 = (ImageView) findViewById(R.id.punten_tegenstander_2);
        ImageView imageView_punten_tegenstander_3 = (ImageView) findViewById(R.id.punten_tegenstander_3);
        ImageView imageView_beoordelen_speler = (ImageView) findViewById(R.id.beoordelen_speler);
        ImageView imageView_beoordelen_tegenstander = (ImageView) findViewById(R.id.beoordelen_tegenstander);
        ImageView imageView_beurt_speler = (ImageView) findViewById(R.id.beurt_speler);
        ImageView imageView_beurt_tegenstander = (ImageView) findViewById(R.id.beurt_tegenstander);

        TextView textView_naam_speler = (TextView) findViewById(R.id.naam_speler);
        TextView textView_naam_tegenstander = (TextView) findViewById(R.id.naam_tegenstander);
        TextView textView_chat = (TextView) findViewById(R.id.chat);

        RelativeLayout BeoordelenSpeler = (RelativeLayout) findViewById(R.id.BeoordelenSpeler);
        RelativeLayout BeoordelenTegenstander = (RelativeLayout) findViewById(R.id.BeoordelenTegenstander);

        if (kleur_speler == null){
            kleur_speler = "";
        }
        if (kleur_tegenstander == null){
            kleur_tegenstander = "";
        }

        int kleur_Speler;
        int kleur_Speler_uit;
        int kleur_Tegenstander;
        int kleur_Tegenstander_uit;

        if (kleur_speler.equals("groen")){
            kleur_Speler = R.drawable.oog_1;
            kleur_Speler_uit = R.drawable.oog_1_uit;
        }else if (kleur_speler.equals("blauw")){
            kleur_Speler = R.drawable.oog_2;
            kleur_Speler_uit = R.drawable.oog_2_uit;
        }else if (kleur_speler.equals("paars")){
            kleur_Speler = R.drawable.oog_3;
            kleur_Speler_uit = R.drawable.oog_3_uit;
        }else if (kleur_speler.equals("oranje")){
            kleur_Speler = R.drawable.oog_4;
            kleur_Speler_uit = R.drawable.oog_4_uit;
        }else{
            kleur_Speler = R.drawable.oog_5;
            kleur_Speler_uit = R.drawable.oog_5_uit;
        }

        if (kleur_tegenstander.equals("groen")){
            kleur_Tegenstander = R.drawable.oog_1;
            kleur_Tegenstander_uit = R.drawable.oog_1_uit;
        }else if (kleur_tegenstander.equals("blauw")){
            kleur_Tegenstander = R.drawable.oog_2;
            kleur_Tegenstander_uit = R.drawable.oog_2_uit;
        }else if (kleur_tegenstander.equals("paars")){
            kleur_Tegenstander = R.drawable.oog_3;
            kleur_Tegenstander_uit = R.drawable.oog_3_uit;
        }else if (kleur_tegenstander.equals("oranje")){
            kleur_Tegenstander = R.drawable.oog_4;
            kleur_Tegenstander_uit = R.drawable.oog_4_uit;
        }else{
            kleur_Tegenstander = R.drawable.oog_5;
            kleur_Tegenstander_uit = R.drawable.oog_5_uit;
        }

        ImageView[] imageViewPuntenSpeler = new ImageView[3];
        imageViewPuntenSpeler[0] = imageView_punten_speler_1;
        imageViewPuntenSpeler[1] = imageView_punten_speler_2;
        imageViewPuntenSpeler[2] = imageView_punten_speler_3;

        ImageView[] imageViewPuntenTegenstander = new ImageView[3];
        imageViewPuntenTegenstander[0] = imageView_punten_tegenstander_1;
        imageViewPuntenTegenstander[1] = imageView_punten_tegenstander_2;
        imageViewPuntenTegenstander[2] = imageView_punten_tegenstander_3;

        int tellen = 0;
        int score_Speler = Integer.parseInt(score_speler);
        int score_Tegenstander = Integer.parseInt(score_tegenstander);
        int score = Integer.parseInt(punten);

        while (tellen < score){
            imageViewPuntenSpeler[tellen].setImageResource(kleur_Speler_uit);
            tellen++;
        }
        tellen = 0;
        while (tellen < score_Speler){
            imageViewPuntenSpeler[tellen].setImageResource(kleur_Speler);
            tellen++;
        }

        tellen = 0;
        while (tellen < score){
            imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander_uit);
            tellen++;
        }
        tellen = 0;
        while (tellen < score_Tegenstander){
            imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander);
            tellen++;
        }

        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animatie_oneindig);

        if (beoordelen_speler != null && Integer.parseInt(beoordelen_speler) > 0){
            imageView_beoordelen_speler.setImageResource(R.drawable.beoordelen);
            imageView_beoordelen_speler.startAnimation(myAnimation);
        }
        if (beoordelen_tegenstander != null && Integer.parseInt(beoordelen_tegenstander) > 0){
            imageView_beoordelen_tegenstander.setImageResource(R.drawable.beoordelen);
            //imageView_beoordelen_tegenstander.startAnimation(myAnimation);
        }

        textView_naam_speler.setText(naam_speler);
        textView_naam_tegenstander.setText(naam_tegenstander);
        textView_chat.setText(chat);

        if (beurt.equals("1") || beurt.equals("2")){
            imageView_beurt_speler.setImageResource(R.drawable.beurt_aan);
            imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_uit);
            textView_naam_speler.setTextColor(Color.parseColor("#ffffff"));
            textView_naam_tegenstander.setTextColor(Color.parseColor("#555555"));
        }else{
            imageView_beurt_speler.setImageResource(R.drawable.beurt_uit);
            imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_aan);
            textView_naam_speler.setTextColor(Color.parseColor("#555555"));
            textView_naam_tegenstander.setTextColor(Color.parseColor("#ffffff"));

            ImageView camera = (ImageView) findViewById(R.id.camera);
            camera.setVisibility(View.INVISIBLE);

            ImageView blurr_image = (ImageView) findViewById(R.id.blurr_image);
            TextView blurr_text = (TextView) findViewById(R.id.blurr_text);
            blurr_image.setVisibility(View.VISIBLE);
            blurr_text.setVisibility(View.VISIBLE);

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fout_tekst);
            linearLayout.setVisibility(View.INVISIBLE);
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        textView_naam_speler.setTypeface(typeface);
        textView_naam_tegenstander.setTypeface(typeface);
        textView_chat.setTypeface(typeface_bold);

        File opslag = Environment.getExternalStorageDirectory();
        String tegenstander_foto = naam_tegenstander.replace(" ", "%20");
        File bestand_profielfoto = new File(opslag,"/Spotashot/profielfotos/"+tegenstander_foto+"_"+profielfoto+".jpg");
        if(bestand_profielfoto.exists()){
            final Bitmap myBitmap = BitmapFactory.decodeFile(bestand_profielfoto.getAbsolutePath());
            imageView_foto_tegenstander.post(new Runnable() {
                public void run() {
                    imageView_foto_tegenstander.setImageBitmap(myBitmap);
                }
            });
        }

        TextView textView_onderwerp = (TextView) findViewById(R.id.onderwerp);
        textView_onderwerp.setTypeface(typeface);
        textView_onderwerp.setText(onderwerp);

        float dpi  = getResources().getDisplayMetrics().density;
        float breedte_vakje = dpi * 120;

        if (tijd == null){
            tijd = "0:0:0";
        }

        StringTokenizer tijd_token = new StringTokenizer(tijd, ":");
        tijd_uren = Integer.parseInt(tijd_token.nextToken());
        tijd_minuten = Integer.parseInt(tijd_token.nextToken());
        tijd_seconden = Integer.parseInt(tijd_token.nextToken());

        float vakje1_aan = breedte_vakje / 24 * tijd_uren;
        float vakje1_uit = breedte_vakje / 24 * (24-tijd_uren);

        ImageView imageview_tijd_aan = (ImageView) findViewById(R.id.tijd_aan);
        ImageView imageview_tijd_uit = (ImageView) findViewById(R.id.tijd_uit);
        imageview_tijd_aan.getLayoutParams().width = (int)vakje1_aan;
        imageview_tijd_uit.getLayoutParams().width = (int)vakje1_uit;
        imageview_tijd_aan.requestLayout();
        imageview_tijd_uit.requestLayout();

        if (kleur == null){
            kleur = "";
        }

        switch (kleur) {
            case "groen":
                imageview_tijd_aan.setImageResource(R.drawable.groen);
                imageview_tijd_uit.setImageResource(R.drawable.groen_uit);
                break;
            case "blauw":
                imageview_tijd_aan.setImageResource(R.drawable.blauw);
                imageview_tijd_uit.setImageResource(R.drawable.blauw_uit);
                break;
            case "paars":
                imageview_tijd_aan.setImageResource(R.drawable.paars);
                imageview_tijd_uit.setImageResource(R.drawable.paars_uit);
                break;
            case "oranje":
                imageview_tijd_aan.setImageResource(R.drawable.oranje);
                imageview_tijd_uit.setImageResource(R.drawable.oranje_uit);
                break;
            case "rood":
                imageview_tijd_aan.setImageResource(R.drawable.rood);
                imageview_tijd_uit.setImageResource(R.drawable.rood_uit);
                break;
        }

        TextView textView_tijd = (TextView) findViewById(R.id.tijd);
        textView_tijd.setTypeface(typeface);
        textView_tijd.setText(tijd);

        handler = new Handler();
        handler.postDelayed(timer, 0);

        if ((beurt_tellen.equals("3") || beurt_tellen.equals("4")) && (beurt.equals("1") || beurt.equals("2"))) {
            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            String popup = sharedPreferences.getString("popup_7", "0");
            if (!popup.equals("JA")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("popup_7", "JA");
                editor.apply();
                Home.popup_test(this, "popup_7", "popup_7");
            }
        }

    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            tijd_seconden = tijd_seconden -1;

            if (tijd_seconden < 0){
                tijd_minuten = tijd_minuten -1;
                tijd_seconden = 59;
            }
            if (tijd_minuten < 0){
                tijd_uren = tijd_uren -1;
                tijd_minuten = 59;
            }
            if (tijd_uren < 1){
                tijd_uren = 0;
            }

            String uren;
            String minuten;
            String seconden;

            if (tijd_uren < 10){
                uren = "0"+tijd_uren;
            }else{
                uren = ""+tijd_uren;
            }
            if (tijd_minuten < 10){
                minuten = "0"+tijd_minuten;
            }else{
                minuten = ""+tijd_minuten;
            }
            if (tijd_seconden < 10){
                seconden = "0"+tijd_seconden;
            }else{
                seconden = ""+tijd_seconden;
            }

            String tijd = uren+":"+minuten+":"+seconden;
            TextView textView_tijd = (TextView) findViewById(R.id.tijd);
            textView_tijd.setText(tijd);
            handler.postDelayed(timer, 1000);
        }
    };

    @SuppressWarnings("deprecation")
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(0);
        }
        catch (Exception e){
            // Exception
        }
        return c;
    }

    @SuppressWarnings("deprecation")
    public void selfie(View view){
        int aantal_cameras = Camera.getNumberOfCameras();
        if (aantal_cameras > 1){
            if (!selfie){
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.removeAllViews();
                mCamera.release();
                mCamera = Camera.open(1);
                mPreview = new Camera_preview(context, mCamera, "ja");
                preview.addView(mPreview);
                selfie = true;
            }else{
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.removeAllViews();
                mCamera.release();
                mCamera = Camera.open(0);
                mPreview = new Camera_preview(context, mCamera, "nee");
                preview.addView(mPreview);
                selfie = false;
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void flitser(View view){
        ImageView imageView_flitser = (ImageView) findViewById(R.id.flitser);

        Camera.Parameters params = mCamera.getParameters();

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (flitser.equals("auto")){
            params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(params);
            flitser = "aan";
            imageView_flitser.setImageResource(R.drawable.flitser_aan);
            editor.putString("flitser", "aan");
        }else if(flitser.equals("aan")){
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(params);
            imageView_flitser.setImageResource(R.drawable.flitser_uit);
            flitser = "uit";
            editor.putString("flitser", "uit");
        }else{
            params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            mCamera.setParameters(params);
            imageView_flitser.setImageResource(R.drawable.flitser_auto);
            flitser = "auto";
            editor.putString("flitser", "auto");
        }

        editor.apply();
    }

    @SuppressWarnings("deprecation")
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            final File pictureFile = getOutputMediaFile();
            if (pictureFile == null){
                Log.d("Viewfinder", "Error creating media file, check storage permissions:");

            }else{

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    Bitmap realImage1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Bitmap realImage2 = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (realImage1.getWidth() > realImage1.getHeight()) {
                        if (selfie) {
                            Matrix matrix1 = new Matrix();
                            matrix1.postRotate(90);
                            matrix1.preScale(-1.0f, -1.0f);
                            Matrix matrix2 = new Matrix();
                            matrix2.postRotate(90);
                            matrix2.preScale(-1.0f, 1.0f);
                            realImage1 = Bitmap.createBitmap(realImage1, 0, 0, realImage1.getWidth(), realImage1.getHeight(), matrix1, true);
                            realImage2 = Bitmap.createBitmap(realImage2, 0, 0, realImage2.getWidth(), realImage2.getHeight(), matrix2, true);
                        }else{
                            Matrix matrix1 = new Matrix();
                            if (Build.MODEL.contains("Nexus 5X")){
                                matrix1.postRotate(270);
                            }else{
                                matrix1.postRotate(90);
                            }
                            matrix1.preScale(1.0f, -1.0f);
                            Matrix matrix2 = new Matrix();
                            if (Build.MODEL.contains("Nexus 5X")){
                                matrix2.postRotate(270);
                            }else{
                                matrix2.postRotate(90);
                            }
                            realImage1 = Bitmap.createBitmap(realImage1, 0, 0, realImage1.getWidth(), realImage1.getHeight(), matrix1, true);
                            realImage2 = Bitmap.createBitmap(realImage2, 0, 0, realImage2.getWidth(), realImage2.getHeight(), matrix2, true);
                        }
                    }
                    Bitmap croppedBitmap1 = Bitmap.createBitmap(realImage1, 0, 80, realImage1.getWidth(), realImage1.getHeight() - 160);
                    croppedBitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();

                    Bitmap croppedBitmap2 = Bitmap.createBitmap(realImage2, 0, 80, realImage2.getWidth(), realImage2.getHeight() - 160);
                    croppedBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                    final Drawable drawable = new BitmapDrawable(getResources(), croppedBitmap2);
                    ImageView imageView = (ImageView) findViewById(R.id.border);
                    imageView.setBackgroundDrawable(drawable);

                    foto = pictureFile;

                    RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.achtergrond);
                    RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.dialoog);
                    relativeLayout1.setVisibility(View.VISIBLE);
                    relativeLayout2.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    Log.d("Viewfinder", "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d("Viewfinder", "Error accessing file: " + e.getMessage());
                }

            }

            foto_gemaakt = true;
        }
    };

    public void foto_ja(View view){

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            Toast.makeText(this, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
            File file = new File("" + foto);
            file.delete();
            ImageView imageView = (ImageView) findViewById(R.id.border);
            Drawable drawable = getResources().getDrawable(R.drawable.border_grijs);
            imageView.setBackgroundDrawable(drawable);
            mCamera.startPreview();
        }else{
            mCamera.release();
            ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.foto_maken_melding_2), getString(R.string.een_ogenblik_geduld), true, false);
            new Thread(new Runnable() {
                public void run() {
                    uploadFile("" + foto);
                }
            }).start();
        }

        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.achtergrond);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.dialoog);
        relativeLayout1.setVisibility(View.INVISIBLE);
        relativeLayout2.setVisibility(View.INVISIBLE);
    }

    public void foto_nee(View view){
        File file = new File("" + foto);
        file.delete();
        ImageView imageView = (ImageView) findViewById(R.id.border);
        Drawable drawable = getResources().getDrawable(R.drawable.border_grijs);
        imageView.setBackgroundDrawable(drawable);
        mCamera.startPreview();

        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.achtergrond);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.dialoog);
        relativeLayout1.setVisibility(View.INVISIBLE);
        relativeLayout2.setVisibility(View.INVISIBLE);

        foto_gemaakt = false;
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        File opslag = Environment.getExternalStorageDirectory();
        File mediaStorageDir = new File(opslag,"/Spotashot/");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Viewfinder", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +"IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

    public void foto_maken(View view){
        if (!foto_gemaakt) {
            mCamera.takePicture(null, null, mPicture);
        }
    }

    int serverResponseCode = 0;

    public int uploadFile(String fileName) {

        HttpURLConnection conn;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(fileName);
        InputStream inputStream = null;

        if (!sourceFile.isFile()) {
            //Source File not exist
            return 0;
        }else{
            try {

                FileInputStream fileInputStream = new FileInputStream(sourceFile);

                String naam_speler_upload = naam_speler.replace(" ", "%20");
                URL url = new URL(getString(R.string.website_paginas)+"/upload.php?id="+id+"&nummer="+nummer+"&naam="+naam_speler_upload);

                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename="+ fileName + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.d("uploadFile", "HTTP Response is:"+serverResponseMessage+" :"+serverResponseCode);

                if(serverResponseCode == 200){

                    try{
                        inputStream = new BufferedInputStream(conn.getInputStream());
                    }catch (java.io.IOException e) {
                        System.out.println("java.io.IOException");
                    }

                    if (inputStream != null){
                        resultaat = inputStream.toString();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        try{
                            resultaat = bufferedReader.readLine();
                        }catch (java.io.IOException e) {
                            System.out.println("java.io.IOException");
                        }

                    }else{
                        resultaat = "ERROR";
                    }

                    Log.d("uploadFile", resultaat);

                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);

                    int aantal_fotos = sharedPreferences.getInt("aantal_fotos", 0);
                    aantal_fotos++;

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("upload", resultaat);
                    editor.putInt("aantal_fotos", aantal_fotos);
                    editor.apply();

                    sourceFile.delete();
                }

                fileInputStream.close();
                dos.flush();
                dos.close();

                ProgressDialog.dismiss();

                SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                int aantal_fotos = sharedPreferences.getInt("aantal_fotos", 0);
                if (aantal_fotos == 3){
                    Log.d("SAS", "FILMPJE");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("aantal_fotos", 0);
                    editor.apply();

                    this.runOnUiThread(new Runnable() {
                        public void run() {
                            if (mInterstitialAd2.isLoaded()) {
                                mInterstitialAd2.show();
                            }else{
                                finish();
                            }
                        }
                    });
                }

                finish();

            } catch (MalformedURLException ex) {
                Log.e("Viewfinder", "MalformedURLException");
            } catch (Exception e) {
                Log.e("Viewfinder", "TEST:"+e);
                ProgressDialog.dismiss();
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
                    }
                });
                sourceFile.delete();
                finish();
            }
            return serverResponseCode;

        }
    }

    public void annuleren (View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(Foto_maken.this);
        builder.setTitle(getString(R.string.foto_maken_melding_3_titel))
                .setMessage(getString(R.string.foto_maken_melding_3_tekst));
        builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cancel();
            }
        });
        builder.setNegativeButton(getString(R.string.nee), null);
        builder.show();

    }

    public void cancel(){

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            Toast.makeText(this, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
        }else{
            String reclame = getString(R.string.reclame);

            if (reclame.equals("ja")) {
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                }else{
                    ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.foto_maken_melding_4), getString(R.string.een_ogenblik_geduld), true, false);
                    new foto_cancelen().execute();
                }
            }else{
                ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.foto_maken_melding_4), getString(R.string.een_ogenblik_geduld), true, false);
                new foto_cancelen().execute();
            }

        }

    }

    private class foto_cancelen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_speler_cancel = naam_speler.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/foto_cancelen.php?naam="+naam_speler_cancel+"&id="+id+"&nummer="+nummer);
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException");
            }

            if (url != null){
                try{
                    urlConnection = url.openConnection();
                }catch (java.io.IOException e){
                    System.out.println("java.io.IOException");
                }
            }

            if (urlConnection != null){
                try{
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }
            }

            if (inputStream != null){
                resultaat = inputStream.toString();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try{
                    resultaat = bufferedReader.readLine();
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            }else{
                resultaat = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ProgressDialog.dismiss();
            foto_cancelen_klaar();
        }

    }

    public void foto_cancelen_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else{
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void chat (View view){
        Intent intent = new Intent(Foto_maken.this, Chat.class);
        intent.putExtra("nummer", id);
        intent.putExtra("naam_speler", naam_speler);
        intent.putExtra("tegenstander", naam_tegenstander);
        intent.putExtra("kleur_speler", kleur_speler);
        intent.putExtra("kleur_tegenstander", kleur_tegenstander);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void info (View view) {
        float dpi  = getResources().getDisplayMetrics().density;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int breedte = metrics.widthPixels;
        int hoogte = metrics.heightPixels;

        String camera = "";

        Camera.Parameters parameters = mCamera.getParameters();
        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            camera = camera+"\n"+size.width+"-"+size.height;
        }

        String totaal = "Dpi: "+dpi+"\nBreedte: "+breedte+"\nHoogte: "+hoogte+"\n\nPreview:"+camera;

        AlertDialog.Builder builder = new AlertDialog.Builder(Foto_maken.this);
        builder.setTitle("Info")
                .setMessage(totaal);
        builder.show();
    }

    public void profiel_laden(View view) {

    }

}