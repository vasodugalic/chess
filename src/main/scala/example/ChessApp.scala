package example

import scala.swing._
import scala.swing.event._
import java.awt.{Color, Dimension, Toolkit, Point}
import javax.swing.ImageIcon

object ChessApp extends SimpleSwingApplication {

  val chess = new Chess()
  var selectedPosition: Option[(Int,Int,Piece)] = None

  val turnLabel = new Label {
    text = s"Player on turn: ${chess.playerTurn}"
  }

  val newGameButton = new Button {
    text = "New game"
  }

  val boardButtons = Array.ofDim[Button](8, 8)

  val boardPanel = new GridPanel(8, 8) {
    for (row <- 0 until 8; col <- 0 until 8) {
      val btn = new Button {
        preferredSize = new Dimension(60, 60)
        background = if ((row + col) % 2 == 0) Color.WHITE else Color.GRAY
      }
      boardButtons(row)(col) = btn
      contents += btn
    }
  }

  def top: MainFrame = new MainFrame {

    title = "Chess"
    preferredSize = new Dimension(650, 700)

    // Centriranje prozora
    val screenSize = Toolkit.getDefaultToolkit.getScreenSize
    val frameSize = this.preferredSize
    val x = (screenSize.width - frameSize.width) / 2
    val y = (screenSize.height - frameSize.height) / 2
    location = new Point(x, y)

    contents = new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel {
        contents += turnLabel 
      } 
      contents += boardPanel
      contents += new FlowPanel {
        contents += newGameButton
      }
    }

    // Listener za novu igru
    listenTo(newGameButton)
    reactions += {
      case ButtonClicked(`newGameButton`) =>
        chess.newGame()
        updateBoard()
        turnLabel.text = s"Player on turn: ${chess.playerTurn}"
        selectedPosition = None
    }

    // Listener za tablu
    for (row <- 0 until 8; col <- 0 until 8) {
      listenTo(boardButtons(row)(col))
      reactions += {
        case ButtonClicked(button) if button == boardButtons(row)(col) =>
          selectedPosition match {
            case Some((fromRow, fromCol, piece)) =>
              chess.board(row)(col) match {
                // ako postoji figura na polju na kojem pomeramo odabranu figuru, proveri da li su iste boje
                // ako su iste boje, odaberi drugu figuru za pomeranje
                case Some(destinationPiece) =>
                  if (piece.color != destinationPiece.color) {
                    handleMove(fromRow, fromCol, row, col, piece, chess.board)
                  } else {
                    selectedPosition = Some(row, col, destinationPiece)
                  }
                case None =>
                  handleMove(fromRow, fromCol, row, col, piece, chess.board)
              }
            case None =>
              chess.board(row)(col) match {
                case Some(piece) => 
                  if (chess.playerTurn == piece.color) {
                    selectedPosition = Some((row, col, piece))
                  } 
                case None => //
              }
          }
      }
    }
  }

  // Azuriranje dugmica na tabli na osnovu stanja igre
  def updateBoard(): Unit = {
    for (row <- 0 until 8; col <- 0 until 8) {
      val piece = chess.board(row)(col)
      boardButtons(row)(col).icon = piece match {
        case Some(p) => {
          val imgPath = p.getImgPath
          new ImageIcon(Toolkit.getDefaultToolkit.getImage(getClass.getResource(imgPath)))
        }
        case None    => null
      }
    }
  }

  def handleMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, piece: Piece, board: Array[Array[Option[Piece]]]): Unit = {
    if (piece.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
      val isGameOver = chess.movePiece(fromRow, fromCol, toRow, toCol)
      updateBoard()
      if (isGameOver) { // kraj igre
        var winner = chess.playerTurn match {
          case EColor.White => "Black"
          case EColor.Black => "White"
        }
        val result = Dialog.showConfirmation(
          title = "Game Over",
          message = s"${winner} player is the winner. Do you want to play again?",
          optionType = Dialog.Options.YesNo
        )
        if (result == Dialog.Result.Yes) {
          chess.newGame()
          updateBoard()
          turnLabel.text = s"Player on turn: ${chess.playerTurn}"
          selectedPosition = None
        } else {
          sys.exit(0)
        }
      } else {
        turnLabel.text = s"Player on turn: ${chess.playerTurn}"
        selectedPosition = None
      }
    }
  }

  {
    chess.newGame()
    updateBoard()
  }
}
