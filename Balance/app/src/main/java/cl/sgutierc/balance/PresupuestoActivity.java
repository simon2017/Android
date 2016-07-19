package cl.sgutierc.balance;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.controller.PresupuestoController;
import cl.sgutierc.balance.controller.PresupuestoControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.view.presupuesto.PresupuestoAdapter;
import cl.sgutierc.balance.view.presupuesto.PresupuestoView;
import cl.sgutierc.libdatarepository.SQLiteRepo;


public class PresupuestoActivity extends AppCompatActivity {

    private GastoControllerImp controller = null;
    private SQLiteDatabase database = null;
    private Activity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuesto);
        activity = this;
        this.setTitle("Presupuesto");
        SQLiteRepo repository = new SQLiteRepo(this, new BalanceSchema());
        database = repository.getWritableDatabase();

        loadPresupuestos(database);
        loadCategorias();

        Button saveBttn = (Button) findViewById(R.id.saveButton);
        saveBttn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Spinner catSpinner = (Spinner) findViewById(R.id.categoriaDropdown);
                                            EditText montoEditTxt = (EditText) findViewById(R.id.montoEditTxt);

                                            Categoria categoria = (Categoria) catSpinner.getSelectedItem();
                                            long monto = -1l;
                                            boolean error = false;
                                            try {
                                                monto = Long.valueOf(montoEditTxt.getText().toString());
                                            } catch (Exception e) {
                                                Log.e(this.getClass().getName(), e.toString());
                                                error = true;
                                            }
                                            if (error == false) {
                                                Presupuesto presupuesto = new Presupuesto(monto, categoria);
                                                PresupuestoController pc = new PresupuestoControllerImp(database);
                                                try {
                                                    pc.insertPresupuesto(presupuesto);
                                                } catch (Exception e) {
                                                    Log.e(this.getClass().getName(), e.toString());
                                                }
                                            }
                                            if (error == true) {
                                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(activity);

                                                dlgAlert.setMessage("Favor completar el presupuesto");
                                                dlgAlert.setTitle("Datos incorrectos");
                                                dlgAlert.setPositiveButton("OK", null);
                                                dlgAlert.setCancelable(true);
                                                dlgAlert.create().show();

                                            }

                                        }
                                    }

        );

    }

    private void loadPresupuestos(SQLiteDatabase database) {
        ListView presLayout = (ListView) findViewById(R.id.presListView);

        PresupuestoControllerImp presupuestoController = new PresupuestoControllerImp(database);

        List<Presupuesto> presupuestos = presupuestoController.getPresupuestos();
        PresupuestoAdapter padapter=new PresupuestoAdapter(getBaseContext(),presupuestos);

        presLayout.setAdapter(padapter);
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
