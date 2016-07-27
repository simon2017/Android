package cl.sgutierc.balance.database;

import android.content.Context;

import cl.sgutierc.libdatarepository.*;

/**
 * Created by sgutierc on 03-06-2016.
 */
public class BalanceSchema extends DBSchema {
    private static final String DATABASE_NAME = "balance.db";
    private static final int DATABASE_VERSION = 1;

    public BalanceSchema() {
        super();
        makeDBScript();
    }

    private void makeDBScript() {
        String categoria = "create table categoria(\n" +
                "\ttitulo TEXT not null PRIMARY KEY,\n" +
                "\tdescripcion TEXT not null\n" +
                ");";

        String presupuesto = "create table presupuesto(\n" +
                "\tmonth INTEGER not null,\n" +
                "\tyear INTEGER not null,\n" +
                "\tidCategoria TEXT not null,\n" +
                "\tmonto INTEGER not null,\n" +
                "\tforeign key(idCategoria) references categoria(titulo) on delete cascade on update cascade,\n" +
                "\tPRIMARY KEY(month,year,idCategoria)\n" +
                "\t\n" +
                ");";


        String gasto = "create table gasto(\n" +
                "\tid INTEGER not null PRIMARY KEY AUTOINCREMENT,\n" +
                "\tidCategoria TEXT not null,\n" +
                "\tmonto INTEGER not null,\n" +
                "\tfecha TEXT not null,\n" +
                "\tforeign key(idCategoria) references categoria(titulo) on delete cascade on update cascade\n" +
                ");";

        String basicCategories = "insert into categoria(titulo,descripcion) values (\"Basicos\",\"Basicos\"),(\"Combustible\",\"Combustible\"),(\"Supermercado\",\"Supermercado\"),(\"Extras\",\"Extras\");";

        super.addCreateScript(categoria);
        super.addCreateScript(presupuesto);
        super.addCreateScript(gasto);
        super.addCreateScript(basicCategories);
    }

    @Override
    public int getDBVersion() {
        return DATABASE_VERSION;
    }

    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }
}
