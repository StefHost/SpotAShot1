package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.TextView;

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

public class Foto_maken extends AppCompatActivity {

    public String id;
    public String naam_speler;
    public String naam_tegenstander;
    public String kleur_speler;
    public String kleur_tegenstander;

    public String nummer;
    public String resultaat;
    public String flitser = "auto";
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

    float x1;
    float x2;
    float y1;
    float y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        float dpi  = getResources().getDisplayMetrics().density;
        if (dpi == 4.0){
            setContentView(R.layout.activity_foto_maken_4dpi);
        }else{
            setContentView(R.layout.activity_foto_maken_3dpi);
        }

        context = Foto_maken.this;

        ImageView border = (ImageView) findViewById(R.id.border);
        border.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:{
                        x1 = event.getX();
                        y1 = event.getY();
                        //Log.d("Viewfinder", "x1: "+x1+" y1: "+y1);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        x2 = event.getX();
                        y2 = event.getY();

                        if (x2 > x1){
                            if (x2 - x1 > 100){
                                zoomen("in");
                            }
                        }else if (x2 < x1){
                            if (x1 - x2 > 100){
                                zoomen("uit");
                            }
                        }

                        if (y2 > y1){
                            if (y2 - y1 > 100){
                                zoomen("uit");
                            }
                        }else if (y2 < y1){
                            if (y1 - y2 > 100){
                                zoomen("in");
                            }
                        }
                        //Log.d("Viewfinder", "x2: "+x2+" y2: "+y2);
                        break;
                    }
                }
                return true;
            }
        });

        spel_laden();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = getCameraInstance();
        mPreview = new Camera_preview(context, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        preview.removeView(mPreview);
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
        Log.d("Viewfinder", ""+zoom);
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
        String tijd = getIntent().getExtras().getString("tijd");
        String kleur = getIntent().getExtras().getString("kleur");
        nummer = getIntent().getExtras().getString("nummer");
        String profielfoto = getIntent().getExtras().getString("profielfoto");

        final ImageView imageView_foto_tegenstander = (ImageView) findViewById(R.id.foto_tegenstander);
        ImageView imageView_punten_speler_1 = (ImageView) findViewById(R.id.punten_speler_1);
        ImageView imageView_punten_speler_2 = (ImageView) findViewById(R.id.punten_speler_2);
        ImageView imageView_punten_speler_3 = (ImageView) findViewById(R.id.punten_speler_3);
        ImageView imageView_punten_speler_4 = (ImageView) findViewById(R.id.punten_speler_4);
        ImageView imageView_punten_speler_5 = (ImageView) findViewById(R.id.punten_speler_5);
        ImageView imageView_punten_speler_6 = (ImageView) findViewById(R.id.punten_speler_6);
        ImageView imageView_punten_speler_7 = (ImageView) findViewById(R.id.punten_speler_7);
        ImageView imageView_punten_speler_8 = (ImageView) findViewById(R.id.punten_speler_8);
        ImageView imageView_punten_speler_9 = (ImageView) findViewById(R.id.punten_speler_9);
        ImageView imageView_punten_tegenstander_1 = (ImageView) findViewById(R.id.punten_tegenstander_1);
        ImageView imageView_punten_tegenstander_2 = (ImageView) findViewById(R.id.punten_tegenstander_2);
        ImageView imageView_punten_tegenstander_3 = (ImageView) findViewById(R.id.punten_tegenstander_3);
        ImageView imageView_punten_tegenstander_4 = (ImageView) findViewById(R.id.punten_tegenstander_4);
        ImageView imageView_punten_tegenstander_5 = (ImageView) findViewById(R.id.punten_tegenstander_5);
        ImageView imageView_punten_tegenstander_6 = (ImageView) findViewById(R.id.punten_tegenstander_6);
        ImageView imageView_punten_tegenstander_7 = (ImageView) findViewById(R.id.punten_tegenstander_7);
        ImageView imageView_punten_tegenstander_8 = (ImageView) findViewById(R.id.punten_tegenstander_8);
        ImageView imageView_punten_tegenstander_9 = (ImageView) findViewById(R.id.punten_tegenstander_9);
        ImageView imageView_beoordelen_speler = (ImageView) findViewById(R.id.beoordelen_speler);
        ImageView imageView_beoordelen_tegenstander = (ImageView) findViewById(R.id.beoordelen_tegenstander);

        TextView textView_naam_speler = (TextView) findViewById(R.id.naam_speler);
        TextView textView_naam_tegenstander = (TextView) findViewById(R.id.naam_tegenstander);
        TextView textView_chat = (TextView) findViewById(R.id.chat);

        if (kleur_speler == null){
            kleur_speler = "";
        }
        if (kleur_tegenstander == null){
            kleur_tegenstander = "";
        }

        int kleur_Speler = 0;
        int kleur_Speler_uit = 0;
        int kleur_Tegenstander = 0;
        int kleur_Tegenstander_uit = 0;

        if (kleur_speler.equals("groen")){
            kleur_Speler = R.drawable.kleur_1;
            kleur_Speler_uit = R.drawable.kleur_1_uit;
        }else if (kleur_speler.equals("blauw")){
            kleur_Speler = R.drawable.kleur_2;
            kleur_Speler_uit = R.drawable.kleur_2_uit;
        }else if (kleur_speler.equals("paars")){
            kleur_Speler = R.drawable.kleur_3;
            kleur_Speler_uit = R.drawable.kleur_3_uit;
        }else if (kleur_speler.equals("oranje")){
            kleur_Speler = R.drawable.kleur_4;
            kleur_Speler_uit = R.drawable.kleur_4_uit;
        }else{
            kleur_Speler = R.drawable.kleur_5;
            kleur_Speler_uit = R.drawable.kleur_5_uit;
        }

        if (kleur_tegenstander.equals("groen")){
            kleur_Tegenstander = R.drawable.kleur_1;
            kleur_Tegenstander_uit = R.drawable.kleur_1_uit;
        }else if (kleur_tegenstander.equals("blauw")){
            kleur_Tegenstander = R.drawable.kleur_2;
            kleur_Tegenstander_uit = R.drawable.kleur_2_uit;
        }else if (kleur_tegenstander.equals("paars")){
            kleur_Tegenstander = R.drawable.kleur_3;
            kleur_Tegenstander_uit = R.drawable.kleur_3_uit;
        }else if (kleur_tegenstander.equals("oranje")){
            kleur_Tegenstander = R.drawable.kleur_4;
            kleur_Tegenstander_uit = R.drawable.kleur_4_uit;
        }else{
            kleur_Tegenstander = R.drawable.kleur_5;
            kleur_Tegenstander_uit = R.drawable.kleur_5_uit;
        }

        ImageView[] imageViewPuntenSpeler = new ImageView[10];
        imageViewPuntenSpeler[0] = imageView_punten_speler_1;
        imageViewPuntenSpeler[1] = imageView_punten_speler_2;
        imageViewPuntenSpeler[2] = imageView_punten_speler_3;
        imageViewPuntenSpeler[3] = imageView_punten_speler_4;
        imageViewPuntenSpeler[4] = imageView_punten_speler_5;
        imageViewPuntenSpeler[5] = imageView_punten_speler_6;
        imageViewPuntenSpeler[6] = imageView_punten_speler_7;
        imageViewPuntenSpeler[7] = imageView_punten_speler_8;
        imageViewPuntenSpeler[8] = imageView_punten_speler_9;

        ImageView[] imageViewPuntenTegenstander = new ImageView[10];
        imageViewPuntenTegenstander[0] = imageView_punten_tegenstander_1;
        imageViewPuntenTegenstander[1] = imageView_punten_tegenstander_2;
        imageViewPuntenTegenstander[2] = imageView_punten_tegenstander_3;
        imageViewPuntenTegenstander[3] = imageView_punten_tegenstander_4;
        imageViewPuntenTegenstander[4] = imageView_punten_tegenstander_5;
        imageViewPuntenTegenstander[5] = imageView_punten_tegenstander_6;
        imageViewPuntenTegenstander[6] = imageView_punten_tegenstander_7;
        imageViewPuntenTegenstander[7] = imageView_punten_tegenstander_8;
        imageViewPuntenTegenstander[8] = imageView_punten_tegenstander_9;

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
        while (tellen < 9){
            if (tellen > (score -1)) {
                imageViewPuntenSpeler[tellen].setImageResource(R.drawable.zwart);
            }
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
        tellen = 0;
        while (tellen < 9){
            if (tellen > (score -1)) {
                imageViewPuntenTegenstander[tellen].setImageResource(R.drawable.zwart);
            }
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

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        textView_naam_speler.setTypeface(typeface);
        textView_naam_tegenstander.setTypeface(typeface);
        textView_chat.setTypeface(typeface);

        File opslag = Environment.getExternalStorageDirectory();
        File bestand_profielfoto = new File(opslag,"/Viewfinder/profielfotos/"+naam_tegenstander+"_"+profielfoto+".jpg");
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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int breedte = metrics.widthPixels;
        float dpi  = getResources().getDisplayMetrics().density;
        float breedte_totaal = breedte - (30 * dpi);
        float breedte_vakje = breedte_totaal / 2;

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
                break;
            case "blauw":
                imageview_tijd_aan.setImageResource(R.drawable.blauw);
                break;
            case "paars":
                imageview_tijd_aan.setImageResource(R.drawable.paars);
                break;
            case "oranje":
                imageview_tijd_aan.setImageResource(R.drawable.oranje);
                break;
            case "rood":
                imageview_tijd_aan.setImageResource(R.drawable.rood);
                break;
        }

        TextView textView_tijd = (TextView) findViewById(R.id.tijd);
        textView_tijd.setTypeface(typeface);
        textView_tijd.setText(tijd);

        handler = new Handler();
        handler.postDelayed(timer, 0);

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
                mPreview = new Camera_preview(context, mCamera);
                preview.addView(mPreview);
                selfie = true;
            }else{
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.removeAllViews();
                mCamera.release();
                mCamera = Camera.open(0);
                mPreview = new Camera_preview(context, mCamera);
                preview.addView(mPreview);
                selfie = false;
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void flitser(View view){
        ImageView imageView_flitser = (ImageView) findViewById(R.id.flitser);

        Camera.Parameters params = mCamera.getParameters();

        if (flitser.equals("auto")){
            params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(params);
            flitser = "aan";
            imageView_flitser.setImageResource(R.drawable.flitser_aan);
        }else if(flitser.equals("aan")){
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(params);
            imageView_flitser.setImageResource(R.drawable.flitser_uit);
            flitser = "uit";
        }else{
            params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            mCamera.setParameters(params);
            imageView_flitser.setImageResource(R.drawable.flitser_auto);
            flitser = "auto";
        }
    }

    @SuppressWarnings("deprecation")
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            final File pictureFile = getOutputMediaFile();
            if (pictureFile == null){
                Log.d("Viewfinder", "Error creating media file, check storage permissions:");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                Bitmap realImage1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap realImage2 = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (realImage1.getWidth() > realImage1.getHeight()) {
                    if (selfie){
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
                        matrix1.postRotate(90);
                        matrix1.preScale(1.0f, -1.0f);
                        Matrix matrix2 = new Matrix();
                        matrix2.postRotate(90);
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
                ImageView imageView = (ImageView)findViewById(R.id.border);
                imageView.setBackgroundDrawable(drawable);

                AlertDialog.Builder builder = new AlertDialog.Builder(Foto_maken.this);
                builder.setTitle("Use this photo?")
                        .setMessage("")
                        .setCancelable(false);


                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mCamera.release();
                        ProgressDialog = android.app.ProgressDialog.show(context, "Uploading photo", "One moment please..", true, false);
                        new Thread(new Runnable() {
                            public void run() {
                                uploadFile(""+pictureFile);
                            }
                        }).start();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //foto verwijderen
                        File file = new File(""+pictureFile);
                        file.delete();
                        ImageView imageView = (ImageView) findViewById(R.id.border);
                        Drawable drawable = getResources().getDrawable(R.drawable.border_grijs);
                        imageView.setBackgroundDrawable(drawable);
                        mCamera.startPreview();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                alertDialog.show();

            } catch (FileNotFoundException e) {
                Log.d("Viewfinder", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("Viewfinder", "Error accessing file: " + e.getMessage());
            }
        }
    };

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        File opslag = Environment.getExternalStorageDirectory();
        File mediaStorageDir = new File(opslag,"/Viewfinder/");

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
        mCamera.takePicture(null, null, mPicture);
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

                URL url = new URL(getString(R.string.website_paginas)+"/upload.php?id="+id+"&nummer="+nummer+"&naam="+naam_speler);

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
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("herladen", "ja");
                    editor.putString("upload", resultaat);
                    editor.apply();

                    sourceFile.delete();
                }

                fileInputStream.close();
                dos.flush();
                dos.close();

                ProgressDialog.dismiss();

                finish();

            } catch (MalformedURLException ex) {
                Log.e("Viewfinder", "MalformedURLException");
            } catch (Exception e) {
                Log.e("Viewfinder", ""+e);
            }
            return serverResponseCode;

        }
    }

    public void annuleren (View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(Foto_maken.this);
        builder.setTitle("Cancel subject")
                .setMessage("Do you want to cancel this subject?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cancel();
            }
        });
        builder.setNegativeButton("NO", null);
        builder.show();

    }

    public void cancel(){
        ProgressDialog = android.app.ProgressDialog.show(this, "Cancelling subject", "One moment please..", true, false);
        new foto_cancelen().execute();
    }

    private class foto_cancelen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/foto_cancelen.php?naam="+naam_speler+"&id="+id+"&nummer="+nummer);
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
            _functions.melding("Cancelen niet mogelijk", "Beoordelen is op dit moment niet mogelijk..\nProbeer het later nog een keer.", context);
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("herladen", "ja");
            editor.apply();

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

}