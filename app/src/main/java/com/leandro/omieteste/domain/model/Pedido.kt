package com.leandro.omieteste.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leandro.omieteste.ui.util.DateUtil
import com.leandro.omieteste.ui.util.extensions.formatarMoeda
import java.util.Calendar
import java.util.Locale

@Entity(tableName = "pedido")
data class Pedido(
    @ColumnInfo(name = "nome_cliente") val nomeCliente: String,
    @ColumnInfo(name = "valor_total") val valorTotal: Double,
    @ColumnInfo(name = "data_pedido") val dataPedido: Long = Calendar.getInstance().timeInMillis,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "pedido_id") val id: Long = 0
) {
    fun formatarData() : String = DateUtil.formatarTImeStampParaData(dataPedido)
    fun formatarValor() : String = valorTotal.formatarMoeda()
    fun formatarID() : String = String.format(Locale.getDefault(), "%04d", id)
}