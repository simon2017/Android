package cl.sgutierc.balance.data;

import java.util.Date;

import lib.data.Data;
import lib.data.ID;
import lib.data.dispatcher.LongId;

/**
 * Created by sgutierc on 14-06-2016.
 */
public class Gasto implements Data {
    private long monto;
    private Date fecha;
    private Categoria categoria;
    private LongId id;

   /**
     * @param monto
     * @param fecha
     * @param categoria
     */
    public Gasto(long monto, Date fecha, Categoria categoria) {
        this(null, monto, fecha, categoria);
    }

    /**
     * @param id
     * @param monto
     * @param fecha
     * @param categoria
     */
    public Gasto(LongId id, long monto, Date fecha, Categoria categoria) {
        this.monto = monto;
        this.fecha = fecha;
        this.categoria = categoria;
        this.id = id;
    }

    public void setId(LongId id) {
        this.id = id;
    }

    public long getMonto() {
        return monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gasto)) return false;

        Gasto gasto = (Gasto) o;

        if (id == null) return false;
        if (gasto.id == null) return false;

        return id.equals(gasto.id);

    }

    @Override
    public int hashCode() {
        if (id == null) return -1;
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "monto=" + monto +
                ", fecha=" + fecha +
                ", categoria=" + categoria +
                '}';
    }
}
