package de.flapdoodle.swing.playground

import java.awt.Dimension
import javax.swing.*

object AbsoluteLayoutSampleApp {
  @JvmStatic
  fun main(vararg args: String) {
    val frame = JFrame("Absolute Layout Sample")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(800, 600)

    frame.contentPane = nodeEditor()
    frame.isVisible = true
  }

  private fun nodeEditor(): JComponent {
    val ret = JScrollPane(JPanel().also {
      it.layout = null
      it.preferredSize = Dimension(800, 600)
      it.add(JButton("A").also { button ->
        button.setBounds(90,10,200,300)
      })

      it.add(JButton("B").also { button ->
        button.setBounds(-50,30,100,100)
      })
    })
    return ret
  }
}