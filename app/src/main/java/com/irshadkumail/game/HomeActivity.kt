package com.irshadkumail.game

import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.irshadkumail.game.HomeViewModel.Companion.GAME_BOARD_HEIGHT
import com.irshadkumail.game.HomeViewModel.Companion.GAME_BOARD_WIDTH
import com.irshadkumail.game.ui.theme.DarkGreen
import com.irshadkumail.game.ui.theme.GameTheme
import com.irshadkumail.game.ui.theme.LightGreen


@RequiresApi(Build.VERSION_CODES.S)
class HomeActivity : AppCompatActivity() {

    val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.gameOverEvent.observe(this) {
            if (it == true) {
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                finish()
                startActivity(Intent(this, ExitActivity::class.java))
            }
        }

        setContent {
            GameTheme {
                GameBoard(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun GameBoard(viewModel: HomeViewModel) {

    val paddleOneLeft = remember { MutableInteractionSource() }
    val paddleOneRight = remember { MutableInteractionSource() }
    val paddleTwoLeft = remember { MutableInteractionSource() }
    val paddleTwoRight = remember { MutableInteractionSource() }

    if (paddleOneLeft.collectIsPressedAsState().value) {
        viewModel.updateFirstPaddle(viewModel.firstPaddle.start.first - 1)
    }

    if (paddleOneRight.collectIsPressedAsState().value) {
        viewModel.updateFirstPaddle(viewModel.firstPaddle.start.first + 1)
    }

    if (paddleTwoLeft.collectIsPressedAsState().value) {
        viewModel.updateSecondPaddle(viewModel.secondPaddle.start.first - 1)
    }

    if (paddleTwoRight.collectIsPressedAsState().value) {
        viewModel.updateSecondPaddle(viewModel.secondPaddle.start.first + 1)
    }

    Column(
        modifier = Modifier.background(
            color = DarkGreen
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                interactionSource = paddleOneLeft,
                onClick = {
                }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Arrow Left",
                    tint = LightGreen
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                interactionSource = paddleOneRight,
                onClick = {}) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow Right",
                    tint = LightGreen
                )
            }

        }
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colors.background
                )
        ) {
            val tileWidth: Dp = maxWidth / GAME_BOARD_WIDTH
            val tileHeight: Dp = maxHeight / GAME_BOARD_HEIGHT

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .background(LightGreen)
            )

            //Ball
            Box(
                modifier = Modifier
                    .offset(
                        x = (tileWidth * viewModel.ball.currentCentre.first) - (tileWidth),
                        y = (tileHeight * viewModel.ball.currentCentre.second) - (tileWidth)
                    )
                    .size(tileWidth * 2)
                    .background(color = DarkGreen, shape = CircleShape)
            )

            //Paddle One
            Box(
                modifier = Modifier
                    .offset(x = tileWidth * viewModel.firstPaddle.start.first)
                    .size(
                        width = tileWidth * viewModel.firstPaddle.width,
                        height = tileHeight * viewModel.firstPaddle.height
                    )
                    .background(color = DarkGreen, shape = RoundedCornerShape(10))
            )

            //Paddle Two
            Box(
                modifier = Modifier
                    .offset(
                        x = tileWidth * viewModel.secondPaddle.start.first,
                        y = tileHeight * viewModel.secondPaddle.start.second
                    )
                    .size(
                        width = tileWidth * viewModel.secondPaddle.width,
                        height = tileHeight * viewModel.secondPaddle.height
                    )
                    .background(color = DarkGreen, shape = RoundedCornerShape(10))
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                modifier = Modifier.weight(1f),
                interactionSource = paddleTwoLeft,
                onClick = {
                }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Arrow Left",
                    tint = LightGreen
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                interactionSource = paddleTwoRight,
                onClick = {
                }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow Right",
                    tint = LightGreen
                )
            }

        }
    }
}