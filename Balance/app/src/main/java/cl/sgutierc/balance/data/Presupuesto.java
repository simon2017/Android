package cl.sgutierc.balance.data;

/**
 * Created by sgutierc on 01-07-2016.
 */
public class Presupuesto {
    private long mes;
    private long annio;
    private long monto;
    private Categoria categoria;

    public Presupuesto(long monto, Categoria categoria) {
        this(-1, -1, monto, categoria);
    }

    public Presupuesto(long mes, long annio, long monto, Categoria categoria) {
        this.mes = mes;
        this.annio = annio;
        this.monto = monto;
        this.categoria = categoria;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
