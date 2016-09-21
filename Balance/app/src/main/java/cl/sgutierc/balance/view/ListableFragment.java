package cl.sgutierc.balance.view;

import android.app.Fragment;

/**
 * Created by sgutierc on 21-09-2016.
 */

public abstract class ListableFragment extends Fragment {


    public abstract String getTitle();

    public abstract String getListableText();

    public String toString() {
        return getListableText();
    }
}
