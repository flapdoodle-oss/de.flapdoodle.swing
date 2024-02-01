package de.flapdoodle.swing.playground

import de.flapdoodle.swing.ComponentTreeModel
import de.flapdoodle.swing.events.MouseListenerAdapter
import de.flapdoodle.swing.events.MouseMotionListenerAdapter
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.geom.AffineTransform
import java.util.concurrent.atomic.AtomicReference
import javax.swing.*
import javax.swing.plaf.LayerUI

object PlaygroundApp {
  @JvmStatic
  fun main(vararg args: String) {
    if (false) {
      val e: EventQueue = EventQueue()

      Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
//      println("event --> $event")
        if (event is MouseEvent) {
//        event.consume()
//        println("--> "+event.source)
          if (event.source is JButton) {
            event.consume()
          }
        }
      }, AWTEvent.MOUSE_EVENT_MASK)
    }

    val frame = JFrame("Playground")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(800, 600)

    frame.contentPane = playground()

    if (false) frame.pack()
    frame.isVisible = true
  }

  private fun playground(): JComponent {
    val ret = JPanel()
    ret.layout = BoxLayout(ret, BoxLayout.Y_AXIS)
    val button = JButton("show tree")
    val nodeEditor = nodeEditor()
    button.addActionListener { action ->
      ComponentTreeModel.showTree(nodeEditor)
    }
    ret.add(button)
    ret.add(nodeEditor)
    return ret
  }

  private fun nodeEditor(): JComponent {
    val subwindow = subWindow().also { win ->
      win.preferredSize = Dimension(100, 100)
      win.setBounds(350, 130, 100, 100)
    }

    val content = JPanel().also {
      it.layout = null
      //it.layout = DragLayout()

//      it.layout = AbsoluteLayout()
      it.addMouseListener(MouseListenerAdapter(
        pressed = { event ->
          println("pressed in panel")
//          event.consume()
        }
      ))
//      it.bounds = Rectangle(0,0,200,200)
      it.preferredSize = Dimension(800, 600)
      it.add(subwindow)
      it.add(JButton("A").also { button ->
        //button.minimumSize = Dimension(300, 200)
        button.preferredSize = Dimension(300, 200)
        button.setBounds(90, 10, 200, 300)
        button.addMouseListener(MouseListenerAdapter(
          pressed = { event ->
            println("pressed")
//            event.consume()
          }
        ))
        button.addActionListener { println("button pressed") }
      })

      it.add(JButton("B").also { button ->
        //button.minimumSize = Dimension(300, 200)
        button.preferredSize = Dimension(300, 200)
        button.setBounds(-50, 30, 100, 100)
      })
    }
    val ui=object : LayerUI<JPanel>() {
      override fun installUI(c: JComponent?) {
        super.installUI(c)
        // enable mouse motion events for the layer's subcomponents
        (c as JLayer<*>).layerEventMask = AWTEvent.MOUSE_EVENT_MASK or AWTEvent.MOUSE_MOTION_EVENT_MASK
      }

      override fun uninstallUI(c: JComponent?) {
        (c as JLayer<*>).layerEventMask = 0
        
        super.uninstallUI(c)
      }

      override fun eventDispatched(e: AWTEvent, l: JLayer<out JPanel>) {
        if (e.source == subwindow && e is MouseEvent) {
          println("subwindow --> "+e)
//          e.consume()
        }
        super.eventDispatched(e, l)
      }
    }
    val layer = JLayer<JPanel>(content, ui);
//    val glassPane =
    val ret = JScrollPane(layer)
    return ret
  }

  private fun subWindow(): JComponent {
    val ret = object : JPanel() {
      override fun paintComponent(g: Graphics?) {
        if (g is Graphics2D) {
//          g.scale(0.9, 0.9)
          val at = AffineTransform()
          at.scale(0.9, 0.9)
          g.transform(at)
        }
        super.paintComponent(g)
      }

      override fun update(g: Graphics?) {
        if (g is Graphics2D) {
//          g.scale(0.9, 0.9)
          val at = AffineTransform()
          at.scale(0.9, 0.9)
          g.transform(at)
        }
        super.update(g)
      }
    }

    ret.insets.set(10, 10, 10, 10)
    ret.background = Color.RED
    ret.isOpaque = true
    ret.add(JButton("move me").also {
      it.insets.set(10, 10, 10, 10)
    })
    val lock = AtomicReference<StartDrag>()

    ret.addMouseListener(MouseListenerAdapter(
      pressed = { event ->
        println("start "+event.point)
        lock.set(StartDrag(ret.location, event.locationOnScreen))
      },
      released = { event ->
        println("released "+event.point)
        lock.getAndSet(null)
      }
    ))
    ret.addMouseMotionListener(MouseMotionListenerAdapter(
      moved = { event ->
//        if (lock.get() != null) {
////          println("moved " + (event.point - lock.get()))
//        }
      },
      dragged = { event ->
        val oldPosition = lock.get()
        if (oldPosition != null) {
//          println("dragged " + (event.point - lock.get()))
          val delta = event.locationOnScreen - oldPosition.mouse
          val newLocation = oldPosition.pos + delta
          ret.setLocation(newLocation.x, newLocation.y)
        }
      }
    ))
    return ret
  }
}

data class StartDrag(val pos: Point, val mouse: Point)

private operator fun Point.plus(delta: Dimension): Point {
  return Point(x + delta.width, y + delta.height)
}

private operator fun Point.minus(other: Point): Dimension {
  return Dimension(this.x - other.x, this.y - other.y)
}
