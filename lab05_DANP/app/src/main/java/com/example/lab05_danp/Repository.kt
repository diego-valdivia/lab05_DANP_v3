package com.example.lab05_danp

import androidx.compose.material.Text
import kotlinx.coroutines.delay
import com.example.lab05_danp.intensidadA
import com.example.lab05_danp.horas
import com.example.lab05_danp.fechas

class Repository {

    var pivote = 0

    private val remoteDataSource = (1..intensidadA.size).map {
        ListItem(
            intensidad = intensidadA[pivote],
            hora = horas[pivote],
            fecha = fechas[pivote]
        )
        pivote = pivote +1
    }

    suspend fun getItems(page: Int, pageSize: Int): Result<List<Any>> {
        delay(2000L)
        val startingIndex = page * pageSize
        return if(startingIndex + pageSize <= remoteDataSource.size) {
            Result.success(
                remoteDataSource.slice(startingIndex until startingIndex + pageSize)
            )
        } else Result.success(emptyList())
    }
}