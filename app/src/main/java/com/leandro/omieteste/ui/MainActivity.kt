package com.leandro.omieteste.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val binding : ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        binding.setNovaVendaClick { abrirNovaVenda() }
    }

    private fun abrirNovaVenda() {
        startActivity(Intent(this, NovoPedidoActivity::class.java))
    }
}
