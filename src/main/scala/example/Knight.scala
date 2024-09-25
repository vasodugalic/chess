

package example

case class Knight(override val color: EColor.Value) extends Piece(color) {
  override def getImgPath: String = color match {
    case EColor.Black => "/img/knight_black.png"
    case EColor.White => "/img/knight_white.png"
  }

  override def isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, board: Array[Array[Option[Piece]]]): Boolean = {
    val rowDiff = Math.abs(toRow - fromRow)
    val colDiff = Math.abs(toCol - fromCol)
    (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)
  }
}