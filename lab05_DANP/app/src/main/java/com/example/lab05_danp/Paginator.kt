package com.example.lab05_danp

interface Paginator<Item, T> {
    suspend fun loadNextItems()
    fun reset()
}