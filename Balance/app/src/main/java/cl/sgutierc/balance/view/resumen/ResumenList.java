package cl.sgutierc.balance.view.resumen;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import cl.sgutierc.balance.data.Resumen;
import cl.sgutierc.balance.view.HeadedList;

/**
 * Created by sgutierc on 08-09-2016.
 */
public class ResumenList extends HeadedList<Resumen, ResumenView> {
    private static final String[] headers = new String[]{"Categoria", "Presupuesto", "Gastado", "Restante"};

    public ResumenList(Context context, AttributeSet attrs) {
        super(context, attrs, headers);
    }
}
