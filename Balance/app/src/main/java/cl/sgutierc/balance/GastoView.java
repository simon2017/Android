package cl.sgutierc.balance;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import cl.sgutierc.balance.R;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class GastoView extends FrameLayout {

    private Gasto gasto=null;
    private Categoria categoria = null;

    private View view = null;

    public GastoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public GastoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GastoView(Context context) {
        super(context);
        initView();
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.gasto_view, null);

        this.addView(view);
    }

    public void setGasto(Gasto gasto){
        this.gasto = gasto;
        this.categoria =gasto.getCategoria();
        reload();
    }

    private void reload() {
        TextView categoriaTxt = (TextView) this.view.findViewById(R.id.categoriaTxt);
        TextView fechaTxt = (TextView) this.view.findViewById(R.id.fechaTxt);
        EditText montoEdit = (EditText) this.view.findViewById(R.id.montoEditTxt);

        categoriaTxt.setText(this.categoria.getDescripcion());
        montoEdit.setText(String.valueOf(this.gasto.getMonto()));

        fechaTxt.setText(GastoActivity.sdf.format(this.gasto.getFecha()));

    }

}