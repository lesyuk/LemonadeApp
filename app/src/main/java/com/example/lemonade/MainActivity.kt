package com.example.lemonade

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    Column {
        Box(
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Lemonade",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        LemonadeImageAndLabel(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .weight(0.8f)
        )
        Spacer(modifier = Modifier.weight(0.2f))
    }

}

@Composable
fun LemonadeImageAndLabel(
    modifier: Modifier = Modifier
) {
    // Should be used within Composable function. Required for a toast.
    val context = LocalContext.current

    /* Current step the app is displaying

    When the step variable gets updated to a new value, Compose triggers recomposition of the
    LemonadeImageAndLabel composable, which means that the composable will execute again. The step value
    is remembered across recompositions. */
    var step by remember {
        mutableStateOf(1)
    }

    var squeezeCount = (2..4).random()

    var imageResource = when (step) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    var accessibilityResource = when (step) {
        1 -> R.string.lemon_tree_desc
        2 -> R.string.lemon_squeeze_desc
        3 -> R.string.lemon_drink_desc
        else -> R.string.lemon_restart_desc
    }

    var labelResource = when (step) {
        1 -> R.string.lemon_tree
        2 -> R.string.lemon_squeeze
        3 -> R.string.lemon_drink
        else -> R.string.lemon_restart
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = accessibilityResource),
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .clickable {
                    when {
                        step == 2 && squeezeCount != 1 -> {
                            showToast(context, "${--squeezeCount} clicks left")
                        }

                        step != 4 -> step++
                        else -> {
                            step = 1
                            squeezeCount = (2..4).random()
                        }
                    }
                }
                .size(200.dp)
                .border(width = 2.dp, color = Color(0xFF69cdd8), shape = RoundedCornerShape(28.dp))
                .background(Color(0xFFC3ECD2))
                .padding(24.dp)
        )
        Text(
            text = stringResource(id = labelResource),
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 18.sp
        )
    }
}

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}