package de.flapdoodle.swing.playground

import de.flapdoodle.swing.events.MouseListenerAdapter
import de.flapdoodle.swing.events.MouseMotionListenerAdapter
import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.event.MouseEvent
import javax.swing.*

object GlassPaneSampleApp {
  @JvmStatic
  fun main(vararg args: String) {
    val frame = JFrame("Glass Pane Sample")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(800, 600)

    frame.contentPane = nodeEditor()
    frame.glassPane = GlassPane(frame.contentPane)
//    frame.pack()
    frame.isVisible = true

    frame.glassPane.isVisible = true
  }

  private fun nodeEditor(): JComponent {
    val ret = JScrollPane(object : JPanel(), MouseEventFilter {
      override fun filter(event: MouseEvent): Boolean {
        println("filter $event")
        return false
      }
    }.also {
      it.layout = null
      it.preferredSize = Dimension(800, 600)
      it.add(JButton("A").also { button ->
        button.setBounds(90, 10, 200, 300)
      })

      it.add(JButton("B").also { button ->
        button.setBounds(-50, 30, 100, 100)
      })
    })
    return ret
  }

  interface MouseEventFilter {
    fun filter(event: MouseEvent): Boolean
  }

  class GlassPane(val container: Container) : JComponent() {
    init {
      addMouseListener(MouseListenerAdapter(
        entered = { event ->
//          val found = SwingUtilities.getDeepestComponentAt(container, event.x, event.y)
//          println("$event -> $found")
        },
        exited = { event ->
//          println(event)
        }
      ))
      addMouseMotionListener(
        MouseMotionListenerAdapter(
          moved = { event ->
            val found = SwingUtilities.getDeepestComponentAt(container, event.x, event.y)
            if (found!=null) {

              //Forward events over the check box.
              val componentPoint = SwingUtilities.convertPoint(
                container,
                event.point,
                found
              )
              val event = MouseEvent(
                found,
                event.getID(),
                event.getWhen(),
                event.getModifiers(),
                componentPoint.x,
                componentPoint.y,
                event.getClickCount(),
                event.isPopupTrigger()
              )
              val filtered = filter(found, event)
              if (!filtered) {
                found.dispatchEvent(
                  event
                )
              }
            }
//            println("$event -> $found")
          }
        )
      )
//      isVisible = true
    }

    private fun filter(start: Component, event: MouseEvent): Boolean {
      val filteredByParent = start.parent?.let { filter(it, event) } ?: false
      return filteredByParent || if (start is MouseEventFilter) {
        start.filter(event)
      } else {
        false
      }
    }
  }
}