package de.flapdoodle.swing.layout.absolute

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager2

class AbsoluteLayout : LayoutManager2 {
  override fun addLayoutComponent(component: Component, p1: Any?) {

  }

  override fun addLayoutComponent(p0: String, p1: Component) {
    throw IllegalArgumentException("not supported")
  }

  override fun removeLayoutComponent(component: Component) {
  }

  override fun preferredLayoutSize(target: Container): Dimension {
    TODO("Not yet implemented")
//    target.components.map { it -> it.getBounds() }
  }

  override fun minimumLayoutSize(target: Container): Dimension {
    TODO("Not yet implemented")
  }

  override fun maximumLayoutSize(p0: Container?): Dimension {
    TODO("Not yet implemented")
  }

  override fun layoutContainer(target: Container) {

  }

  override fun getLayoutAlignmentX(target: Container): Float {
    return 0.5f
  }

  override fun getLayoutAlignmentY(target: Container): Float {
    return 0.5f
  }

  override fun invalidateLayout(target: Container) {
    
  }
}