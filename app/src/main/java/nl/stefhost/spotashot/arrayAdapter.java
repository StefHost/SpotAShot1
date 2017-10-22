package nl.stefhost.spotashot;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import nl.stefhost.spotashot.functies.download;
import nl.stefhost.spotashot.functies.laad_plaatje;

public class arrayAdapter extends ArrayAdapter<String> {
  
	private final Context context;
    private final ArrayList<String> arrayList_id;
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
    private final ArrayList<String> arrayList_speltype;
    private final ArrayList<String> arrayList_beurt;
    private final ArrayList<String> arrayList_taal;
    private final ArrayList<String> arrayList_nummer;

    private int aantal_spellen1;
    private int aantal_spellen2;

    String toestemming_profielfoto;

	public arrayAdapter(Context context, ArrayList<String> arrayList1, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7, ArrayList<String> arrayList8, ArrayList<String> arrayList9, ArrayList<String> arrayList10, ArrayList<String> arrayList11, ArrayList<String> arrayList12, ArrayList<String> arrayList13, ArrayList<String> arrayList14, String toestemming_profielfoto, ArrayList<String> arrayList15, ArrayList<String> arrayList16, ArrayList<String> arrayList17, ArrayList<String> arrayList18, int int1, int int2) {
		super(context, R.layout.listview_spellen, arrayList1);
		this.context = context;
        this.arrayList_id = arrayList1;
        this.arrayList_naam_speler = arrayList2;
        this.arrayList_naam_tegenstander = arrayList3;
        this.arrayList_punten = arrayList4;
		this.arrayList_kleur_speler = arrayList5;
        this.arrayList_kleur_tegenstander = arrayList6;
        this.arrayList_score_speler = arrayList7;
        this.arrayList_score_tegenstander = arrayList8;
        this.arrayList_beoordelen_speler = arrayList9;
        this.arrayList_beoordelen_tegenstander = arrayList10;
        this.arrayList_chat = arrayList11;
        this.arrayList_profielfoto = arrayList12;
        this.arrayList_thema = arrayList13;
        this.arrayList_status = arrayList14;
        this.toestemming_profielfoto = toestemming_profielfoto;
        this.arrayList_speltype = arrayList15;
        this.arrayList_beurt = arrayList16;
        this.arrayList_taal = arrayList17;
        this.arrayList_nummer = arrayList18;
        this.aantal_spellen1 = int1;
        this.aantal_spellen2 = int2;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_spellen, parent, false);

