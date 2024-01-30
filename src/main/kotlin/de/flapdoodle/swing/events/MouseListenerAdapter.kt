package de.flapdoodle.swing.events

import java.awt.event.MouseEvent
import java.awt.event.MouseListener

typealias MouseEventFunction = (MouseEvent) -> Unit

data class MouseListenerAdapter(
  val clicked: MouseEventFunction? = null,
  val pressed: MouseEventFunction? = null,
  val released: MouseEventFunction? = null,
  val entered: MouseEventFunction? = null,
  val exited: MouseEventFunction? = null
) : MouseListener {
  override fun mouseClicked(event: MouseEvent) {
    clicked?.let { it(event) }
  }

  override fun mousePressed(event: MouseEvent) {
    pressed?.let { it(event) }
  }

  override fun mouseReleased(event: MouseEvent) {
    released?.let { it(event) }
  }

  override fun mouseEntered(event: MouseEvent) {
    entered?.let { it(event) }
  }

  override fun mouseExited(event: MouseEvent) {
    exited?.let { it(event) }
  }
}
