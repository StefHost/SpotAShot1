package nl.stefhost.viewfinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import nl.stefhost.viewfinder.functies.download;
import nl.stefhost.viewfinder.functies.laad_plaatje;

public class arrayAdapter extends ArrayAdapter<String> {
  
	private final Context context;
    private final ArrayList<String> arrayList_naam_speler;
    private final ArrayList<String> arrayList_naam_tegenstander;
    private final ArrayList<String> arrayList_punten;
    private final ArrayList<String> arrayList_kleur_speler;
    private final ArrayList<String> arrayList_kleur_tegenstander;
    private final ArrayList<String> arrayList_score_speler;
    private final ArrayList<String> arrayList_score_tegenstander;
    private final ArrayList<String> arrayList_beoordelen_speler;
    private final ArrayList<String> arrayList_beoordelen_tegenstander;
    private final ArrayList<String> arrayList_chat;
    private final ArrayList<String> arrayList_profielfoto;
    private final ArrayList<String> arrayList_thema;
    private final ArrayList<String> arrayList_status;

	public arrayAdapter(Context context, ArrayList<String> arrayList1, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7, ArrayList<String> arrayList8, ArrayList<String> arrayList9, ArrayList<String> arrayList10, ArrayList<String> arrayList11, ArrayList<String> arrayList12, ArrayList<String> arrayList13) {
		super(context, R.layout.listview_spellen, arrayList1);
		this.context = context;
        this.arrayList_naam_speler = arrayList1;
        this.arrayList_naam_tegenstander = arrayList2;
        this.arrayList_punten = arrayList3;
		this.arrayList_kleur_speler = arrayList4;
        this.arrayList_kleur_tegenstander = arrayList5;
        this.arrayList_score_speler = arrayList6;
        this.arrayList_score_tegenstander = arrayList7;
        this.arrayList_beoordelen_speler = arrayList8;
        this.arrayList_beoordelen_tegenstander = arrayList9;
        this.arrayList_chat = arrayList10;
        this.arrayList_profielfoto = arrayList11;
        this.arrayList_thema = arrayList12;
        this.arrayList_status = arrayList13;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_spellen, parent, false);

