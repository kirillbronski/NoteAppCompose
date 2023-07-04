package com.kbcoding.noteappcompose.featureNote.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
