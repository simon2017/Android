package cl.sgutierc.balance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.libdatarepository.SQLiteRepo;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class PresupuestoView extends FrameLayout {

    private Categoria categoria = null;
    private long monto = 0l;
    private View view = null;

    public PresupuestoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public PresupuestoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PresupuestoView(Context context) {
        super(context);
        initView();
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.presupuesto_view, null);

        this.addView(view);
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        reload();
    }

    public void setMonto(Long monto) {
        this.monto = monto;
        reload();
    }

    public void setPresupuesto(Presupuesto presupuesto){
        this.monto = presupuesto.getMonto();
        this.categoria =presupuesto.getCategoria();
        reload();
    }

    private void reload() {
        TextView categoriaTxt = (TextView) this.view.findViewById(R.id.categoriaTxt);
        EditText montoEdit = (EditText) this.view.findViewById(R.id.montoEditTxt);

        categoriaTxt.setText(this.categoria.getDescripcion());
        montoEdit.setText(String.valueOf(this.monto));
    }

}