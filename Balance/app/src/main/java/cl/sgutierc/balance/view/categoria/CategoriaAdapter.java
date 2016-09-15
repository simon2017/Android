package cl.sgutierc.balance.view.categoria;

import android.app.Activity;
import android.content.Context;

import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.view.BaseListAdapter;

/**
 * Created by sgutierc on 13-07-2016.
 */
public class CategoriaAdapter extends BaseListAdapter<Categoria, CategoriaView> {

    public CategoriaAdapter( Activity activity) {
        super( activity);
    }

    @Override
    protected CategoriaView getView(Context context) {
        return new CategoriaView(context);
    }

    @Override
    protected Categoria convertFrom(Object data) {
        if (data instanceof Categoria) return (Categoria) data;
        else return null;
    }
}
