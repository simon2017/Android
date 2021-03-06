package cl.sgutierc.balance;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cl.sgutierc.balance.controller.CategoriasControllerImp;
import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.dispatcher.RepositoryChannel;
import cl.sgutierc.balance.view.ListableFragment;
import cl.sgutierc.balance.view.categoria.CategoriaAdapter;
import cl.sgutierc.balance.view.categoria.CategoriaList;
import cl.sgutierc.libdatarepository.SQLiteRepo;
import lib.data.lib.data.handler.DataAction;


public class CategoriaActivity extends ListableFragment {

    private GastoControllerImp controller = null;
    private SQLiteDatabase database = null;
    private CategoriaActivity activity = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_categorias, container, false);
        activity = this;
        //Initialize repository
        {
            SQLiteRepo repository = new SQLiteRepo(this.getActivity(), new BalanceSchema());
            database = repository.getWritableDatabase();
        }
        loadCategorias(view);

        Button saveBttn = (Button) view.findViewById(R.id.saveButton);
        saveBttn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View innerView) {
                                            EditText catTitleEditTxt = (EditText) view.findViewById(R.id.catTitleEditTxt);
                                            EditText catDescEditTxt = (EditText) view.findViewById(R.id.catDescEditTxt);
                                            String descripcion = catDescEditTxt.getText().toString();
                                            String title = catTitleEditTxt.getText().toString();


                                            if (descripcion.isEmpty() == false) {
                                                Categoria categoria = new Categoria(title, descripcion);
                                                RepositoryChannel.getInstance().spread(new DataAction(categoria, DataAction.Trigger.INSERT));
                                            } else {
                                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(activity.getActivity());

                                                dlgAlert.setMessage("Favor ingresar descripcion en categoria");
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

    private void loadCategorias(View view) {
        CategoriasControllerImp query = new CategoriasControllerImp(database);

        CategoriaList catList = (CategoriaList) view.findViewById(R.id.catListView);
        CategoriaAdapter adapter = new CategoriaAdapter(this.getActivity());
        catList.setAdapter(adapter);

        DataDispatcher.getInstance().spread(query.getCategorias(), DataAction.Trigger.LOAD);
    }

    public String getTitle() {
        return "Categoria";
    }

    public String getListableText() {
        return "Categoria";
    }
}
