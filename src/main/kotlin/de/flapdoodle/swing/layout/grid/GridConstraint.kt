package de.flapdoodle.swing.layout.grid

data class GridConstraint(
  val column: Int,
  val row: Int,
  val horizontalPosition: HPos? = null,
  val verticalPosition: VPos? = null
) {
  fun asPos(): GridMap.Pos {
    return GridMap.Pos(column,row)
  }
}
