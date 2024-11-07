package edu.farmingdale.datastoredemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.farmingdale.datastoredemo.ui.theme.DataStoreDemoTheme
import edu.farmingdale.datastoredemo.ui.EmojiReleaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreDemoTheme {
                EmojiReleaseApp() //locate where this is

            }
        }
    }
}
