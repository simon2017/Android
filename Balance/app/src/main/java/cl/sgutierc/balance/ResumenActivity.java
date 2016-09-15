package cl.sgutierc.balance;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cl.sgutierc.balance.controller.GastoController;
import cl.sgutierc.balance.controller.GastoControllerImp;
import cl.sgutierc.balance.controller.PresupuestoController;
import cl.sgutierc.balance.controller.PresupuestoControllerImp;
import cl.sgutierc.balance.controller.ResumenController;
import cl.sgutierc.balance.controller.ResumenControllerImp;
import cl.sgutierc.balance.data.Resumen;
import cl.sgutierc.balance.database.BalanceSchema;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.view.resumen.ResumenAdapter;
import cl.sgutierc.balance.view.resumen.ResumenList;
import cl.sgutierc.libdatarepository.SQLiteRepo;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class ResumenActivity extends AppCompatActivity {

    public static final String MONTO_BUNDLE_ID = "monto.id";
    public static final String CATEGORIA_BUNDLE_ID = "categoria.id";
    public static final String FECHA_BUNDLE_ID = "fecha.id";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    private ResumenActivity activity;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_resumen);
        this.setTitle("Resumen");

        //Initialize repository
        {
            SQLiteRepo repository = new SQLiteRepo(this, new BalanceSchema());
            database = repository.getWritableDatabase();
        }

        PresupuestoController pController = new PresupuestoControllerImp(database);
        GastoController gController = new GastoControllerImp(database);

        ResumenController resumenController = new ResumenControllerImp(pController, gController);

        ResumenList resumenListList = (ResumenList) findViewById(R.id.resumenListView);
        ResumenAdapter adapter = new ResumenAdapter(this);
        resumenListList.setAdapter(adapter);
        List<Resumen> resumenList = resumenController.getResumen();
        DataDispatcher.getInstance().spread(resumenList, DataAction.Trigger.LOAD);

        float presTotal = 0f;
        float gastoTotal = 0f;
        float restoTotal = 0f;

        for (Resumen resumen : resumenList) {
            gastoTotal -= resumen.getGastoTotal();
            presTotal += resumen.getPresupuesto().getMonto();
            restoTotal += resumen.getRestante();
        }

        TextView ptotal=(TextView)findViewById(R.id.presTotalTxt);
        TextView gtotal=(TextView)findViewById(R.id.gastoTotalTxt);
        TextView rtotal=(TextView)findViewById(R.id.restTotalTxt);
        NumberFormat plusMinusNF = new DecimalFormat("+ #,###;- #,###");

        ptotal.setText(plusMinusNF.format(presTotal));
        gtotal.setText(plusMinusNF.format(gastoTotal));
        rtotal.setText(plusMinusNF.format(restoTotal));
    }
}
