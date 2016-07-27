package cl.sgutierc.balance.data;

import lib.data.Data;
import lib.data.ID;
import lib.data.StringID;

/**
 * Created by sgutierc on 01-07-2016.
 */
public class Presupuesto implements Data {
    private long monto;
    private Categoria categoria;
    private PresupuestoId id = null;


    /**
     *
     */
    public static class PresupuestoId implements ID {
        private long mes;
        private long annio;
        private StringID categoriaId;

        public PresupuestoId(StringID categoriaId, long mes, long annio) {
            this.categoriaId = categoriaId;
            this.mes = mes;
            this.annio = annio;
        }

        public StringID getCategoriaId() {
            return this.categoriaId;
        }

        public long getMes() {
            return this.mes;
        }

        public long getAnnio() {
            return this.annio;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PresupuestoId)) return false;

            PresupuestoId that = (PresupuestoId) o;

            if (mes != that.mes) return false;
            if (annio != that.annio) return false;
            return categoriaId != null ? categoriaId.equals(that.categoriaId) : that.categoriaId == null;

        }

        @Override
        public int hashCode() {
            int result = (int) (mes ^ (mes >>> 32));
            result = 31 * result + (int) (annio ^ (annio >>> 32));
            result = 31 * result + (categoriaId != null ? categoriaId.hashCode() : 0);
            return result;
        }
    }

    public Presupuesto(long monto, Categoria categoria) {
        this(null, monto, categoria);
    }

    public Presupuesto(long month, long year, long monto, Categoria categoria) {
        this.id = new PresupuestoId((StringID) categoria.getId(), month, year);
        this.monto = monto;
        this.categoria = categoria;
    }

    public Presupuesto(PresupuestoId id, long monto, Categoria categoria) {
        this.id = id;
        this.monto = monto;
        this.categoria = categoria;
    }

    @Override
    public ID getId() {
        return this.id;
    }

    public void setId(PresupuestoId id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Presupuesto)) return false;

        Presupuesto that = (Presupuesto) o;

        if (categoria != null ? !categoria.equals(that.categoria) : that.categoria != null)
            return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = categoria != null ? categoria.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
