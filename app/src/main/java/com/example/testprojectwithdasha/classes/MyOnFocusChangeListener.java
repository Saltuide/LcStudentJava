package com.example.testprojectwithdasha.classes;

import android.view.View;
import android.widget.EditText;

import com.example.testprojectwithdasha.R;

// Меняется полоска под текстом, чтобы было видно что выбрано именно это поле
public class MyOnFocusChangeListener implements View.OnFocusChangeListener {
    private EditText editText;
    public MyOnFocusChangeListener(final EditText editText) {
        super();
        this.editText = editText;
    }

    @Override
    public void onFocusChange(final View view, final boolean isFocused) {
        if (!isFocused) {
            editText.setBackgroundResource(R.drawable.input_field);
        } else {
            editText.setBackgroundResource(R.drawable.input_field_selected);
        }
    }
}