        final ImageView imageView_foto_tegenstander = (ImageView) rowView.findViewById(R.id.foto_tegenstander);
		ImageView imageView_punten_speler_1 = (ImageView) rowView.findViewById(R.id.punten_speler_1);
        ImageView imageView_punten_speler_2 = (ImageView) rowView.findViewById(R.id.punten_speler_2);
        ImageView imageView_punten_speler_3 = (ImageView) rowView.findViewById(R.id.punten_speler_3);
        ImageView imageView_punten_speler_4 = (ImageView) rowView.findViewById(R.id.punten_speler_4);
        ImageView imageView_punten_speler_5 = (ImageView) rowView.findViewById(R.id.punten_speler_5);
        ImageView imageView_punten_speler_6 = (ImageView) rowView.findViewById(R.id.punten_speler_6);
        ImageView imageView_punten_speler_7 = (ImageView) rowView.findViewById(R.id.punten_speler_7);
        ImageView imageView_punten_speler_8 = (ImageView) rowView.findViewById(R.id.punten_speler_8);
        ImageView imageView_punten_speler_9 = (ImageView) rowView.findViewById(R.id.punten_speler_9);
        ImageView imageView_punten_tegenstander_1 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_1);
        ImageView imageView_punten_tegenstander_2 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_2);
        ImageView imageView_punten_tegenstander_3 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_3);
        ImageView imageView_punten_tegenstander_4 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_4);
        ImageView imageView_punten_tegenstander_5 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_5);
        ImageView imageView_punten_tegenstander_6 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_6);
        ImageView imageView_punten_tegenstander_7 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_7);
        ImageView imageView_punten_tegenstander_8 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_8);
        ImageView imageView_punten_tegenstander_9 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_9);
        ImageView imageView_beoordelen_speler = (ImageView) rowView.findViewById(R.id.beoordelen_speler);
        ImageView imageView_beoordelen_tegenstander = (ImageView) rowView.findViewById(R.id.beoordelen_tegenstander);

        TextView textView_naam_speler = (TextView) rowView.findViewById(R.id.naam_speler);
        TextView textView_naam_tegenstander = (TextView) rowView.findViewById(R.id.naam_tegenstander);
        TextView textView_chat = (TextView) rowView.findViewById(R.id.chat);
        TextView textView_thema = (TextView) rowView.findViewById(R.id.thema);

        TextView textView10 = (TextView) rowView.findViewById(R.id.overlay);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/futura.ttf");
        textView_naam_speler.setTypeface(typeface);
        textView_naam_tegenstander.setTypeface(typeface);
        textView_chat.setTypeface(typeface);
        textView_thema.setTypeface(typeface);
        textView10.setTypeface(typeface);

        new Thread(new Runnable() {
            public void run() {

                File opslag = Environment.getExternalStorageDirectory();
                File profielfoto = new File(opslag,"/Viewfinder/profielfotos/"+arrayList_naam_tegenstander.get(position)+"_"+arrayList_profielfoto.get(position)+".jpg");
                if(profielfoto.exists()){
                    final Bitmap myBitmap = BitmapFactory.decodeFile(profielfoto.getAbsolutePath());
                        imageView_foto_tegenstander.post(new Runnable() {
                            public void run() {
                                imageView_foto_tegenstander.setImageBitmap(myBitmap);
                            }
                        });
                }else{
                    new download(imageView_foto_tegenstander).execute(arrayList_naam_tegenstander.get(position),arrayList_profielfoto.get(position));
                }
            }
        }).start();

        int kleur_speler = 0;
        int kleur_speler_uit = 0;
        int kleur_tegenstander = 0;
        int kleur_tegenstander_uit = 0;

        if (arrayList_kleur_speler.get(position).equals("0")) {
            textView10.setVisibility(View.VISIBLE);
            textView10.setText("select play color");
        }else if (arrayList_kleur_speler.get(position).equals("groen")){
            kleur_speler = R.drawable.kleur_1;
            kleur_speler_uit = R.drawable.kleur_1_uit;
        }else if (arrayList_kleur_speler.get(position).equals("blauw")){
            kleur_speler = R.drawable.kleur_2;
            kleur_speler_uit = R.drawable.kleur_2_uit;
        }else if (arrayList_kleur_speler.get(position).equals("paars")){
            kleur_speler = R.drawable.kleur_3;
            kleur_speler_uit = R.drawable.kleur_3_uit;
        }else if (arrayList_kleur_speler.get(position).equals("oranje")){
            kleur_speler = R.drawable.kleur_4;
            kleur_speler_uit = R.drawable.kleur_4_uit;
        }else{
            kleur_speler = R.drawable.kleur_5;
            kleur_speler_uit = R.drawable.kleur_5_uit;
        }

        if (arrayList_kleur_tegenstander.get(position).equals("0")){
            textView10.setVisibility(View.VISIBLE);
            textView10.setText("waiting for confirmation");
        }else if (arrayList_kleur_tegenstander.get(position).equals("groen")){
            kleur_tegenstander = R.drawable.kleur_1;
            kleur_tegenstander_uit = R.drawable.kleur_1_uit;
        }else if (arrayList_kleur_tegenstander.get(position).equals("blauw")){
            kleur_tegenstander = R.drawable.kleur_2;
            kleur_tegenstander_uit = R.drawable.kleur_2_uit;
        }else if (arrayList_kleur_tegenstander.get(position).equals("paars")){
            kleur_tegenstander = R.drawable.kleur_3;
            kleur_tegenstander_uit = R.drawable.kleur_3_uit;
        }else if (arrayList_kleur_tegenstander.get(position).equals("oranje")){
            kleur_tegenstander = R.drawable.kleur_4;
            kleur_tegenstander_uit = R.drawable.kleur_4_uit;
        }else{
            kleur_tegenstander = R.drawable.kleur_5;
            kleur_tegenstander_uit = R.drawable.kleur_5_uit;
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
        int score_speler = Integer.parseInt(arrayList_score_speler.get(position));
        int punten = Integer.parseInt(arrayList_punten.get(position));

        while (tellen < punten){
            imageViewPuntenSpeler[tellen].setImageResource(kleur_speler_uit);
            tellen++;
        }
        tellen = 0;
        while (tellen < score_speler){
            imageViewPuntenSpeler[tellen].setImageResource(kleur_speler);
            tellen++;
        }
        tellen = 0;
        while (tellen < 9){
            if (tellen > (punten -1)) {
                imageViewPuntenSpeler[tellen].setImageResource(R.drawable.kleur_0);
            }
            tellen++;
        }

        tellen = 0;
        int score_tegenstander = Integer.parseInt(arrayList_score_tegenstander.get(position));
        while (tellen < punten){
            imageViewPuntenTegenstander[tellen].setImageResource(kleur_tegenstander_uit);
            tellen++;
        }
        tellen = 0;
        while (tellen < score_tegenstander){
            imageViewPuntenTegenstander[tellen].setImageResource(kleur_tegenstander);
            tellen++;
        }
        tellen = 0;
        while (tellen < 9){
            if (tellen > (punten -1)) {
                imageViewPuntenTegenstander[tellen].setImageResource(R.drawable.kleur_0);
            }
            tellen++;
        }

        Animation myAnimation = AnimationUtils.loadAnimation(context, R.anim.animatie_oneindig);

        if (Integer.parseInt(arrayList_beoordelen_speler.get(position)) > 0){
            imageView_beoordelen_speler.setImageResource(R.drawable.beoordelen);
            imageView_beoordelen_speler.startAnimation(myAnimation);
        }
        if (Integer.parseInt(arrayList_beoordelen_tegenstander.get(position)) > 0){
            imageView_beoordelen_tegenstander.setImageResource(R.drawable.beoordelen);
            //imageView_beoordelen_tegenstander.startAnimation(myAnimation);
        }

		textView_naam_speler.setText(arrayList_naam_speler.get(position));
        textView_naam_tegenstander.setText(arrayList_naam_tegenstander.get(position));
        textView_chat.setText(arrayList_chat.get(position));
        textView_thema.setText(arrayList_thema.get(position));

        if (punten == score_speler){
            textView10.setBackgroundColor(Color.parseColor("#80197b30"));
            textView10.setVisibility(View.VISIBLE);
            textView10.setText("GEWONNEN");
        }else if (punten == score_tegenstander){
            textView10.setBackgroundColor(Color.parseColor("#80ed1c24"));
            textView10.setVisibility(View.VISIBLE);
            textView10.setText("VERLOREN");
        }else if (arrayList_status.get(position).equals("GEANNULEERD")) {
            textView10.setVisibility(View.VISIBLE);
            textView10.setText("spel is geannuleerd door tegenstander");
        }

		return rowView;
	}
	
}