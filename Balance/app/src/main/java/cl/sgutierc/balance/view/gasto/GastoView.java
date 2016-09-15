package cl.sgutierc.balance.view.gasto;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    private static final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM",Locale.getDefault());

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

        categoriaTxt.setText(getData().getCategoria().getTitulo());
        montoEdit.setText(formatter.format(getData().getMonto()));

        fechaTxt.setText(sdf.format(getData().getFecha()));
    }

}