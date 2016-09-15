package cl.sgutierc.balance.view.resumen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import cl.sgutierc.balance.GastoActivity;
import cl.sgutierc.balance.R;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.data.Resumen;
import cl.sgutierc.balance.view.DataView;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class ResumenView extends DataView<Resumen> {
    private static final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());

    public ResumenView(Context context, AttributeSet attrs, int defStyle) {
        super(R.layout.resumen_view, context, attrs, defStyle);
    }

    public ResumenView(Context context, AttributeSet attrs) {
        super(R.layout.resumen_view, context, attrs);
    }

    public ResumenView(Context context) {
        super(R.layout.resumen_view, context);
    }

    protected void reload() {

        Presupuesto presupuesto = getData().getPresupuesto();
        Categoria cat = presupuesto.getCategoria();
        long montoPres = presupuesto.getMonto();
        long gastoTotal = getData().getGastoTotal();
        long restante = getData().getRestante();

        TextView categoriaTxt = (TextView) getViewLayout().findViewById(R.id.categoriaTxt);
        TextView presupuestoTxt = (TextView) getViewLayout().findViewById(R.id.presupuestoTxt);
        TextView gastoTotalTxt = (TextView) getViewLayout().findViewById(R.id.gastoTotalTxt);
        TextView restanteTxt = (TextView) getViewLayout().findViewById(R.id.restanteTxt);

        categoriaTxt.setText(cat.getTitulo());
        presupuestoTxt.setText(formatter.format(montoPres));
        gastoTotalTxt.setText(formatter.format(gastoTotal));
        restanteTxt.setText(formatter.format(restante));


    }

}