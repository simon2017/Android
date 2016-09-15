package cl.sgutierc.balance.view.categoria;

import android.content.Context;
import android.util.AttributeSet;

import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.view.HeadedList;
import cl.sgutierc.balance.view.gasto.GastoView;

/**
 * Created by sgutierc on 08-09-2016.
 */
public class CategoriaList extends HeadedList<Categoria, CategoriaView> {
    private static final String[] headers = new String[]{"Ultimas"};

    public CategoriaList(Context context, AttributeSet attrs) {
        super(context, attrs, headers);
    }
}
