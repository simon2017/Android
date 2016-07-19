package cl.sgutierc.balance.database;

import java.text.SimpleDateFormat;

/**
 * Created by sgutierc on 18-07-2016.
 */
public class GASTO_TABLE {
    public static final String NAME = "gasto";

    public static final String FIELD_ID = "id";
    public static final String FIELD_ID_CAT = "idCategoria";
    public static final String FIELD_MONTO = "monto";
    public static final String FIELD_FECHA = "fecha";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
}
