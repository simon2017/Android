package cl.sgutierc.balance.database;

import android.content.Context;
import cl.sgutierc.libdatarepository.*;

/**
 * Created by sgutierc on 03-06-2016.
 */
public class BalanceSchema extends DBSchema {
    private static final String DATABASE_NAME="balance.db";
    private static final int DATABASE_VERSION=1;

    public BalanceSchema(){
        super();
        makeDBScript();
    }

    private void makeDBScript(){
        String categoria="create table categoria(\n" +
                "\tid INTEGER not null PRIMARY KEY AUTOINCREMENT,\n" +
                "\tdescripcion TEXT not null\n" +
                ");";
        String presupuesto="create table presupuesto(\n" +
                "\tid INTEGER not null PRIMARY KEY AUTOINCREMENT,\n" +
                "\tidCategoria INTEGER not null,\n" +
                "\tmonto INTEGER not null,\n" +
                "\tforeign key(idCategoria) references categoria(id) on delete cascade on update cascade\n" +
                ");";
        String gasto="create table gasto(\n" +
                "\tid INTEGER not null PRIMARY KEY AUTOINCREMENT,\n" +
                "\tidCategoria INTEGER not null,\n" +
                "\tmonto INTEGER not null,\n" +
                "\tfecha TEXT not null,\n" +
                "\tforeign key(idCategoria) references categoria(id) on delete cascade on update cascade\n" +
                ");";
        String basicCategories="insert into categoria(descripcion) values " +
                                "(\"Basicos\"),(\"Combustible\"),(\"Supermercado\"),(\"Extras\");";
        super.addCreateScript(categoria);
        super.addCreateScript(presupuesto);
        super.addCreateScript(gasto);
        super.addCreateScript(basicCategories);   }

    @Override
    public int getDBVersion() {
        return DATABASE_VERSION;
    }

    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }
}
