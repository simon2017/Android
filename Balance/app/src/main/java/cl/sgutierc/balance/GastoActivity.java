package cl.sgutierc.balance;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.dispatcher.RepositoryChannel;
import cl.sgutierc.balance.view.ListableFragment;
import cl.sgutierc.balance.view.NumericEditText;
import cl.sgutierc.balance.view.gasto.GastoAdapter;
import cl.sgutierc.balance.view.gasto.GastoList;
import cl.sgutierc.libdatarepository.SQLiteRepo;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class GastoActivity extends ListableFragment implements DatePickerDialog.OnDateSetListener {

    public static final String MONTO_BUNDLE_ID = "monto.id";
    public static final String CATEGORIA_BUNDLE_ID = "categoria.id";
    public static final String FECHA_BUNDLE_ID = "fecha.id";

    private TextView dateTxt;
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private SQLiteDatabase database;
    GastoActivity activity;

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        dateTxt.setText(sdf.format(newDate.getTime()));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gasto, container, false);
        activity = this;

        //Initialize repository
        {
            SQLiteRepo repository = new SQLiteRepo(this.getActivity(), new BalanceSchema());
            database = repository.getWritableDatabase();
        }

        loadCategorias(view);
        loadGastos(view);

        //Initialize Date picker
        {
            dateTxt = (TextView) view.findViewById(R.id.dateEditTxt);
            {
                final Calendar newCalendar = Calendar.getInstance();

                dateTxt.setText(sdf.format(newCalendar.getTime()));
                dateTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(activity.getActivity(), activity, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        }

        //Link Save button with Save action
        {
            Button saveButton = (Button) view.findViewById(R.id.saveButton);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NumericEditText montotxt = (NumericEditText) view.findViewById(R.id.montoEditTxt);
                    TextView dateEditTxt = (TextView) view.findViewById(R.id.dateEditTxt);
                    Spinner categoriaSpin = (Spinner) view.findViewById(R.id.categoriaDropdown);

                    boolean error = false;
                    long monto = 0l;
                    try {
                        monto = montotxt.getNumber().longValue();
                    } catch (Exception e) {
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
                        RepositoryChannel.getInstance().spread(new DataAction(gasto, DataAction.Trigger.INSERT));
                        montotxt.setText("");
                    } else {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(activity.getActivity());

                        dlgAlert.setMessage("Favor completar el gasto");
                        dlgAlert.setTitle("Datos incorrectos");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                    }
                }
            });
        }
        return view;
    }


    void loadGastos(View view) {

        GastoList gastoList = (GastoList) view.findViewById(R.id.gastoListView);
        GastoAdapter adapter = new GastoAdapter(this.getActivity());
        gastoList.setAdapter(adapter);

        GastoControllerImp gastoController = new GastoControllerImp(database);
        DataDispatcher.getInstance().spread(gastoController.getGastos(), DataAction.Trigger.LOAD);
    }

    /**
     * Carga en combo de categorias las categorias existentes en BD
     */
    void loadCategorias(View view) {


        CategoriasControllerImp query = new CategoriasControllerImp(database);
        Categoria[] categorias = query.getCategorias().toArray(new Categoria[]{});

        final Spinner categoriaDropDown = (Spinner) view.findViewById(R.id.categoriaDropdown);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, categorias);
        categoriaDropDown.setAdapter(adapter);

    }

    public String getTitle() {
        return "Gasto";
    }

    public String getListableText() {
        return "Gasto";
    }
}
