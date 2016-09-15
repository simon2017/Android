package cl.sgutierc.balance.controller;

import java.util.ArrayList;
import java.util.List;

import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.data.Resumen;

/**
 * Created by sgutierc on 07-09-2016.
 */
public class ResumenControllerImp implements ResumenController {
    private PresupuestoController pController;
    private GastoController gController;

    public ResumenControllerImp(PresupuestoController pController, GastoController gController) {
        this.pController = pController;
        this.gController = gController;
    }

    @Override
    public List<Resumen> getResumen() {
        List<Resumen> resumenList = new ArrayList<>();
        List<Presupuesto> presupuestos = pController.getPresupuestos();


        for (Presupuesto pres : presupuestos) {
            List<Gasto> gastos = gController.getGastos(pres.getCategoria());
            Resumen resumen = new Resumen(pres);
            for (Gasto gasto : gastos)
                resumen.addGasto(gasto.getMonto());
            resumenList.add(resumen);
        }

        return resumenList;
    }
}
