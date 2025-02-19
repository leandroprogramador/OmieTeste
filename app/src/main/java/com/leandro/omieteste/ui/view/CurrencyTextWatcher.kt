package com.leandro.omieteste.ui.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

class CurrencyTextWatcher(private val editText: EditText) : TextWatcher {
    private val currencyFormatter: NumberFormat =
        NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    private var previousCleanString = ""

    init {
        currencyFormatter.maximumFractionDigits = 2
        currencyFormatter.minimumFractionDigits = 2
    }

    override fun afterTextChanged(s: Editable?) {
        if (s == null || s.toString().isEmpty()) {
            return
        }

        // Remove simbolo e espaço
        val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
        if (cleanString == previousCleanString) {
            return
        }

        // tranforma o valor puro em decimal. EX: 100 vira 1.00
        val parsed = cleanString.toDouble() / 100

        // Formata como moeda brasileira
        val formatted = currencyFormatter.format(parsed)
        previousCleanString = cleanString

        editText.removeTextChangedListener(this)
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    companion object {
        fun unmask(value: String): String {
            return value.replace("R\$ ", "").replace("R\$ ", "").replace(".", "").replace(",", ".")
        }
    }
}
