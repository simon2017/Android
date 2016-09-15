package cl.sgutierc.balance.view.resumen;

import android.app.Activity;
import android.content.Context;

import cl.sgutierc.balance.data.Resumen;
import cl.sgutierc.balance.view.BaseListAdapter;

/**
 * Created by sgutierc on 19-07-2016.
 */
public class ResumenAdapter extends BaseListAdapter<Resumen, ResumenView> {

    public ResumenAdapter(Activity activity) {
        super(activity);
    }

    @Override
    protected ResumenView getView(Context context) {
        return new ResumenView(context);
    }

    @Override
    protected Resumen convertFrom(Object data) {
        if (data instanceof Resumen) return (Resumen) data;
        else return null;
    }
}
