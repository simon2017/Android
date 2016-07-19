package cl.sgutierc.balance.controller;

import java.util.List;

import cl.sgutierc.balance.data.Gasto;

/**
 * Created by sgutierc on 14-06-2016.
 */
public interface GastoController {

    void updateGasto(Gasto gasto) throws Exception;
    List<Gasto> getGastos();

}
