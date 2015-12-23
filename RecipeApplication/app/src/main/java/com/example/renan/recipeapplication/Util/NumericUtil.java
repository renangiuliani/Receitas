package com.example.renan.recipeapplication.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by c1284141 on 17/11/2015.
 */
public class NumericUtil {

    public static String parseMoneyToBr(Double value) {
        BigDecimal currency = new BigDecimal(value);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Util.LOCALE_PT_BR);
        return numberFormat.format(currency);
    }

    public static class MonetaryMask implements TextWatcher {

        private EditText mEditText;
        private Boolean mPrefix;

        private NumberFormat mNumberFormat = NumberFormat.getCurrencyInstance(Util.LOCALE_PT_BR);

        public MonetaryMask(EditText editText, Boolean prefix) {
            super();
            mEditText = editText;
            mPrefix = prefix;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {
            String str = "";

            try {
                double value = valueWithoutMask(cs.toString());
                str = ("").equals(cs.toString()) ? "" : mNumberFormat.format(value);
                mEditText.removeTextChangedListener(this);
                if (!mPrefix) {
                    str = str.replaceAll("[R$]", "");
                }

                mEditText.setText(str);
                mEditText.addTextChangedListener(this);
                mEditText.setSelection(str.length());
            } catch (Exception e) {
                e.printStackTrace();
                cs = "";
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        public Double withoutMask() {
            return valueWithoutMask(this.mEditText.getText().toString());
        }

        private Double valueWithoutMask(String valor) {
            BigDecimal parsed = null;
            try {
                String cleanString = valor.replaceAll("[R,$,.]", "");
                parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(
                        new BigDecimal(100), BigDecimal.ROUND_FLOOR);
            } catch (Exception e) {
                parsed = new BigDecimal("0.0");
            }
            return parsed.doubleValue();
        }
    }
}
