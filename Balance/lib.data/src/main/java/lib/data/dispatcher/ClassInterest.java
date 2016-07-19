package lib.data.dispatcher;

/**
 * Created by sgutierc on 15-07-2016.
 */
public class ClassInterest implements Interest {
    private Class iClass = null;

    public ClassInterest(Class iClass) {

        this.iClass = iClass;
    }

    public Class getInterestClass() {
        return this.iClass;
    }


    @Override
    public boolean isOfLike(Object obj) {
        return iClass.isInstance(obj);
    }

    @Override
    public int hashCode() {
        if (iClass == null) return -1;
        return iClass.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (iClass == null) return false;
        if (obj instanceof ClassInterest) {
            ClassInterest cobj = (ClassInterest) obj;
            if (cobj.getInterestClass() != null)
                return iClass.equals(cobj.getInterestClass());
        }
        return false;
    }
}
