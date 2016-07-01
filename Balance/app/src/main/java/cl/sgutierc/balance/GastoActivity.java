package cl.sgutierc.balance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.libdatarepository.SQLiteRepo;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class GastoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String MONTO_BUNDLE_ID="monto.id";
    public static final String CATEGORIA_BUNDLE_ID="categoria.id";
    public static final String FECHA_BUNDLE_ID="fecha.id";

    private EditText dateTxt;
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
        activity=this;
        setContentView(R.layout.activity_gasto);

        SQLiteRepo repository = new SQLiteRepo(this, new BalanceSchema());
         database = repository.getWritableDatabase();

        loadCategorias();

        dateTxt=(EditText)findViewById(R.id.dateEditTxt);
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
        Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view){
                EditText montotxt=(EditText)findViewById(R.id.montoEditTxt);
                EditText dateEditTxt=(EditText)findViewById(R.id.dateEditTxt);
                Spinner categoriaSpin=(Spinner)findViewById(R.id.categoriaDropdown);

                boolean error=false;
                long monto=0l;
                try {
                    monto = Long.parseLong(montotxt.getText().toString());
                }catch (NumberFormatException e){
                    error=true;
                }
                Categoria categoriaSel=(Categoria)categoriaSpin.getSelectedItem();
                long catId=categoriaSel.getId();
                String date=dateEditTxt.getText().toString();

                if(error==false) {
                    Intent intent = new Intent();
                    intent.putExtra(MONTO_BUNDLE_ID, monto);
                    intent.putExtra(CATEGORIA_BUNDLE_ID, catId);
                    intent.putExtra(FECHA_BUNDLE_ID, date);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(activity);

                    dlgAlert.setMessage("Favor completar el gasto");
                    dlgAlert.setTitle("Datos incorrectos");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                }
            }
        });

        {//load gastos scroll
            RelativeLayout base = (RelativeLayout) findViewById(R.id.activityGasto);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.BELOW, R.id.categoriaDropdown);
            lp.addRule(RelativeLayout.ABOVE, R.id.saveButton);

            View view = loadGastos();
            view.setLayoutParams(lp);

            base.addView(view);
        }
    }


    View loadGastos(){

        ScrollView scrollview=new ScrollView(getApplicationContext());
        LinearLayout layout=new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        GastoControllerImp gastoController=new GastoControllerImp(database);

        List<Gasto> gastos=gastoController.getGastos();
        for(Gasto gasto:gastos){
            Log.d(this.getClass().getName(),gasto.toString());
            GastoView gv=new GastoView(getApplicationContext());
            gv.setGasto(gasto);
            layout.addView(gv);
        }
        scrollview.addView(layout);
        return scrollview;
    }

    /**
     * Carga en combo de categorias las categorias existentes en BD
     */
    void loadCategorias(){


        CategoriasControllerImp query = new CategoriasControllerImp();
        Categoria[] categorias = query.getCategorias(database).toArray(new Categoria[]{});

        final Spinner categoriaDropDown = (Spinner) findViewById(R.id.categoriaDropdown);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        categoriaDropDown.setAdapter(adapter);

    }
}
