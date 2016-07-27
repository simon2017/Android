package cl.sgutierc.balance;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.data.Categoria;
import lib.data.lib.data.handler.DataAction;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.libdatarepository.SQLiteRepo;

public class MainActivity extends AppCompatActivity {
    private static final int LOAD_GASTO_ACTIVITY_ID = 10;
    private static final int LOAD_PRESUPUESTO_ACTIVITY_ID = 20;
    private static final int LOAD_CATEGORIA_ACTIVITY_ID = 30;
    //private GastoControllerImp controller = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteRepo repository = new SQLiteRepo(this, new BalanceSchema());
        SQLiteDatabase database = repository.getWritableDatabase();

        Button gastoBttn = (Button) findViewById(R.id.gastoBttn);

        // controller = new GastoControllerImp(database);


        gastoBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GastoActivity.class);
                startActivityForResult(intent, LOAD_GASTO_ACTIVITY_ID);
            }
        });

        Button presupuestoBttn = (Button) findViewById(R.id.presupuestoBttn);

        presupuestoBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PresupuestoActivity.class);
                startActivityForResult(intent, LOAD_PRESUPUESTO_ACTIVITY_ID);
            }
        });

        Button categoriaBttn = (Button) findViewById(R.id.categoriaBttn);
        categoriaBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoriaActivity.class);
                startActivityForResult(intent, LOAD_CATEGORIA_ACTIVITY_ID);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == LOAD_GASTO_ACTIVITY_ID && resultCode == RESULT_OK) {
            long monto = data.getLongExtra(GastoActivity.MONTO_BUNDLE_ID, -1);
            long categoriaId = data.getLongExtra(GastoActivity.CATEGORIA_BUNDLE_ID, -1l);
            String fechaString = data.getStringExtra(GastoActivity.FECHA_BUNDLE_ID);
            Date date = Calendar.getInstance().getTime();
            try {
                date = GastoActivity.sdf.parse(fechaString);
            } catch (Exception ex) {

            }
            if (monto != -1 && categoriaId != -1) {
                Log.d(MainActivity.class.getName(), "WORKED! " + monto + " " + categoriaId + " " + date);
            }
            try {
                Gasto gasto=new Gasto(monto, date, new Categoria(categoriaId, ""));
                DataDispatcher.getInstance().spread(new DataAction(gasto,DataAction.Trigger.INSERT));
            } catch (Exception e) {
                Log.e(this.getClass().getName(), e.toString());
            }
        }*/
    }
}
