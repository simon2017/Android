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
import cl.sgutierc.balance.data.Presupuesto.PresupuestoId;
import cl.sgutierc.balance.database.CAT_TABLE;
import cl.sgutierc.balance.database.PRES_TABLE;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.dispatcher.RepositoryChannel;
import lib.data.StringID;
import lib.data.dispatcher.ClassInterest;
import lib.data.dispatcher.Listener;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 01-07-2016.
 */
public class PresupuestoControllerImp implements PresupuestoController, Listener {

    private SQLiteDatabase sqLiteDatabase;
    private final ClassInterest interest = new ClassInterest(DataAction.class);


    public PresupuestoControllerImp(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        //Conecta controller al bus de notificaciones
        RepositoryChannel.getInstance().attachListener(this, interest);
    }


    @Override
    public List<Presupuesto> getPresupuestos() {
        String query = String.format("select %s.%s,%s,%s,%s,%s from %s join %s on %s=%s.%s",
                                /*ARG*/PRES_TABLE.NAME, PRES_TABLE.FIELD_MONTH, PRES_TABLE.FIELD_YEAR, PRES_TABLE.FIELD_CATEGORIA_ID, PRES_TABLE.FIELD_MONTO, CAT_TABLE.FIELD_DESC,
                                /*FROM*/PRES_TABLE.NAME,/*JOIN*/ CAT_TABLE.NAME, /*ON*/PRES_TABLE.FIELD_CATEGORIA_ID, CAT_TABLE.NAME, CAT_TABLE.FIELD_TITLE);


        Log.d(this.getClass().getName(), "P Q: " + query);

        String selectionArgs[] = null;

        Cursor c = sqLiteDatabase.rawQuery(query, selectionArgs);
        List<Presupuesto> presupuestos = new ArrayList<>();

        while (c.moveToNext()) {
            long month = c.getLong(0);
            long year = c.getLong(1);
            String catTitle = c.getString(2);
            long monto = c.getLong(3);
            String descripcion = c.getString(4);

            Categoria categoria = new Categoria(catTitle, descripcion);
            Presupuesto presupuesto = new Presupuesto(month, year, monto, categoria);

            presupuestos.add(presupuesto);
        }

        return presupuestos;
    }


    @Override
    public void handle(Object object) {
        if (object != null && interest.isOfLike(object)) {
            DataAction action = (DataAction) object;
            if (action.getData() != null && (action.getData() instanceof Presupuesto)) {
                Presupuesto presupuesto = (Presupuesto) action.getData();

                try {
                    switch (action.getTrigger()) {
                        case UPDATE:
                        case INSERT: {
                            updatePresupuesto(presupuesto);
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

    @Override
    public void updatePresupuesto(Presupuesto presupuesto) throws Exception {
        boolean hasId = (presupuesto.getId() != null);

        ContentValues values = new ContentValues();
        /*Composed ID*/
        Categoria cat = presupuesto.getCategoria();
        StringID catId = (StringID) cat.getId();
        values.put(PRES_TABLE.FIELD_CATEGORIA_ID, catId.getId());
        long month = Calendar.getInstance().get(Calendar.MONTH);
        long year = Calendar.getInstance().get(Calendar.YEAR);
        if (hasId) {
            month = ((PresupuestoId) presupuesto.getId()).getMes();
            year = ((PresupuestoId) presupuesto.getId()).getAnnio();
        }
        /*Other values*/
        values.put(PRES_TABLE.FIELD_MONTH, month);
        values.put(PRES_TABLE.FIELD_YEAR, year);
        values.put(PRES_TABLE.FIELD_MONTO, presupuesto.getMonto());

        long result = sqLiteDatabase.insertWithOnConflict(PRES_TABLE.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (result == -1) //try to update
        {
            String where = String.format("%s=? and %s=? and %s=?", PRES_TABLE.FIELD_MONTH, PRES_TABLE.FIELD_YEAR, PRES_TABLE.FIELD_CATEGORIA_ID);
            result = sqLiteDatabase.update(PRES_TABLE.NAME, values, where, new String[]{String.valueOf(month), String.valueOf(year), catId.getId()});
        }

        if (result == -1) throw new Exception("Error on insert new Presupuesto");

        PresupuestoId id = new PresupuestoId(catId, month, year);
        presupuesto.setId(id);
        DataDispatcher.getInstance().spread(new DataAction(presupuesto, DataAction.Trigger.UPDATE));
    }
}

