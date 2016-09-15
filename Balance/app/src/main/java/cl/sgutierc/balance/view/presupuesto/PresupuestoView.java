package cl.sgutierc.balance.view.presupuesto;

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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cl.sgutierc.balance.R;
import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.view.DataView;
import cl.sgutierc.libdatarepository.SQLiteRepo;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class PresupuestoView extends DataView<Presupuesto> {

    private static final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());

    public PresupuestoView(Context context, AttributeSet attrs, int defStyle) {
        super(R.layout.presupuesto_view, context, attrs, defStyle);
    }

    public PresupuestoView(Context context, AttributeSet attrs) {
        super(R.layout.presupuesto_view, context, attrs);
    }

    public PresupuestoView(Context context) {
        super(R.layout.presupuesto_view, context);
    }

    protected void reload() {
        TextView categoriaTxt = (TextView) getViewLayout().findViewById(R.id.categoriaTxt);
        TextView montoEdit = (TextView) getViewLayout().findViewById(R.id.montoTxt);

        Categoria cat = getData().getCategoria();
        categoriaTxt.setText(cat.getTitulo());
        montoEdit.setText(formatter.format(getData().getMonto()));
    }

}