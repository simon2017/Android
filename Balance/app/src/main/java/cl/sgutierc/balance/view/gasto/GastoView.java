package cl.sgutierc.balance.view.gasto;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import cl.sgutierc.balance.GastoActivity;
import cl.sgutierc.balance.R;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.view.DataView;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class GastoView extends DataView<Gasto> {

    public GastoView(Context context, AttributeSet attrs, int defStyle) {
        super(R.layout.gasto_view, context, attrs, defStyle);
    }

    public GastoView(Context context, AttributeSet attrs) {
        super(R.layout.gasto_view, context, attrs);
    }

    public GastoView(Context context) {
        super(R.layout.gasto_view, context);
    }

    protected void reload() {
        TextView categoriaTxt = (TextView) getViewLayout().findViewById(R.id.categoriaTxt);
        TextView fechaTxt = (TextView) getViewLayout().findViewById(R.id.fechaTxt);
        TextView montoEdit = (TextView) getViewLayout().findViewById(R.id.montoTxt);

        categoriaTxt.setText(getData().getCategoria().getDescripcion());
        montoEdit.setText(String.valueOf(getData().getMonto()));

        fechaTxt.setText(GastoActivity.sdf.format(getData().getFecha()));
    }

}