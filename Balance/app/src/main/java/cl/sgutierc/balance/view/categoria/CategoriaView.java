package cl.sgutierc.balance.view.categoria;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import cl.sgutierc.balance.R;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.view.DataView;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class CategoriaView extends DataView<Categoria> {

    public CategoriaView(Context context, AttributeSet attrs, int defStyle) {
        super(R.layout.categoria_view, context, attrs, defStyle);
    }

    public CategoriaView(Context context, AttributeSet attrs) {
        super(R.layout.categoria_view, context, attrs);
    }

    public CategoriaView(Context context) {
        super(R.layout.categoria_view, context);
    }

    protected void reload() {
        TextView titleTxt = (TextView) getViewLayout().findViewById(R.id.catTitleTxt);
        TextView descripcionTxt = (TextView) getViewLayout().findViewById(R.id.catDescTxt);

        Categoria cat = getData();
        descripcionTxt.setText(cat.getDescripcion());
        titleTxt.setText(cat.getTitulo());
    }

}
