package cl.sgutierc.balance.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by sgutierc on 15-09-2016.
 */
public class NumericWatcher implements TextWatcher {
    private EditText editText;
    private DecimalFormat formatter;

    public NumericWatcher(EditText editText, DecimalFormat formatter) {
        this.editText = editText;
        this.formatter = formatter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.removeTextChangedListener(this);

        long sign=s.toString().startsWith("-")?-1:1;

        String userInput = s.toString().replaceAll("[^0-9]", "");
        if (userInput.length() > 0) {
            long input =sign * Long.parseLong(userInput);
            editText.setText(formatter.format(input));
            editText.setSelection(editText.getText().length());
        }
        editText.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
