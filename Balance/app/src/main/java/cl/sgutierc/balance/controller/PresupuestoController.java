package cl.sgutierc.balance.controller;

import java.util.List;

import cl.sgutierc.balance.data.Presupuesto;

/**
 * Created by sgutierc on 01-07-2016.
 */
public interface PresupuestoController {
    void insertPresupuesto(Presupuesto presupuesto) throws Exception;
    List<Presupuesto> getPresupuestos();

}
