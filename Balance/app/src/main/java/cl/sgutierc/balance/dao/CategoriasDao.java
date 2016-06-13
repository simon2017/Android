package cl.sgutierc.balance.dao;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class CategoriasDao {
    private final long id;
    private final String descripcion;

    public CategoriasDao(long id, String descripcion){
        this.id=id;
        this.descripcion=descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public long getId() {
        return id;
    }

    public String toString(){
        return getDescripcion();

    }
}
