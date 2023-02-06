package com.irshadkumail.game

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irshadkumail.game.ui.theme.DarkGreen
import com.irshadkumail.game.ui.theme.GameTheme
import com.irshadkumail.game.ui.theme.LightGreen

class ExitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTheme {
                ExitScreen(onClick = {
                    startActivity(Intent(baseContext, HomeActivity::class.java))
                }, gameOver = {
                    finishAffinity()
                })
            }
        }
    }

    override fun onBackPressed() {
        //Nothing
    }
}

@Composable
fun ExitScreen(onClick: () -> Unit, gameOver: () -> Unit) {


    Column(
        modifier = Modifier
            .background(LightGreen)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "GAME OVER", style = TextStyle(
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                fontFamily = FontFamily(Font(R.font.samson))
            )
        )
        Spacer(modifier = Modifier.height(36.dp))
        Button(modifier = Modifier.background(
            color = DarkGreen, shape = RoundedCornerShape(10)
        ), onClick = { onClick.invoke() }, content = {
            Text(
                text = "RETRY",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = LightGreen,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.samson))
                )
            )
        })
        Spacer(modifier = Modifier.height(18.dp))
        Button(modifier = Modifier.background(
            color = DarkGreen, shape = RoundedCornerShape(10)
        ), onClick = { onClick.invoke() }, content = {
            Text(
                text = "EXIT",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = LightGreen,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.samson))
                )
            )
        })


    }
}