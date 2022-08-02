package org.eu.maxelbk.emeraldmc

interface CommandParameter {
    val size: Int
    val map: Map<String, EmeraldData>
    val originalArgs: Array<out String>
}