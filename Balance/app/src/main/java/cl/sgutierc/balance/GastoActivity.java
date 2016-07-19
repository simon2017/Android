package cl.sgutierc.balance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.database.GASTO_TABLE;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.view.gasto.GastoAdapter;
import cl.sgutierc.balance.view.gasto.GastoList;
import cl.sgutierc.balance.view.gasto.GastoView;
import cl.sgutierc.libdatarepository.SQLiteRepo;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class GastoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String MONTO_BUNDLE_ID = "monto.id";
    public static final String CATEGORIA_BUNDLE_ID = "categoria.id";
    public static final String FECHA_BUNDLE_ID = "fecha.id";

    private TextView dateTxt;
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    private GastoActivity activity;
    private SQLiteDatabase database;

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        dateTxt.setText(sdf.format(newDate.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_gasto);
        this.setTitle("Gastos");

        //Initialize repository
        {
            SQLiteRepo repository = new SQLiteRepo(this, new BalanceSchema());
            database = repository.getWritableDatabase();
        }

        loadCategorias();
        loadGastos();

        //Initialize Date picker
        {
            dateTxt = (TextView) findViewById(R.id.dateEditTxt);
            {
                final Calendar newCalendar = Calendar.getInstance();

                dateTxt.setText(sdf.format(newCalendar.getTime()));
                dateTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(activity, activity, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        }

        //Link Save button with Save action
        {
            Button saveButton = (Button) findViewById(R.id.saveButton);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText montotxt = (EditText) findViewById(R.id.montoEditTxt);
                    TextView dateEditTxt = (TextView) findViewById(R.id.dateEditTxt);
                    Spinner categoriaSpin = (Spinner) findViewById(R.id.categoriaDropdown);

                    boolean error = false;
                    long monto = 0l;
                    try {
                        monto = Long.parseLong(montotxt.getText().toString());
                    } catch (NumberFormatException e) {
                        error = true;
                    }
                    Categoria categoriaSel = (Categoria) categoriaSpin.getSelectedItem();
                    Date fecha = Calendar.getInstance().getTime();
                    try {
                        fecha = sdf.parse(dateEditTxt.getText().toString());
                    } catch (Exception e) {
                        Log.e(this.getClass().getName(), e.toString());
                    }


                    if (error == false) {
                        Gasto gasto = new Gasto(monto, fecha, categoriaSel);
                        DataDispatcher.getInstance().spread(new DataAction(gasto, DataAction.Trigger.INSERT));
                        montotxt.setText("");
                    } else {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(activity);

                        dlgAlert.setMessage("Favor completar el gasto");
                        dlgAlert.setTitle("Datos incorrectos");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                    }
                }
            });
        }

    }


    void loadGastos() {
        GastoList gastoList = new GastoList(R.id.gastoListView, this);

        GastoControllerImp gastoController = new GastoControllerImp(database);
        DataDispatcher.getInstance().spread(gastoController.getGastos(), DataAction.Trigger.LOAD);
    }

    /**
     * Carga en combo de categorias las categorias existentes en BD
     */
    void loadCategorias() {


        CategoriasControllerImp query = new CategoriasControllerImp();
        Categoria[] categorias = query.getCategorias(database).toArray(new Categoria[]{});

        final Spinner categoriaDropDown = (Spinner) findViewById(R.id.categoriaDropdown);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        categoriaDropDown.setAdapter(adapter);

    }
}
