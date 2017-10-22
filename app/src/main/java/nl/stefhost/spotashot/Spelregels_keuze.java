package nl.stefhost.spotashot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Spelregels_keuze extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int pagina = getArguments().getInt("pagina");
        pagina++;
        int id = this.getResources().getIdentifier("fragment_spelregels_"+pagina, "layout", this.getActivity().getPackageName());

        return inflater.inflate(id, container, false);
    }

}
