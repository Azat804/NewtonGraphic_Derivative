import ru.smak.polynoms.Polynom

class Proisv(polynom:Polynom): Polynom() {
    var pp2=Polynom()
    init {
        coef = polynom.coefficients
 val t = DoubleArray(coef.size) { 0.0 }
        coef.forEachIndexed { i, v ->
            if(i!=0)
            t[i-1]=v*i
        }
        pp2=Polynom(t)
    }
}