package cl.sgutierc.balance.data;

import java.util.Date;

/**
 * Created by sgutierc on 14-06-2016.
 */
public class Gasto {
    private long id=Long.MAX_VALUE;
    private long monto;
    private Date fecha;
    private Categoria categoria;

    /**
     *
     * @param monto
     * @param fecha
     * @param categoria
     */
    public Gasto(long monto, Date fecha, Categoria categoria) {
        this.monto = monto;
        this.fecha = fecha;
        this.categoria = categoria;
    }

    /**
     *
     * @param id
     * @param monto
     * @param fecha
     * @param categoria
     */
    public Gasto(long id,long monto, Date fecha, Categoria categoria) {
        this(monto,fecha,categoria);
        this.id=id;
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

    public long getId() {
        return id;
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
