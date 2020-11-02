package ru.smak


import ru.smak.gui.MainWindow
import ru.smak.polynoms.Newton

fun main() {
    /*   val n= Newton(mutableMapOf(
           -1.0 to 5.0,
           0.0 to 0.0,
           -1.0 to 2.0,
           1.0 to 1.0,
           -1.0 to 1.0
       ))
       println(n)
   */
    try {
        val n = Newton(arrayListOf(Pair(-1.0, 1.0)))
        n.addDot(0.0, 0.0)
        //  n.addDot(-1.0, 0.0)
        n.addDot(1.0, 1.0)
        println(n)
    } catch (e: Exception) {
        println(e.message)
    }
    val w = MainWindow()
    w.isVisible = true
    w.isResizable = true
}