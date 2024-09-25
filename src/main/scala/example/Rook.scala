

package example

case class Rook(override val color: EColor.Value) extends Piece(color) {
  override def getImgPath: String = color match {
    case EColor.Black => "/img/rook_black.png"
    case EColor.White => "/img/rook_white.png"
  }

  override def isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, board: Array[Array[Option[Piece]]]): Boolean = {
    val rowDiff = toRow - fromRow
    val colDiff = toCol - fromCol

    if (rowDiff == 0) {
      // Horizontalno
      val colStep = if (colDiff > 0) 1 else -1
      for (col <- (fromCol + colStep) until toCol by colStep) {
        if (board(fromRow)(col).isDefined) return false
      }
      true
    } else if (colDiff == 0) {
      // Vertikalno
      val rowStep = if (rowDiff > 0) 1 else -1
      for (row <- (fromRow + rowStep) until toRow by rowStep) {
        if (board(row)(fromCol).isDefined) return false
      }
      true
    } else {
      false
    }
  }
}