package ru.smak.polynoms

class Newton(var xfx: ArrayList<Pair<Double, Double>>) : Polynom() {
    val dots: MutableMap<Double, Double> = mutableMapOf()
    val divDiff: MutableMap<Pair<Int, Int>, Double> = mutableMapOf()
    var p = Polynom()
    var pr2 = Polynom(doubleArrayOf(1.0))
    var i = -1

    init {
        for ((key, value) in xfx) {
            addDot(key, value)
        }
        //   coef = p.coefficients
    }

    public fun addDot(key: Double, value: Double) {
        var flag = true
        if (dots.size > 0)
            dots.forEach { i, v ->
                if (i == key) {
                    flag = false
                    throw  Exception("Введено одинаковое значение Х = " + key)
                }
            }
        if (flag) {
            dots.put(key, value)
            if (dots.size == 1) {
                p = Polynom(doubleArrayOf((value)))
            } else {
                val pr = Polynom(doubleArrayOf(-dots.keys.elementAt(++i), 1.0))
                pr2 = pr2 * pr
                p = p + pr2 * divDiff(0, i + 1)
            }
            coef = p.coefficients
        }
    }

    private fun divDiff(st: Int, fn: Int): Double {
        var diff = 0.0
        val start: Double
        val finish: Double
        if ((fn - st) < 2) {
            val cst = if (fn == st) st - 1 else st
            finish = dots.values.elementAt(fn)
            start = dots.values.elementAt(cst)
            divDiff.put(Pair(st, fn), (finish - start) / (dots.keys.elementAt(fn) - dots.keys.elementAt(cst)))
            diff = (finish - start) / (dots.keys.elementAt(fn) - dots.keys.elementAt(cst))

            return diff
        } else {
            start = divDiff(st + 1, fn)
            val per = divDiff[Pair(st, fn - 1)]
            if (per != null) {
                divDiff[Pair(st, fn)] = (start - per) / (dots.keys.elementAt(fn) - dots.keys.elementAt(st))
                diff = (start - per) / (dots.keys.elementAt(fn) - dots.keys.elementAt(st))
            }
        }
        return diff
    }
}