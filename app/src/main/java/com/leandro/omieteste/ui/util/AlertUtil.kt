package com.leandro.omieteste.ui.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object AlertUtil {

    fun showAlerta(context : Context, titulo : String, mensagem : String, btnText : String, btnAction : () -> Unit) {
        val alert = AlertDialog.Builder(context)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setPositiveButton(btnText, DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                btnAction()
            }).create()
        alert.show()
    }

    fun showAlerta(context : Context, titulo : String, mensagem : String, btnPositiveText : String, btnPositiveAction : () -> Unit, btnNegativeText : String, btnNegativeAction : () -> Unit) {
        val alert = AlertDialog.Builder(context)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setPositiveButton(btnPositiveText, DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                btnPositiveAction()
            })
            .setNegativeButton(btnNegativeText, DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                btnNegativeAction()
            })
            .create()
        alert.show()
    }
}