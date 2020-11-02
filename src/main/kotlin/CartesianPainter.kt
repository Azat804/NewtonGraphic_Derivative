package ru.smak.gui.graphics

import PointPainter
import ru.smak.gui.graphics.convertation.CartesianScreenPlane
import ru.smak.gui.graphics.convertation.Converter
import java.awt.Color
import java.awt.Graphics
import java.awt.geom.Arc2D

open class CartesianPainter(var plane: CartesianScreenPlane) : Painter {
    override fun paint(g: Graphics?) {
        drawAxes(g)
    }

    private fun drawAxes(g: Graphics?) {
        // оси
        if (g != null) {
            g.color = Color.BLACK
            g.drawLine(0,
                    Converter.yCrt2Scr(0.0, plane),
                    plane.width,
                    Converter.yCrt2Scr(0.0, plane)
            )
            g.drawLine(
                    Converter.xCrt2Scr(0.0, plane),
                    0,
                    Converter.xCrt2Scr(0.0, plane),
                    plane.height
            )
            val xScale = if (plane.xMax <= 5 && plane.xMin >= -5) 10.0 else 1.0 // деление шкалы 1 или 10
            val yScale = if (plane.yMax <= 5 && plane.yMin >= -5) 10.0 else 1.0 // деление шкалы 1 или 10
            // Разметка положительной оси x
            for (i in 1..((Converter.xCrt2Scr(plane.xMax, plane) - Converter.xCrt2Scr(0.0, plane)) / 5) * 10) {
                val h = if (i % (if (plane.xMax < 5) 10 else 5) == 0) 10 else 5 // длина черточек
                g.color = Color.BLACK
                // черточки
                if (plane.yMin >= 0 || plane.yMax <= 0) {
                    g.drawLine(//рисуем двойную шкалу в случае, когда ось x уходит за пределы экрана
                            Converter.xCrt2Scr(0.0 + i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMax, plane) - h,
                            Converter.xCrt2Scr(0.0 + i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMax, plane) + h)
                    g.drawLine(
                            Converter.xCrt2Scr(0.0 + i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMin, plane) - h,
                            Converter.xCrt2Scr(0.0 + i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMin, plane) + h)
                } else {
                    g.drawLine(
                            Converter.xCrt2Scr(0.0 + i / xScale, plane),
                            Converter.yCrt2Scr(0.0, plane) - h,
                            Converter.xCrt2Scr(0.0 + i / xScale, plane),
                            Converter.yCrt2Scr(0.0, plane) + h
                    )
                }
                g.color = Color.BLUE
                // надписи
                if (i % (if (plane.xMax <= 10 && plane.xMin >= -10) 1 else if (plane.xMax > 50) 10 else if (plane.xMin < -50 && plane.xMax <= 50 && plane.xMax >= 5) 10 else 5) == 0) {
                    if ((plane.yMin >= -3 && plane.yMin < 0 && plane.yMax >= 80)) {
                        g.drawString(i.toString(), Converter.xCrt2Scr(0.0 + i, plane) - 4, Converter.yCrt2Scr(0.0, plane) - 15)
                    } else {
                        if (plane.yMin >= 0 || plane.yMax <= 0) {
                            g.drawString(i.toString(), Converter.xCrt2Scr(0.0 + i, plane) - 4, Converter.yCrt2Scr(plane.yMin, plane) - 15)
                            g.drawString(i.toString(), Converter.xCrt2Scr(0.0 + i, plane) - 4, Converter.yCrt2Scr(plane.yMax, plane) + 25)
                        } else {
                            g.drawString(i.toString(), Converter.xCrt2Scr(0.0 + i, plane) - 4, Converter.yCrt2Scr(0.0, plane) + 25)
                        }
                    }
                }
            }
            // Разметка отрицательной оси х
            for (i in 1..((Converter.xCrt2Scr(plane.xMin, plane) + Converter.xCrt2Scr(0.0, plane)) / 5) * 10) {
                val h = if (i % (if (plane.xMin > -5) 10 else 5) == 0) 10 else 5
                g.color = Color.BLACK
                if (plane.yMin >= 0 || plane.yMax <= 0) {
                    g.drawLine(//рисуем двойную шкалу в случае, когда ось x уходит за пределы экрана
                            Converter.xCrt2Scr(0.0 - i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMax, plane) - h,
                            Converter.xCrt2Scr(0.0 - i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMax, plane) + h)
                    g.drawLine(
                            Converter.xCrt2Scr(0.0 - i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMin, plane) - h,
                            Converter.xCrt2Scr(0.0 - i / xScale, plane),
                            Converter.yCrt2Scr(plane.yMin, plane) + h)
                } else {
                    g.drawLine(
                            Converter.xCrt2Scr(0.0 - i / xScale, plane),
                            Converter.yCrt2Scr(0.0, plane) - h,
                            Converter.xCrt2Scr(0.0 - i / xScale, plane),
                            Converter.yCrt2Scr(0.0, plane) + h
                    )
                }
                g.color = Color.BLUE
                if (i % (if (plane.xMax <= 10 && plane.xMin >= -10) 1 else if (plane.xMin < -50) 10 else if (plane.xMax > 50 && plane.xMin >= -50 && plane.xMin <= -5) 10 else 5) == 0) {
                    if ((plane.yMin >= -3 && plane.yMin < 0 && plane.yMax >= 80)) {
                        g.drawString("-" + i.toString(), Converter.xCrt2Scr(0.0 - i, plane) - 4, Converter.yCrt2Scr(0.0, plane) - 15)
                    } else {
                        if (plane.yMin >= 0 || plane.yMax <= 0) {
                            g.drawString("-" + i.toString(), Converter.xCrt2Scr(0.0 - i, plane) - 4, Converter.yCrt2Scr(plane.yMin, plane) - 15)
                            g.drawString("-" + i.toString(), Converter.xCrt2Scr(0.0 - i, plane) - 4, Converter.yCrt2Scr(plane.yMax, plane) + 25)
                        } else {
                            g.drawString("-" + i.toString(), Converter.xCrt2Scr(0.0 - i, plane) - 4, Converter.yCrt2Scr(0.0, plane) + 25)
                        }
                    }
                }
            }
            // Разметка положительной оси у
            for (i in 1..(((Converter.yCrt2Scr(0.0, plane) - Converter.yCrt2Scr(plane.yMax, plane)) / 5) * 10)) {
                val h = if (i % (if (plane.yMax < 5) 10 else 5) == 0) 10 else 5
                g.color = Color.BLACK
                if (plane.xMin >= 0 || plane.xMax <= 0) {
                    g.drawLine(//рисуем двойную шкалу в случае, когда ось y уходит за пределы экрана
                            Converter.xCrt2Scr(plane.xMax, plane) - h,
                            Converter.yCrt2Scr(0.0 + i / yScale, plane),
                            Converter.xCrt2Scr(plane.xMax, plane) + h,
                            Converter.yCrt2Scr(0.0 + i / yScale, plane)
                    )
                    g.drawLine(
                            Converter.xCrt2Scr(plane.xMin, plane) - h,
                            Converter.yCrt2Scr(0.0 + i / yScale, plane),
                            Converter.xCrt2Scr(plane.xMin, plane) + h,
                            Converter.yCrt2Scr(0.0 + i / yScale, plane)
                    )
                } else {
                    g.drawLine(
                            Converter.xCrt2Scr(0.0, plane) - h,
                            Converter.yCrt2Scr(0.0 + i / yScale, plane),
                            Converter.xCrt2Scr(0.0, plane) + h,
                            Converter.yCrt2Scr(0.0 + i / yScale, plane)
                    )
                }
                g.color = Color.BLUE
                if (i % (if (plane.yMax <= 10 && plane.yMin >= -10) 1 else if (plane.yMax > 50) 10 else if (plane.yMin < -50 && plane.yMax <= 50 && plane.yMax >= 5) 10 else 5) == 0) {
                    if ((plane.xMax <= 3 && plane.xMax > 0 && plane.xMin <= -80)) {
                        g.drawString(i.toString(), Converter.xCrt2Scr(0.0, plane) - 25, Converter.yCrt2Scr(0.0 + i, plane) + 4)
                    } else {
                        if (plane.xMin >= 0 || plane.xMax <= 0) {
                            g.drawString(i.toString(), Converter.xCrt2Scr(plane.xMin, plane) + 15, Converter.yCrt2Scr(0.0 + i, plane) + 4)
                            g.drawString(i.toString(), Converter.xCrt2Scr(plane.xMax, plane) - 25, Converter.yCrt2Scr(0.0 + i, plane) + 4)
                        } else {
                            g.drawString(i.toString(), Converter.xCrt2Scr(0.0, plane) + 15, Converter.yCrt2Scr(0.0 + i, plane) + 4)
                        }
                    }
                }
            }
            // Разметка отрицательной оси у
            for (i in 1..(((Converter.yCrt2Scr(0.0, plane) + Converter.yCrt2Scr(plane.yMin, plane)) / 5) * 10)) {
                val h = if (i % (if (plane.yMin > -5) 10 else 5) == 0) 10 else 5
                g.color = Color.BLACK
                if (plane.xMin >= 0 || plane.xMax <= 0) {
                    g.drawLine(//рисуем двойную шкалу в случае, когда ось y уходит за пределы экрана
                            Converter.xCrt2Scr(plane.xMax, plane) - h,
                            Converter.yCrt2Scr(0.0 - i / yScale, plane),
                            Converter.xCrt2Scr(plane.xMax, plane) + h,
                            Converter.yCrt2Scr(0.0 - i / yScale, plane)
                    )
                    g.drawLine(
                            Converter.xCrt2Scr(plane.xMin, plane) - h,
                            Converter.yCrt2Scr(0.0 - i / yScale, plane),
                            Converter.xCrt2Scr(plane.xMin, plane) + h,
                            Converter.yCrt2Scr(0.0 - i / yScale, plane)
                    )
                } else {
                    g.drawLine(
                            Converter.xCrt2Scr(0.0, plane) - h,
                            Converter.yCrt2Scr(0.0 - i / yScale, plane),
                            Converter.xCrt2Scr(0.0, plane) + h,
                            Converter.yCrt2Scr(0.0 - i / yScale, plane)
                    )
                }
                g.color = Color.BLUE
                if (i % (if (plane.yMax <= 10 && plane.yMin >= -10) 1 else if (plane.yMin < -50) 10 else if (plane.yMax > 50 && plane.yMin >= -50 && plane.yMin <= -5) 10 else 5) == 0) {
                    if ((plane.xMax <= 3 && plane.xMax > 0 && plane.xMin <= -80)) {
                        g.drawString("-" + i.toString(), Converter.xCrt2Scr(0.0, plane) - 25, Converter.yCrt2Scr(0.0 - i, plane) + 4)
                    } else {
                        if (plane.xMin >= 0 || plane.xMax <= 0) {
                            g.drawString("-" + i.toString(), Converter.xCrt2Scr(plane.xMin, plane) + 15, Converter.yCrt2Scr(0.0 - i, plane) + 4)
                            g.drawString("-" + i.toString(), Converter.xCrt2Scr(plane.xMax, plane) - 25, Converter.yCrt2Scr(0.0 - i, plane) + 4)
                        } else {
                            g.drawString("-" + i.toString(), Converter.xCrt2Scr(0.0, plane) + 15, Converter.yCrt2Scr(0.0 - i, plane) + 4)
                        }
                    }
                }
            }
        }
    }
}