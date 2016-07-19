package cl.sgutierc.balance.view.presupuesto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;

/**
 * Created by sgutierc on 13-07-2016.
 */
public class PresupuestoAdapter extends ArrayAdapter<Presupuesto> {

    public PresupuestoAdapter(Context context, List<Presupuesto> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PresupuestoView pview = (PresupuestoView) convertView;

        Presupuesto pres = getItem(position);
        if (pview == null) {
            pview = new PresupuestoView(getContext());
        }
        pview.setPresupuesto(pres);

        return pview;
    }
}
