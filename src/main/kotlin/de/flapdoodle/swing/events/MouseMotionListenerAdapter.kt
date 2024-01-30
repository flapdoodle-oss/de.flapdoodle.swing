package de.flapdoodle.swing.events

import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

data class MouseMotionListenerAdapter(
  val dragged: MouseEventFunction? = null,
  val moved: MouseEventFunction? = null
) : MouseMotionListener {
  override fun mouseDragged(event: MouseEvent) {
    dragged?.let { it(event) }
  }

  override fun mouseMoved(event: MouseEvent) {
    moved?.let { it(event) }
  }

}
