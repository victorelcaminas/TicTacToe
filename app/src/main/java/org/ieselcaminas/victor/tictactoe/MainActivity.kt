package org.ieselcaminas.victor.tictactoe

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.jar.Attributes

class MainActivity : AppCompatActivity() {

    enum class Players{
        PLAYER1, PLAYER2
    }

    class ButtonTTT(context: Context, atrribSet: AttributeSet?, style: Int):
                        Button(context, atrribSet, style) {
        var player: Players? = null
    }

    val NUM_LINES = 3
    lateinit var arrayButtons: Array<ButtonTTT>
    val SYMBOL_PLAYER1 = "X"
    val SYMBOL_PLAYER2 = "O"
    var gameOver = false

    var turn: Players = Players.PLAYER1
    lateinit var textViewTurn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createButtons()
        createBoard()
        textViewTurn = findViewById<TextView>(R.id.textView_Turn)
        displayTurn()

    }

    private fun displayTurn() {
        if (turn == Players.PLAYER1) {
            textViewTurn.setText("Turn Player 1 ($SYMBOL_PLAYER1)")
        } else {
            textViewTurn.setText("Turn Player 2 ($SYMBOL_PLAYER2)")
        }
    }

    private fun createButtons() {
        arrayButtons = Array<ButtonTTT>(NUM_LINES * NUM_LINES) {
            ButtonTTT(this, null, android.R.attr.buttonStyleSmall)
        }
        for (button in arrayButtons) {
            button.setOnClickListener() {
                shoot(button)
            }
        }
    }

    private fun shoot(button: ButtonTTT) {
        button.player = turn
        if (turn == Players.PLAYER1) {
            button.setText(SYMBOL_PLAYER1)

        } else {
            button.setText(SYMBOL_PLAYER2)
        }
        button.isClickable = false
        gameOver = checkWinner(button)
        if (!gameOver) {
            changeTurn()
            displayTurn()
        }
    }

    private fun changeTurn() {
        if (turn == Players.PLAYER1) {
            turn = Players.PLAYER2
        } else {
            turn = Players.PLAYER1
        }
    }

    private fun checkWinner(button: ButtonTTT): Boolean {
        // Check rows
        var pair = getRowCol(button)
        var row = pair.first
        var col = pair.second
        var counter = 0
        for (i in 0 until NUM_LINES) {
            if (getButton(row, i)?.player == turn) {
                counter ++
            }
        }
        if (counter == NUM_LINES) {
            return true
        }
        counter = 0
        for (i in 0 until NUM_LINES) {
            if (getButton(i, col)?.player == turn) {
                counter ++
            }
        }
        if (counter == NUM_LINES) {
            return true
        }
        if (col == row) {
            for (i in 0 until)
        }

        return false
    }

    private fun createBoard() {
        val baseLinearLayout = findViewById<LinearLayout>(R.id.base_linear_layout)
        for (row in 0..NUM_LINES - 1) {
            val linearLayout = LinearLayout(this)
            linearLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
            for (col in 0..NUM_LINES - 1) {
                val button = getButton(row, col)
                button?.setText("  ")
                linearLayout.addView(button)
            }
            baseLinearLayout.addView(linearLayout)

        }
    }

    fun getRowCol(button: Button): Pair<Int, Int> {
        val index = arrayButtons.indexOf(button)
        val row = index / NUM_LINES
        val col = index % NUM_LINES
        return Pair(row, col)
    }

    fun getButton(row: Int, col: Int): ButtonTTT? {
        val index = row * NUM_LINES + col
        if (index < 0 || index >= NUM_LINES * NUM_LINES) {
            return null
        }
        return arrayButtons[index]
    }


}
