

package example

case class King(override val color: EColor.Value) extends Piece(color) {
  override def getImgPath: String = color match {
    case EColor.Black => "/img/king_black.png"
    case EColor.White => "/img/king_white.png"
  }

  override def isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, board: Array[Array[Option[Piece]]]): Boolean = {
    val rowDiff = Math.abs(toRow - fromRow)
    val colDiff = Math.abs(toCol - fromCol)
    (rowDiff <= 1 && colDiff <= 1)
  }
}