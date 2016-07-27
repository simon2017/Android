package cl.sgutierc.balance.data;

import lib.data.Data;
import lib.data.ID;
import lib.data.StringID;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class Categoria implements Data {

    private String titulo;
    private String descripcion;
    private ID id;


    public Categoria(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.id = new StringID(titulo);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public ID getId() {
        return this.id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setId(StringID id) {
        this.id = id;
    }

    public String toString() {
        return getTitulo();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;

        Categoria categoria = (Categoria) o;

        return id != null ? id.equals(categoria.id) : categoria.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
