
package example

class Chess {
  type Field = Option[Piece]
  val board: Array[Array[Field]] = Array.fill(8,8)(None)
  var playerTurn = EColor.White

  {
    newGame()
  }

  def newGame(): Unit = {
    // crne figure
    board(0)(0) = Some(Rook(EColor.Black))
    board(0)(1) = Some(Knight(EColor.Black))
    board(0)(2) = Some(Bioshop(EColor.Black))
    board(0)(3) = Some(Queen(EColor.Black))
    board(0)(4) = Some(King(EColor.Black))
    board(0)(5) = Some(Bioshop(EColor.Black))
    board(0)(6) = Some(Knight(EColor.Black))
    board(0)(7) = Some(Rook(EColor.Black))

    // pesaci crni
    for (i <- 0 until 8) {
      board(1)(i) = Some(Pawn(EColor.Black))
    }

    // prazan deo table
    for (i <- 2 until 6; j <- 0 until 8) {
      board(i)(j) = None
    }

    // pesaci beli
    for (i <- 0 until 8) {
      board(6)(i) = Some(Pawn(EColor.White))
    }

    // bele figure
    board(7)(0) = Some(Rook(EColor.White))
    board(7)(1) = Some(Knight(EColor.White))
    board(7)(2) = Some(Bioshop(EColor.White))
    board(7)(3) = Some(Queen(EColor.White))
    board(7)(4) = Some(King(EColor.White))
    board(7)(5) = Some(Bioshop(EColor.White))
    board(7)(6) = Some(Knight(EColor.White))
    board(7)(7) = Some(Rook(EColor.White))

    playerTurn = EColor.White
  }

  // Pomeranje figure
  def movePiece(rowFrom: Int, colFrom: Int, rowTo: Int, colTo: Int): Boolean = {
    // Kraj ukoliko je pojeden kralj
    val isGameOver = board(rowTo)(colTo) match {
      case Some(_: King) => true
      case _          => false
    }

    board(rowTo)(colTo) = board(rowFrom)(colFrom)
    board(rowFrom)(colFrom) = None

    playerTurn = if (playerTurn == EColor.White) EColor.Black else EColor.White

    isGameOver
  }
}