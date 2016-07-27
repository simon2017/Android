package cl.sgutierc.balance.view.gasto;

import android.app.Activity;
import android.content.Context;

import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.view.BaseList;
import cl.sgutierc.balance.view.gasto.GastoView;
import lib.data.Data;

/**
 * Created by sgutierc on 19-07-2016.
 */
public class GastoList extends BaseList<Gasto, GastoView> {

    public GastoList(int layoutId, Activity activity) {
        super(layoutId, activity);
    }

    @Override
    protected GastoView getView(Context context) {
        return new GastoView(context);
    }

    @Override
    protected Gasto convertFrom(Object data) {
        if (data instanceof Gasto) return (Gasto) data;
        else return null;
    }
}
