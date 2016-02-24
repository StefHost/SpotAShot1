package nl.stefhost.viewfinder;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class arrayAdapter_bericht extends ArrayAdapter<String> {

	private final Context context;
    private final ArrayList<String> arrayList_bericht;

	public arrayAdapter_bericht(Context context, ArrayList<String> arrayList1) {
		super(context, R.layout.listview_bericht, arrayList1);
		this.context = context;
        this.arrayList_bericht = arrayList1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_bericht, parent, false);

		Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/futura.ttf");
		TextView textView1 = (TextView) rowView.findViewById(R.id.textView1);
		textView1.setTypeface(typeface);

        String bericht = arrayList_bericht.get(position);
		bericht = bericht.replace("[groen]", "<font color=#197b30>");
		bericht = bericht.replace("[/groen]", "</font>");
        bericht = bericht.replace("[blauw]", "<font color=#0000ff>");
        bericht = bericht.replace("[/blauw]", "</font>");
		bericht = bericht.replace("[paars]", "<font color=#92278f>");
		bericht = bericht.replace("[/paars]", "</font>");
		bericht = bericht.replace("[oranje]", "<font color=#f26522>");
		bericht = bericht.replace("[/oranje]", "</font>");
		bericht = bericht.replace("[rood]", "<font color=#ED1C24>");
		bericht = bericht.replace("[/rood]", "</font>");
		bericht = bericht.replace("[grijs]", "<font color=#9c9c9c>");
		bericht = bericht.replace("[/grijs]", "</font>");
		bericht = bericht.replace("[enter]","\n");
        textView1.setText(Html.fromHtml(bericht));

		return rowView;
	}
	
}