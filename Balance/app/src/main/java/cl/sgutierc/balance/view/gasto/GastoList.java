package cl.sgutierc.balance.view.gasto;

import android.content.Context;
import android.util.AttributeSet;

import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.view.HeadedList;
import cl.sgutierc.balance.view.presupuesto.PresupuestoView;

/**
 * Created by sgutierc on 08-09-2016.
 */
public class GastoList extends HeadedList<Gasto, GastoView> {
    private static final String[] headers = new String[]{"Ultimos Gastos"};

    public GastoList(Context context, AttributeSet attrs) {
        super(context, attrs, headers);
    }
}
