import ru.smak.gui.graphics.CartesianPainter
import ru.smak.gui.graphics.convertation.CartesianScreenPlane
import ru.smak.gui.graphics.convertation.Converter
import ru.smak.polynoms.Newton
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt

class PointPainter(g: Graphics?, plane: CartesianScreenPlane) : CartesianPainter(plane) {
    val Points: ArrayList<Pair<Double, Double>> = arrayListOf()
    val p = GraphicPainter(g, plane)
    var firstind = 0.0
    var secondind = 0.0

    init {
        g?.color = Color.BLUE
    }

    fun drawPoints(X: Int, Y: Int, g: Graphics?) {
        var flag = true
        if (g != null) {
            Points.forEach {
                if (X < Converter.xCrt2Scr(it.first, plane) + 5 && X > Converter.xCrt2Scr(it.first, plane) - 5) {
                    flag = false
                }
            }
        }
        if (flag)
            Points.add(Pair(Converter.xScr2Crt(X, plane), Converter.yScr2Crt(Y, plane)))
        update(g)
    }

    fun removePoints(X: Int, Y: Int, g: Graphics?) {
        if (g != null) {
            Points.forEach {
                if ((Converter.xCrt2Scr(it.first, plane) - 5 <= X && Converter.xCrt2Scr(it.first, plane) + 5 >= X) && (Converter.yCrt2Scr(it.second, plane) - 5 <= Y && Converter.yCrt2Scr(it.second, plane) + 5 >= Y)) {
                    firstind = it.first
                    secondind = it.second
                }
            }
            Points.remove(Pair(firstind, secondind))
        }
        if (Points.size > 0)
            update(g)
    }

    fun update(g: Graphics?) {
        Points.forEach { x ->
            if (g != null) {
                g.color = Color.RED
                g.fillOval(Converter.xCrt2Scr(x.first, plane) - 5, Converter.yCrt2Scr(x.second, plane) - 5, 10, 10)
            }
        }
        p.drawGraphic(g, Newton(Points))
        p.drawGraphic(g, Proisv(Newton(Points)).pp2)
    }
}