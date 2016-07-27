package cl.sgutierc.balance.view.categoria;

import android.app.Activity;
import android.content.Context;

import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.view.BaseList;
import lib.data.Data;

/**
 * Created by sgutierc on 13-07-2016.
 */
public class CategoriaList extends BaseList<Categoria, CategoriaView> {

    public CategoriaList(int layoutId, Activity activity) {
        super(layoutId, activity);
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
