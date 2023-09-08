package com.example.lemonade

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    // Should be used within Composable function. Required for a toast.
    val context = LocalContext.current

    /* Current step the app is displaying.

    When the step variable gets updated to a new value, Compose triggers recomposition of the
    LemonadeApp composable, which means that the composable will execute again. The step value
    is remembered across recompositions. */
    var step by remember { mutableStateOf(1) }

    // Random value from 2 to 4 for 2nd step.
    var squeezeCount = (2..4).random()

    Column {
        Box(
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Lemonade",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        when (step) {
            1 -> LemonadeImageAndLabel(
                R.drawable.lemon_tree,
                R.string.lemon_tree_desc,
                R.string.lemon_tree,
                { step++ },
            )

            2 -> LemonadeImageAndLabel(
                R.drawable.lemon_squeeze,
                R.string.lemon_squeeze_desc,
                R.string.lemon_squeeze,
                {
                    if (squeezeCount != 1) showToast(context, "${--squeezeCount} clicks left")
                    else step++
                },
            )

            3 -> LemonadeImageAndLabel(
                R.drawable.lemon_drink,
                R.string.lemon_drink_desc,
                R.string.lemon_drink,
                { step++ },
            )

            4 -> LemonadeImageAndLabel(
                R.drawable.lemon_restart,
                R.string.lemon_restart_desc,
                R.string.lemon_restart,
                {
                    step = 1
                    squeezeCount = (2..4).random()
                },
            )
        }
    }

}

@Composable
fun LemonadeImageAndLabel(
    imageResource: Int,
    accessibilityResource: Int,
    labelResource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = accessibilityResource),
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .clickable {
                    onClick()
                }
                .size(250.dp)
                .background(Color(0xFFC3ECD2))
                .padding(24.dp),
        )
        Text(
            text = stringResource(id = labelResource),
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 18.sp,
        )
    }
}

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}