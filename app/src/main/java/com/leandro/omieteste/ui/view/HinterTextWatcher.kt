package com.leandro.omieteste.ui.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class HintedTextWatcher(val editText: EditText, val inputLayout: TextInputLayout, val hint : String) {

    fun addWatch() : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isNotEmpty()) {
                    editText.hint = ""
                    inputLayout.hint = ""
                    inputLayout.error = null
                    inputLayout.isErrorEnabled = false
                } else {
                    editText.hint = hint
                    inputLayout.hint = hint
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }
}