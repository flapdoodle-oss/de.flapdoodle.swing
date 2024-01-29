package de.flapdoodle.swing

import java.awt.BorderLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

class SampleApp(title: String) : JFrame(title) {
  init {
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    setSize(300, 300)

    val textToCopy = JTextField("")
    textToCopy.name = "textToCopy"
    val copyButton = JButton("copyButton")
    copyButton.name = "copyButton"
    val copiedText = JLabel("")
    copiedText.name = "copiedText"

    copyButton.addActionListener { action -> copiedText.text = textToCopy.text }
//    copyButton.addMouseListener(object : MouseListener {
//      override fun mouseClicked(mouseEvent: MouseEvent) {
//        println("clicked")
//        copiedText.text = textToCopy.text
//      }
//
//      override fun mousePressed(mouseEvent: MouseEvent) {
//      }
//
//      override fun mouseReleased(mouseEvent: MouseEvent) {
//      }
//
//      override fun mouseEntered(mouseEvent: MouseEvent) {
//      }
//
//      override fun mouseExited(mouseEvent: MouseEvent) {
//      }
//    })


    //		copyButton.addActionListener(actionEvent -> {
//
//		});
    contentPane.layout=BorderLayout()
    contentPane.add(textToCopy, BorderLayout.NORTH)
    contentPane.add(copyButton, BorderLayout.CENTER)
    contentPane.add(copiedText, BorderLayout.SOUTH)
  }

  companion object {
    @JvmStatic
    fun main(vararg args: String) {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      val frame = SampleApp("Sample")
      frame.isVisible = true
    }
  }
}