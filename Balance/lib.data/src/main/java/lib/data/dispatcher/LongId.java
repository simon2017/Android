package lib.data.dispatcher;

import lib.data.ID;

/**
 * Created by sgutierc on 27-07-2016.
 */
public class LongId implements ID{
    private final long id;

    public LongId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LongId)) return false;

        LongId longId = (LongId) o;

        return id == longId.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
