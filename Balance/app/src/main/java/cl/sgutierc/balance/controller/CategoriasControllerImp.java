package cl.sgutierc.balance.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;
import cl.sgutierc.balance.database.CAT_TABLE;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.dispatcher.RepositoryChannel;
import lib.data.StringID;
import lib.data.dispatcher.ClassInterest;
import lib.data.dispatcher.Listener;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class CategoriasControllerImp implements CategoriasController, Listener {

    private final ClassInterest interest = new ClassInterest(DataAction.class);
    private SQLiteDatabase sqLiteDatabase;

    public CategoriasControllerImp(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        //Conecta controller al bus de notificaciones
        RepositoryChannel.getInstance().attachListener(this, interest);
    }

    @Override
    public void handle(Object object) {
        if (object != null && interest.isOfLike(object)) {
            DataAction action = (DataAction) object;
            if (action.getData() != null && (action.getData() instanceof Categoria)) {
                Categoria categoria = (Categoria) action.getData();

                try {
                    switch (action.getTrigger()) {
                        case UPDATE:
                        case INSERT: {
                            updateCategoria(categoria);
                            break;
                        }
                        case DELETE: { //TODO
                            break;
                        }
                        default: { //TODO
                            break;
                        }
                    }


                } catch (Exception e) {
                    Log.e(this.getClass().getName(), e.toString());
                }
            }
        }
    }

    public void updateCategoria(Categoria categoria) throws Exception {
        long resultId = -1;

        ContentValues values = new ContentValues();
        values.put(CAT_TABLE.FIELD_TITLE, categoria.getTitulo());
        values.put(CAT_TABLE.FIELD_DESC, categoria.getDescripcion());

        //first try insert
        resultId = sqLiteDatabase.insertWithOnConflict(CAT_TABLE.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        //then if result == -1 try to update
        if (resultId == -1) {
            String where = String.format("%s=?", CAT_TABLE.FIELD_TITLE);
            resultId = sqLiteDatabase.update(CAT_TABLE.NAME, values, where, new String[]{categoria.getTitulo()});
        }
        if (resultId == -1) throw new Exception("Error on categoria update call");

        DataDispatcher.getInstance().spread(new DataAction(categoria, DataAction.Trigger.UPDATE));


    }

    public List<Categoria> getCategorias() {

        String columns[] = {CAT_TABLE.FIELD_TITLE, CAT_TABLE.FIELD_DESC};
        String selection = null;
        String selectionArgs[] = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor c = sqLiteDatabase.query(CAT_TABLE.NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        List<Categoria> categorias = new ArrayList();

        while (c.moveToNext()) {
            String title = c.getString(0);
            String descripcion = c.getString(1);
            categorias.add(new Categoria(title, descripcion));
        }
        return categorias;
    }
}
