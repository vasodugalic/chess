

package example

case class Bioshop(override val color: EColor.Value) extends Piece(color) {
  override def getImgPath: String = color match {
    case EColor.Black => "/img/bioshop_black.png"
    case EColor.White => "/img/bioshop_white.png"
  }

  override def isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, board: Array[Array[Option[Piece]]]): Boolean = {
    val rowDiff = Math.abs(toRow - fromRow)
    val colDiff = Math.abs(toCol - fromCol)

    if (rowDiff == colDiff) {
      val rowStep = if (toRow > fromRow) 1 else -1
      val colStep = if (toCol > fromCol) 1 else -1

      for (i <- 1 until rowDiff) {
        if (board(fromRow + i * rowStep)(fromCol + i * colStep).isDefined) return false
      }
      true
    } else {
      false
    }
  }
}