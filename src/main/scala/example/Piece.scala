

package example

abstract class Piece(val color: EColor.Value) {
  def getImgPath: String
  def isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, board: Array[Array[Option[Piece]]]): Boolean
}