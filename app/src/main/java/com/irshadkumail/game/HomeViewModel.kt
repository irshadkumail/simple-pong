package com.irshadkumail.game

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


sealed class Direction {
    data class TopRight(val speed: Int = 1) : Direction()
    data class TopLeft(val speed: Int = 1) : Direction()
    data class BottomLeft(val speed: Int = 1) : Direction()
    data class BottomRight(val speed: Int = 1) : Direction()
}

data class Paddle(
    val start: Pair<Int, Int>,
    val height: Int = 2,
    val width: Int = 10
)

data class Ball(
    val direction: Direction = Direction.BottomLeft(),
    val currentCentre: Pair<Int, Int>,
    val radians: Double = Math.toRadians(75.0),
    val speed: Int = 1,
)

class HomeViewModel : ViewModel() {

    val gameOverEvent = MutableLiveData<Boolean>()
    var ball by mutableStateOf(Ball(currentCentre = Pair(8, 16)))
    var firstPaddle by mutableStateOf(Paddle(start = Pair(6, 0)))
    var secondPaddle by mutableStateOf(
        Paddle(
            start = Pair(6, GAME_BOARD_HEIGHT - 2),
        )
    )

    init {
        viewModelScope.launch {
            while (true) {
                delay(60)

                var nextPosition = when (ball.direction) {
                    is Direction.BottomLeft ->
                        Pair(
                            (ball.currentCentre.first + ball.speed),
                            (ball.currentCentre.second + 1)
                        )
                    is Direction.BottomRight ->
                        Pair(
                            (ball.currentCentre.first - ball.speed),
                            (ball.currentCentre.second + 1)
                        )
                    is Direction.TopLeft ->
                        Pair(
                            (ball.currentCentre.first - 1),
                            (ball.currentCentre.second - ball.speed)
                        )
                    else ->
                        Pair(
                            (ball.currentCentre.first + 1),
                            (ball.currentCentre.second - ball.speed)
                        )
                }


                if (nextPosition.second <= (firstPaddle.height + firstPaddle.start.second) && nextPosition.first > firstPaddle.start.first && nextPosition.first <= (firstPaddle.start.first + firstPaddle.width)) {
                    val randomSpeed = Random.nextInt(1, 3)
                    if (ball.direction is Direction.TopRight) {
                        ball =
                            ball.copy(direction = Direction.BottomLeft(), speed = randomSpeed)
                    } else if (ball.direction is Direction.TopLeft) {
                        ball =
                            ball.copy(direction = Direction.BottomRight(), speed = randomSpeed)
                    }
                } else if (nextPosition.second >= secondPaddle.start.second && nextPosition.first >= secondPaddle.start.first && nextPosition.first < (secondPaddle.start.first + secondPaddle.width)) {
                    val randomSpeed = Random.nextInt(1, 3)
                    if (ball.direction is Direction.BottomLeft) {
                        ball = ball.copy(direction = Direction.TopRight(), speed = randomSpeed)
                    } else if (ball.direction is Direction.BottomRight) {
                        ball = ball.copy(direction = Direction.TopLeft(), speed = randomSpeed)
                    }
                } else if (nextPosition.second <= 0) {
                    break
                    /*  val randomSpeed = Random.nextInt(1, 3)
                     if (ball.direction is Direction.TopRight) {
                         ball =
                             ball.copy(direction = Direction.BottomLeft(), speed = randomSpeed)
                     } else if (ball.direction is Direction.TopLeft) {
                         ball =
                             ball.copy(direction = Direction.BottomRight(), speed = randomSpeed)
                     }*/
                } else if (nextPosition.second >= GAME_BOARD_HEIGHT) {
                    break
                    /*val randomSpeed = Random.nextInt(1, 3)
                     if (ball.direction is Direction.BottomLeft) {
                         ball = ball.copy(direction = Direction.TopRight(), speed = randomSpeed)
                     } else if (ball.direction is Direction.BottomRight) {
                         ball = ball.copy(direction = Direction.TopLeft(), speed = randomSpeed)
                     }*/
                } else if (nextPosition.first >= GAME_BOARD_WIDTH) {
                    val randomSpeed = Random.nextInt(1, 3)
                    if (ball.direction is Direction.BottomLeft) {
                        ball =
                            ball.copy(direction = Direction.BottomRight(), speed = randomSpeed)
                    } else if (ball.direction is Direction.TopRight) {
                        ball = ball.copy(direction = Direction.TopLeft(), speed = randomSpeed)
                    }
                } else if (nextPosition.first <= 0) {
                    val randomSpeed = Random.nextInt(1, 3)
                    if (ball.direction is Direction.BottomRight) {
                        ball = ball.copy(direction = Direction.BottomLeft(), speed = randomSpeed)
                    } else if (ball.direction is Direction.TopLeft) {
                        ball = ball.copy(direction = Direction.TopRight(), speed = randomSpeed)
                    }
                }

                Log.d("kumail", "${nextPosition.first} ${nextPosition.second}")

                ball = ball.copy(currentCentre = nextPosition)
            }
            gameOverEvent.value = true
        }
    }

    companion object {
        const val GAME_BOARD_WIDTH = 32
        const val GAME_BOARD_HEIGHT = 64
    }

    fun updateFirstPaddle(xOffset: Int) {
        if (xOffset >= 0 && xOffset <= GAME_BOARD_WIDTH - firstPaddle.width) {
            firstPaddle = firstPaddle.copy(start = Pair(xOffset, firstPaddle.start.second))
        }
    }

    fun updateSecondPaddle(xOffset: Int) {
        if (xOffset >= 0 && xOffset <= GAME_BOARD_WIDTH - secondPaddle.width) {
            secondPaddle = secondPaddle.copy(start = Pair(xOffset, secondPaddle.start.second))
        }
    }

}