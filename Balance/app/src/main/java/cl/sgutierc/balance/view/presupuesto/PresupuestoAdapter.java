package cl.sgutierc.balance.view.presupuesto;

import android.app.Activity;
import android.content.Context;

import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.view.BaseListAdapter;

/**
 * Created by sgutierc on 13-07-2016.
 */
public class PresupuestoAdapter extends BaseListAdapter<Presupuesto, PresupuestoView> {

    public PresupuestoAdapter( Activity activity) {
        super(activity);
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
