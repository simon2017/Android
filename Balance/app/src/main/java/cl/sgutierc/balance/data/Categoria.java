package cl.sgutierc.balance.data;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class Categoria {
    private long id;
    private String descripcion;

    public Categoria(long id, String descripcion){
        this(descripcion);
        this.id=id;
    }

    public Categoria(String descripcion){
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
