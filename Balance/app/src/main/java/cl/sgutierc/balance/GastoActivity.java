package cl.sgutierc.balance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cl.sgutierc.balance.dao.CategoriasDao;
import cl.sgutierc.balance.dao.CategoriasQuery;
import cl.sgutierc.libdatarepository.SQLiteRepo;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class GastoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String MONTO_BUNDLE_ID="monto.id";
    public static final String CATEGORIA_BUNDLE_ID="categoria.id";


    private EditText dateTxt;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    private GastoActivity activity;

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        dateTxt.setText(sdf.format(newDate.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.gasto_layout);

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
                Spinner categoriaSpin=(Spinner)findViewById(R.id.categoriaDropdown);

                int monto=Integer.parseInt(montotxt.getText().toString());
                CategoriasDao categoriaSel=(CategoriasDao)categoriaSpin.getSelectedItem();
                long catId=categoriaSel.getId();

                Intent intent=new Intent();
                intent.putExtra(MONTO_BUNDLE_ID,monto);
                intent.putExtra(CATEGORIA_BUNDLE_ID,catId);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /**
     * Carga en combo de categorias las categorias existentes en BD
     */
    void loadCategorias(){
        BalanceSchema schema = new BalanceSchema();

        SQLiteRepo repository = new SQLiteRepo(this, new BalanceSchema());
        SQLiteDatabase database = repository.getWritableDatabase();

        CategoriasQuery query = new CategoriasQuery();
        CategoriasDao[] categorias = query.getCategorias(database).toArray(new CategoriasDao[]{});

        final Spinner categoriaDropDown = (Spinner) findViewById(R.id.categoriaDropdown);
        ArrayAdapter<CategoriasDao> adapter = new ArrayAdapter<CategoriasDao>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        categoriaDropDown.setAdapter(adapter);

    }
}
