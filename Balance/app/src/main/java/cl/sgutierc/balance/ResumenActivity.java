package cl.sgutierc.balance;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cl.sgutierc.balance.view.ListableFragment;
import cl.sgutierc.balance.view.resumen.ResumenAdapter;
import cl.sgutierc.balance.view.resumen.ResumenList;
import cl.sgutierc.libdatarepository.SQLiteRepo;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class ResumenActivity extends ListableFragment {

    public static final String MONTO_BUNDLE_ID = "monto.id";
    public static final String CATEGORIA_BUNDLE_ID = "categoria.id";
    public static final String FECHA_BUNDLE_ID = "fecha.id";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    private SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_resumen, container, false);

        //Initialize repository
        {
            SQLiteRepo repository = new SQLiteRepo(container.getContext(), new BalanceSchema());
            database = repository.getWritableDatabase();
        }

        PresupuestoController pController = new PresupuestoControllerImp(database);
        GastoController gController = new GastoControllerImp(database);

        ResumenController resumenController = new ResumenControllerImp(pController, gController);

        ResumenList resumenListList = (ResumenList) view.findViewById(R.id.resumenListView);
        ResumenAdapter adapter = new ResumenAdapter(this.getActivity());
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

        TextView ptotal = (TextView) view.findViewById(R.id.presTotalTxt);
        TextView gtotal = (TextView) view.findViewById(R.id.gastoTotalTxt);
        TextView rtotal = (TextView) view.findViewById(R.id.restTotalTxt);
        NumberFormat plusMinusNF = new DecimalFormat("+ #,###;- #,###");

        ptotal.setText(plusMinusNF.format(presTotal));
        gtotal.setText(plusMinusNF.format(gastoTotal));
        rtotal.setText(plusMinusNF.format(restoTotal));

        return view;
    }

    public String getTitle() {
        return "Resumen";
    }

    public String getListableText() {
        return "Resumen";
    }
}
