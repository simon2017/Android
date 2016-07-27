package cl.sgutierc.balance.view.presupuesto;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.view.BaseList;
import cl.sgutierc.balance.view.gasto.GastoView;
import lib.data.Data;

/**
 * Created by sgutierc on 13-07-2016.
 */
public class PresupuestoList extends BaseList<Presupuesto, PresupuestoView> {

    public PresupuestoList(int layoutId, Activity activity) {
        super(layoutId, activity);
    }

    @Override
    protected PresupuestoView getView(Context context) {
        return new PresupuestoView(context);
    }

    @Override
    protected Presupuesto convertFrom(Object data) {
        if (data instanceof Presupuesto) return (Presupuesto) data;
        else return null;
    }
}
