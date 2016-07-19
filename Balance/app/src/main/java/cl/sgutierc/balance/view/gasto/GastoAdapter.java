package cl.sgutierc.balance.view.gasto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import cl.sgutierc.balance.data.Gasto;

/**
 * Created by sgutierc on 13-07-2016.
 */
public class GastoAdapter extends ArrayAdapter<Gasto> {
    public GastoAdapter(Context context, List<Gasto> gastos) {
        super(context, 0, gastos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Gasto gasto = getItem(position);
        GastoView gv = (GastoView) convertView;

        // Check if an existing view is being reused, otherwise inflate the view
        if (gv == null) {
            gv = new GastoView(getContext());
        }

        gv.setData(gasto);

        return gv;
    }


}
