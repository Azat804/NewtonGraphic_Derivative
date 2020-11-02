package ru.smak.gui

import PointPainter
import ru.smak.gui.components.ControlPanel
import ru.smak.gui.components.GraphicsPanel
import ru.smak.gui.graphics.CartesianPainter
import ru.smak.gui.graphics.convertation.CartesianScreenPlane
import java.awt.Color
import java.awt.Dimension
import java.awt.event.*
import javax.swing.GroupLayout
import javax.swing.JFrame
import java.awt.Graphics2D

class MainWindow : JFrame() {

    private val minSize = Dimension(550, 400)
    private val mainPanel: GraphicsPanel
    private val controlPanel: ControlPanel

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(minSize.width + 200, minSize.height + 400)
        mainPanel = GraphicsPanel()
        mainPanel.background = Color.WHITE
        controlPanel = ControlPanel()
        val gl = GroupLayout(contentPane)

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, minSize.height, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(4)
        )

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addGroup(
                        gl.createParallelGroup()
                                .addComponent(mainPanel, minSize.width, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                )
                .addGap(4))
        layout = gl
        pack()
        val plane = CartesianScreenPlane(
                mainPanel.width, mainPanel.height,
                controlPanel.smXMin.number.toDouble(),
                controlPanel.smXMax.number.toDouble(),
                controlPanel.smYMin.number.toDouble(),
                controlPanel.smYMax.number.toDouble()
        )
        val dp = CartesianPainter(plane)
        val gr = PointPainter(mainPanel.graphics, plane)
        mainPanel.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                dp.plane.realWidth = mainPanel.width
                dp.plane.realHeight = mainPanel.height
                mainPanel.paint(mainPanel.graphics)
                gr.update(mainPanel.graphics)
            }
        })

        mainPanel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.button == MouseEvent.BUTTON1) {
                    mainPanel.paint(mainPanel.graphics)
                    gr.drawPoints(e.x, e.y, mainPanel.graphics)
                }
                if (e?.button == MouseEvent.BUTTON3) {
                    mainPanel.paint(mainPanel.graphics)
                    gr.removePoints(e.x, e.y, mainPanel.graphics)
                }
            }
        })
        controlPanel.addValChangeListener {
            dp.plane.xMin = controlPanel.smXMin.number.toDouble()
            dp.plane.xMax = controlPanel.smXMax.number.toDouble()
            dp.plane.yMin = controlPanel.smYMin.number.toDouble()
            dp.plane.yMax = controlPanel.smYMax.number.toDouble()
            //   mainPanel.repaint()
            mainPanel.paint(mainPanel.graphics)
            gr.update(mainPanel.graphics)
        }
        mainPanel.painter = dp
    }
}