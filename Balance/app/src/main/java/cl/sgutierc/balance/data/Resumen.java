package cl.sgutierc.balance.data;

import lib.data.Data;
import lib.data.ID;
import lib.data.dispatcher.LongId;

/**
 * Created by sgutierc on 07-09-2016.
 */
public class Resumen implements Data {
    private Presupuesto presupuesto;
    private long gastoTotal;

    public Resumen(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void addGasto(long gasto) {
        this.gastoTotal += gasto;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public long getGastoTotal() {
        return gastoTotal;
    }

    public long getRestante() {
        long montoPres = presupuesto.getMonto();
        return montoPres - getGastoTotal();
    }

    @Override
    public ID getId() {
        if (presupuesto != null)
            return presupuesto.getId();
        else return new LongId(-1);
    }
}
