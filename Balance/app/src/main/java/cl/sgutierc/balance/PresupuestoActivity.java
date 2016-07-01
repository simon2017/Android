package cl.sgutierc.balance;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.controller.PresupuestoControllerImp;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.libdatarepository.SQLiteRepo;


public class PresupuestoActivity extends AppCompatActivity {

    private GastoControllerImp controller = null;
    private LinearLayout baseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_presupuesto, new LinearLayout(getBaseContext()));
        setContentView(baseView);

        SQLiteRepo repository = new SQLiteRepo(this, new BalanceSchema());
        SQLiteDatabase database = repository.getWritableDatabase();

        loadPresupuestos(database);
    }

    private void loadPresupuestos(SQLiteDatabase database) {
        PresupuestoControllerImp presupuestoController = new PresupuestoControllerImp(database);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        List<Presupuesto> presupuestos = presupuestoController.getPresupuestos();
        for (Presupuesto presupuesto : presupuestos) {
            PresupuestoView view = new PresupuestoView(getApplicationContext());

            view.setPresupuesto(presupuesto);
            view.setLayoutParams(lp);
            baseView.addView(view);
        }
    }
}