        final ImageView imageView_foto_tegenstander = (ImageView) rowView.findViewById(R.id.foto_tegenstander);
		ImageView imageView_punten_speler_1 = (ImageView) rowView.findViewById(R.id.punten_speler_1);
        ImageView imageView_punten_speler_2 = (ImageView) rowView.findViewById(R.id.punten_speler_2);
        ImageView imageView_punten_speler_3 = (ImageView) rowView.findViewById(R.id.punten_speler_3);
        ImageView imageView_punten_tegenstander_1 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_1);
        ImageView imageView_punten_tegenstander_2 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_2);
        ImageView imageView_punten_tegenstander_3 = (ImageView) rowView.findViewById(R.id.punten_tegenstander_3);
        ImageView imageView_beoordelen_speler = (ImageView) rowView.findViewById(R.id.beoordelen_speler);
        ImageView imageView_beoordelen_tegenstander = (ImageView) rowView.findViewById(R.id.beoordelen_tegenstander);
        ImageView imageView_beurt_speler = (ImageView) rowView.findViewById(R.id.beurt_speler);
        ImageView imageView_beurt_tegenstander = (ImageView) rowView.findViewById(R.id.beurt_tegenstander);

        TextView textView_naam_speler = (TextView) rowView.findViewById(R.id.naam_speler);
        TextView textView_naam_tegenstander = (TextView) rowView.findViewById(R.id.naam_tegenstander);
        TextView textView_chat = (TextView) rowView.findViewById(R.id.chat);

        TextView textView_thema = (TextView) rowView.findViewById(R.id.thema);
        ImageView imageView_vlag = (ImageView) rowView.findViewById(R.id.vlag);

        TextView textView10 = (TextView) rowView.findViewById(R.id.overlay);
        TextView beurt = (TextView) rowView.findViewById(R.id.beurt);
        TextView info = (TextView) rowView.findViewById(R.id.info);

        RelativeLayout BeoordelenSpeler = (RelativeLayout) rowView.findViewById(R.id.BeoordelenSpeler);
        RelativeLayout BeoordelenTegenstander = (RelativeLayout) rowView.findViewById(R.id.BeoordelenTegenstander);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/futura.ttf");
        Typeface typeface_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/futura_bold.ttf");
        textView_naam_speler.setTypeface(typeface);
        textView_naam_tegenstander.setTypeface(typeface);
        textView_chat.setTypeface(typeface_bold);
        textView_thema.setTypeface(typeface);
        textView10.setTypeface(typeface);
        beurt.setTypeface(typeface);

        if (toestemming_profielfoto.equals("ja")) {
            new Thread(new Runnable() {
                public void run() {
                    String tegenstander = arrayList_naam_tegenstander.get(position);
                    tegenstander = tegenstander.replace(" ", "%20");
                    File opslag = Environment.getExternalStorageDirectory();
                    File profielfoto = new File(opslag, "/Spotashot/profielfotos/" + tegenstander + "_" + arrayList_profielfoto.get(position) + ".jpg");
                    if (profielfoto.exists()) {
                        final Bitmap myBitmap = BitmapFactory.decodeFile(profielfoto.getAbsolutePath());
                        imageView_foto_tegenstander.post(new Runnable() {
                            public void run() {
                                imageView_foto_tegenstander.setImageBitmap(myBitmap);
                            }
                        });
                    }else{
                        new download(imageView_foto_tegenstander).execute(tegenstander, arrayList_profielfoto.get(position));
                    }
                }
            }).start();
        }

        int kleur_speler = 0;
        int kleur_speler_uit = 0;
        int kleur_tegenstander = 0;
        int kleur_tegenstander_uit = 0;

        if (arrayList_kleur_speler.get(position).equals("0")) {
            textView10.setVisibility(View.VISIBLE);
            textView10.setText(context.getString(R.string.spellen_2));
        }else if (arrayList_kleur_speler.get(position).equals("groen")){
            kleur_speler = R.drawable.oog_1;
            kleur_speler_uit = R.drawable.oog_1_uit;
        }else if (arrayList_kleur_speler.get(position).equals("blauw")){
            kleur_speler = R.drawable.oog_2;
            kleur_speler_uit = R.drawable.oog_2_uit;
        }else if (arrayList_kleur_speler.get(position).equals("paars")){
            kleur_speler = R.drawable.oog_3;
            kleur_speler_uit = R.drawable.oog_3_uit;
        }else if (arrayList_kleur_speler.get(position).equals("oranje")){
            kleur_speler = R.drawable.oog_4;
            kleur_speler_uit = R.drawable.oog_4_uit;
        }else{
            kleur_speler = R.drawable.oog_5;
            kleur_speler_uit = R.drawable.oog_5_uit;
        }

        if (arrayList_kleur_tegenstander.get(position).equals("0")){
            textView10.setVisibility(View.VISIBLE);
            textView10.setText(context.getString(R.string.spellen_3));
        }else if (arrayList_kleur_tegenstander.get(position).equals("groen")){
            kleur_tegenstander = R.drawable.oog_1;
            kleur_tegenstander_uit = R.drawable.oog_1_uit;
        }else if (arrayList_kleur_tegenstander.get(position).equals("blauw")){
            kleur_tegenstander = R.drawable.oog_2;
            kleur_tegenstander_uit = R.drawable.oog_2_uit;
        }else if (arrayList_kleur_tegenstander.get(position).equals("paars")){
            kleur_tegenstander = R.drawable.oog_3;
            kleur_tegenstander_uit = R.drawable.oog_3_uit;
        }else if (arrayList_kleur_tegenstander.get(position).equals("oranje")){
            kleur_tegenstander = R.drawable.oog_4;
            kleur_tegenstander_uit = R.drawable.oog_4_uit;
        }else{
            kleur_tegenstander = R.drawable.oog_5;
            kleur_tegenstander_uit = R.drawable.oog_5_uit;
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
        textView_chat.setTag(arrayList_id.get(position));

        if (Integer.parseInt(arrayList_chat.get(position)) > 0){
            textView_chat.startAnimation(myAnimation);
        }

        if (arrayList_thema.get(position).equals("binnen")){
            textView_thema.setText(context.getString(R.string.onderwerp_1));
        }else if (arrayList_thema.get(position).equals("buiten")){
            textView_thema.setText(context.getString(R.string.onderwerp_2));
        }else if (arrayList_thema.get(position).equals("natuur")){
            textView_thema.setText(context.getString(R.string.onderwerp_3));
        }else if (arrayList_thema.get(position).equals("stedelijk")){
            textView_thema.setText(context.getString(R.string.onderwerp_4));
        }else if (arrayList_thema.get(position).equals("willekeurig")){
            textView_thema.setText(context.getString(R.string.onderwerp_5));
        }

        if (arrayList_taal.get(position).equals("nl")){
            imageView_vlag.setImageResource(R.drawable.vlag_nl);
        }else if (arrayList_taal.get(position).equals("de")) {
            imageView_vlag.setImageResource(R.drawable.vlag_de);
        }else{
            imageView_vlag.setImageResource(R.drawable.vlag_en);
        }

        if (arrayList_beurt.get(position).equals("1") || arrayList_beurt.get(position).equals("2")){
            imageView_beurt_speler.setImageResource(R.drawable.beurt_aan);
            imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_uit);
            textView_naam_speler.setTextColor(Color.parseColor("#ffffff"));
            textView_naam_tegenstander.setTextColor(Color.parseColor("#555555"));
        }else{
            imageView_beurt_speler.setImageResource(R.drawable.beurt_uit);
            imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_aan);
            textView_naam_speler.setTextColor(Color.parseColor("#555555"));
            textView_naam_tegenstander.setTextColor(Color.parseColor("#ffffff"));
        }

        if (punten == score_speler){
            textView10.setBackgroundColor(Color.parseColor("#80197b30"));
            textView10.setVisibility(View.VISIBLE);
            textView10.setText(context.getString(R.string.spellen_4));
        }else if (punten == score_tegenstander){
            textView10.setBackgroundColor(Color.parseColor("#80ed1c24"));
            textView10.setVisibility(View.VISIBLE);
            textView10.setText(context.getString(R.string.spellen_5));
        }else if (arrayList_status.get(position).equals("GEANNULEERD")) {
            textView10.setVisibility(View.VISIBLE);
            textView10.setText(context.getString(R.string.spellen_6));
        }

        //beurt weergeven
        if (arrayList_beurt.get(position).equals("1") || arrayList_beurt.get(position).equals("2")){
            if (arrayList_nummer.get(position).equals("1") || arrayList_nummer.get(position).equals("2")){
                beurt.setBackgroundColor(Color.parseColor("#3b0dcf3a"));
                beurt.setText(context.getString(R.string.spellen_7));
            }else{
                beurt.setVisibility(View.GONE);
            }

        }else{
            int nummer = Integer.parseInt(arrayList_nummer.get(position))-1;

            if (nummer == (aantal_spellen1 - aantal_spellen2)){
                beurt.setBackgroundColor(Color.parseColor("#3bed1c24"));
                beurt.setText(context.getString(R.string.spellen_8));
            }else{
                beurt.setVisibility(View.GONE);
            }
        }
        if (Integer.parseInt(arrayList_nummer.get(position)) == aantal_spellen1){
            info.setVisibility(View.VISIBLE);
        }

		return rowView;
	}
	
}