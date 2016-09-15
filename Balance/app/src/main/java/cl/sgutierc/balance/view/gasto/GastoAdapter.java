package cl.sgutierc.balance.view.gasto;

import android.app.Activity;
import android.content.Context;

import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.view.BaseListAdapter;

/**
 * Created by sgutierc on 19-07-2016.
 */
public class GastoAdapter extends BaseListAdapter<Gasto, GastoView> {

    public GastoAdapter(Activity activity) {
        super(activity);
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
