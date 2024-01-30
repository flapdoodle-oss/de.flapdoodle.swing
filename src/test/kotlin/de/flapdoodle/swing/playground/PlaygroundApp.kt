package de.flapdoodle.swing.playground

import java.awt.Container
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

object PlaygroundApp {
  @JvmStatic
  fun main(vararg args: String) {
    val frame = JFrame("Playground")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(300, 300)

    frame.contentPane = playground()

    frame.isVisible = true
  }

  private fun playground(): Container {
    val ret = JPanel()
    ret.add(JButton("press me"))
    return ret
  }
}