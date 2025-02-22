package com.leandro.omieteste.ui.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class HintedTextWatcher(private val editText: EditText, private val inputLayout: TextInputLayout, private val hint : String ) {

    fun addWatch(callback : (String) -> Unit) : TextWatcher {
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

                }
                callback(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }
}