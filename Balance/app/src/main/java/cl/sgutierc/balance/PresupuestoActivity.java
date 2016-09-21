package cl.sgutierc.balance;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.controller.PresupuestoControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.dispatcher.RepositoryChannel;
import cl.sgutierc.balance.view.ListableFragment;
import cl.sgutierc.balance.view.NumericEditText;
import cl.sgutierc.balance.view.presupuesto.PresupuestoAdapter;
import cl.sgutierc.balance.view.presupuesto.PresupuestoList;
import cl.sgutierc.libdatarepository.SQLiteRepo;
import lib.data.lib.data.handler.DataAction;


public class PresupuestoActivity extends ListableFragment {

    private GastoControllerImp controller = null;
    private SQLiteDatabase database = null;
    private Activity activity = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_presupuesto, container, false);
        //Initialize repository
        {
            SQLiteRepo repository = new SQLiteRepo(this.getActivity(), new BalanceSchema());
            database = repository.getWritableDatabase();
        }
        loadPresupuestos(view);
        loadCategorias(view);

        Button saveBttn = (Button) view.findViewById(R.id.saveButton);
        saveBttn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Spinner catSpinner = (Spinner) view.findViewById(R.id.categoriaDropdown);
                                            NumericEditText montoEditTxt = (NumericEditText) view.findViewById(R.id.montoEditTxt);

                                            Categoria categoria = (Categoria) catSpinner.getSelectedItem();
                                            long monto = -1l;
                                            boolean error = false;
                                            try {
                                                monto = montoEditTxt.getNumber().longValue();
                                            } catch (Exception e) {
                                                Log.e(this.getClass().getName(), e.toString());
                                                error = true;
                                            }
                                            if (error == false) {
                                                Presupuesto presupuesto = new Presupuesto(monto, categoria);

                                                RepositoryChannel.getInstance().spread(new DataAction(presupuesto, DataAction.Trigger.INSERT));
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

        return view;

    }

    private void loadPresupuestos(View view) {

        PresupuestoList presList = (PresupuestoList) view.findViewById(R.id.presListView);
        PresupuestoAdapter adapter = new PresupuestoAdapter(this.getActivity());
        presList.setAdapter(adapter);

        PresupuestoControllerImp presController = new PresupuestoControllerImp(database);
        DataDispatcher.getInstance().spread(presController.getPresupuestos(), DataAction.Trigger.LOAD);
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
        return "Presupuesto";
    }

    public String getListableText() {
        return "Presupuesto";
    }
}
