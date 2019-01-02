package com.pstglia.controledegastos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


/**
 * Classe para exibir uma dialog e definir o retorno
 * em um objeto do tipo EditText
 */
public class DataDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    private int paramEditTextId;
    private EditText edtParam;

    public void setEditTextParam(EditText edt) {

        edtParam = edt;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        if (edtParam == null) {
            return null;
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int retYear, int retMonth, int retDay) {
        // Do something with the date chosen by the user

        year = retYear;
        month = retMonth + 1;
        day = retDay;
        edtParam.setText(day + "/" + month + "/" + year );


    }
}