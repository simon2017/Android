package cl.sgutierc.balance.view.presupuesto;

import android.content.Context;
import android.util.AttributeSet;

import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.data.Resumen;
import cl.sgutierc.balance.view.HeadedList;
import cl.sgutierc.balance.view.resumen.ResumenView;

/**
 * Created by sgutierc on 08-09-2016.
 */
public class PresupuestoList extends HeadedList<Presupuesto, PresupuestoView> {
    private static final String[] headers = new String[]{"Actuales"};

    public PresupuestoList(Context context, AttributeSet attrs) {
        super(context, attrs, headers);
    }
}
