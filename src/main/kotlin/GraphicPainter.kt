import ru.smak.gui.graphics.CartesianPainter
import ru.smak.gui.graphics.convertation.CartesianScreenPlane
import ru.smak.gui.graphics.convertation.Converter
import ru.smak.polynoms.Polynom
import java.awt.*

class GraphicPainter(g: Graphics?, plane: CartesianScreenPlane) : CartesianPainter(plane) {

    fun drawGraphic(g: Graphics?, polynom: Polynom) {
        if (g != null) {
            g.color = Color.GREEN
            val g2 = g as Graphics2D
            g2.stroke = BasicStroke(2.0f)
            val rh = mapOf(RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                    RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                    RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                    RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE)
            g2.setRenderingHints(rh)
            val i = 0.001
            var left = plane.xMin
            while (left < plane.xMax) {
                g2.drawLine(Converter.xCrt2Scr(left, plane), Converter.yCrt2Scr(polynom(left), plane),
                        Converter.xCrt2Scr(left + i, plane), Converter.yCrt2Scr(polynom(left + i), plane))
                left += i
            }
        }
    }
}