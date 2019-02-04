package com.pstglia.controledegastos.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Classe para exibir uma dialog e definir o retorno
 * em um objeto do tipo EditText
 *
 * Referência/Reference:
 * https://stackoverflow.com/questions/11754663/android-datepicker-fragment-how-to-do-something-when-the-user-sets-a-date
 */
public class DataDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    private int paramEditTextId;
    private EditText edtParam;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public void setEditTextParam(EditText edt) {

        edtParam = edt;
    }

    private Calendar dataSelecionada;

    public void setDataSelecionada(Calendar pCalendar) {
        dataSelecionada = pCalendar;
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

        if (edtParam != null) {
            dataSelecionada.set(retYear, retMonth + 1, retDay);

            edtParam.setText(DateUtils.formatDateTime(getActivity(), dataSelecionada.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }


    }
}