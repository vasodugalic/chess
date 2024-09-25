

package example

case class Pawn(override val color: EColor.Value) extends Piece(color) {
  override def getImgPath: String = color match {
    case EColor.Black => "/img/pawn_black.png"
    case EColor.White => "/img/pawn_white.png"
  }

  override def isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, board: Array[Array[Option[Piece]]]): Boolean = {
    var direction = if (color == EColor.White) -1 else 1 // -1 za GORE, 1 za DOLE
    val rowDiff = toRow - fromRow
    val colDiff = Math.abs(toCol - fromCol)

    if (colDiff == 0) {
      // Pomeranje napred
      (rowDiff == direction && board(toRow)(toCol).isEmpty) ||
      (fromRow == (if (color == EColor.White) 6 else 1) && rowDiff == 2 * direction && board(toRow)(toCol).isEmpty && board(fromRow + direction)(fromCol).isEmpty)
    } else if (colDiff == 1 && rowDiff == direction) {
      // Hvatanje dijagonalno
      board(toRow)(toCol).exists(_.color != color)
    } else {
      false
    }
  }
}