package de.flapdoodle.swing.playground

import de.flapdoodle.swing.layout.absolute.AbsoluteLayout
import java.awt.Container
import java.awt.Dimension
import java.awt.Rectangle
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane

object PlaygroundApp {
  @JvmStatic
  fun main(vararg args: String) {
    val frame = JFrame("Playground")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(800, 600)

    frame.contentPane = playground()

    if (false) frame.pack()
    frame.isVisible = true
  }

  private fun playground(): JComponent {
    val ret = JPanel()
    ret.layout =BoxLayout(ret, BoxLayout.Y_AXIS)
    ret.add(JButton("press me"))
    ret.add(nodeEditor())
    return ret
  }

  private fun nodeEditor(): JComponent {
    val ret = JScrollPane(JPanel().also {
//      it.layout = AbsoluteLayout()
      it.layout = null
//      it.bounds = Rectangle(0,0,200,200)
      it.preferredSize = Dimension(800, 600)
      it.add(JButton("A").also { button ->
        //button.minimumSize = Dimension(300, 200)
        button.preferredSize = Dimension(300, 200)
        button.setBounds(90,10,200,300)
      })

      it.add(JButton("B").also { button ->
        //button.minimumSize = Dimension(300, 200)
        button.preferredSize = Dimension(300, 200)
        button.setBounds(-50,30,100,100)
      })
    })
    return ret
  }
}