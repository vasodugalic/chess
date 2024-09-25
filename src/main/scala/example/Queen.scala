

package example

case class Queen(override val color: EColor.Value) extends Piece(color) {
  override def getImgPath: String = color match {
    case EColor.Black => "/img/queen_black.png"
    case EColor.White => "/img/queen_white.png"
  }

  override def isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, board: Array[Array[Option[Piece]]]): Boolean = {
    val rowDiff = Math.abs(toRow - fromRow)
    val colDiff = Math.abs(toCol - fromCol)

    if (rowDiff == colDiff) {
      // dijagonalno
      val rowStep = if (toRow > fromRow) 1 else -1
      val colStep = if (toCol > fromCol) 1 else -1

      for (i <- 1 until rowDiff) {
        if (board(fromRow + i * rowStep)(fromCol + i * colStep).isDefined) return false
      }
      true
    } else if (rowDiff == 0) {
      // horizontalno
      val colStep = if (toCol > fromCol) 1 else -1
      for (i <- 1 until colDiff) {
        if (board(fromRow)(fromCol + i * colStep).isDefined) return false
      }
      true
    } else if (colDiff == 0) {
      // vertikalno
      val rowStep = if (toRow > fromRow) 1 else -1
      for (i <- 1 until rowDiff) {
        if (board(fromRow + i * rowStep)(fromCol).isDefined) return false
      }
      true
    } else {
      false
    }
  }
}