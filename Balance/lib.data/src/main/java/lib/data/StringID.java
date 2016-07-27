package lib.data;

/**
 * Created by sgutierc on 27-07-2016.
 */
public class StringID implements ID {
    private String id;

    public StringID(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringID)) return false;

        StringID stringID = (StringID) o;

        return id != null ? id.equals(stringID.id) : stringID.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
