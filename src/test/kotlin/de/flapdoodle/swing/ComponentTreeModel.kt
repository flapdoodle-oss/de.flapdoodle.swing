package de.flapdoodle.swing

import de.flapdoodle.swing.tips4j.ComponentTreeModel
import de.flapdoodle.swing.tips4j.SwingUtils
import java.awt.Dimension
import java.io.Serializable
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

//package darrylbu.model;
//import darrylbu.util.SwingUtils;

/**
 * A tree data model that reproduces the structure of a GUI to reveal the
 * hierarchical placement of components in containers.  Also provides a
 * static method to facilitate adding such a hierarchy at a specified node
 * in an existing `DefaultTreeModel`.
 * <P>
 * Extends [DefaultTreeModel].
 *
 * @version 1.0 11/15/08
 * @author Darryl
</P> */
class ComponentTreeModel private constructor(
  container: JComponent,
  root: DefaultMutableTreeNode, nested: Boolean
) : DefaultTreeModel(root), Serializable {

  /**
   * Creates a new tree model with a specified `JComponent`
   * as the root, optionally including nested components.
   *
   * @param container the `JComponent` to be placed at the root
   * of the tree
   * @param nested `true` to include nested components,
   * `false` otherwise
   */
  /**
   * Creates a new tree model with a specified `JComponent`
   * as the root, including nested components at all levels.
   *
   * @param container the `JComponent` to be placed at the root
   * of the tree
   * @see DefaultTreeModel
   */
  @JvmOverloads
  constructor(container: JComponent, nested: Boolean = true) : this(container, DefaultMutableTreeNode(container), nested)

  /**
   * private constructor for aligning the public constructors with the
   * static method getComponentTreeModel
   */
  init {
    addNodes(container, this, root, nested)
  }

  companion object {

    fun showTree(root: JComponent) {
      val tree = JTree(ComponentTreeModel(root))
      for (i in 0 until tree.rowCount) {
        tree.expandRow(i)
      }
      val scrollPane = JScrollPane(tree)

      //         scrollPane.setPreferredSize(new Dimension(570, 150));
      scrollPane.preferredSize = Dimension(950, 500)
      JOptionPane.showMessageDialog(
        root, scrollPane, "Component Tree",
        JOptionPane.PLAIN_MESSAGE
      )
    }

    fun rootOf(component: JComponent): JComponent? {
      var root: JComponent? = null
      if (root == null) {
        var parent: JComponent = component
        while (parent is JComponent && parent != null) {
          root = parent
          parent = parent.parent as JComponent
        }
      }
      return root
    }

    /**
     * Invoked to create a new model or to add a `JComponent`'s
     * hierarchy to an existing `DefaultTreeModel`.
     *
     * @param container the `JComponent` to be placed at the root
     * of the tree or added at a node of an existing tree
     * @param model a `DefaultTreeModel` to which the
     * `container`'s GUI hierarchy will be added, or
     * `null` to create a new model
     * @param root the node on the model to which the containerand its
     * hierarchy will be added, or null for a model that has the container as
     * its root
     * @param nested `true` to include nested components,
     * `false` otherwise
     * @return the `model`, or a new `DefaultTreeModel`
     * if the `model` parameter is null
     */
    fun getComponentTreeModel(
      container: JComponent,
      model: DefaultTreeModel?, root: DefaultMutableTreeNode?, nested: Boolean
    ): DefaultTreeModel {
      var model = model
      var root = root
      val node = DefaultMutableTreeNode(container)
      if (model == null) {
        model = DefaultTreeModel(node)
      }
      if (root == null) {
        root = node
      }
      if (model.root == null) {
        model.setRoot(root)
      } else {
        root.add(node)
      }
      addNodes(container, model, node, nested)
      return model
    }

    /**
     * private method to obtain the hierarchy devolving from the root
     * container and add nodes to the tree accordingly.
     */
    private fun addNodes(
      container: JComponent, model: DefaultTreeModel,
      root: DefaultMutableTreeNode, nested: Boolean
    ) {
      val componentMap = SwingUtils.getComponentMap(
        container, true
      )
      val components = componentMap[container]
      if (components != null) {
        for (component in componentMap[container]!!) {
          val branch = DefaultMutableTreeNode(component)
          branch.userObject = component
          root.add(branch)
          if (nested) {
            addNodes(component, model, branch, true)
          }
        }
      }
    }
  }
}
