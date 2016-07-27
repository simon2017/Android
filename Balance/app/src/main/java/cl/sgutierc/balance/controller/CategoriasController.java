package cl.sgutierc.balance.controller;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import cl.sgutierc.balance.data.Categoria;

/**
 * Created by sgutierc on 14-06-2016.
 */
public interface CategoriasController {
    List<Categoria> getCategorias();
}
