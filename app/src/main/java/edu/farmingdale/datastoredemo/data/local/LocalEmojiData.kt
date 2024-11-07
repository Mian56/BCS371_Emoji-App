package edu.farmingdale.datastoredemo.data.local

import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable


/*
 * Set data for Android Release Emoji name
 * the list contain distinct sequential Emoji name
 */
object LocalEmojiData {
    val EmojiList = listOf(
       "😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "😊", "😇",
         "🙂", "🙃", "😉", "😌", "😍", "🥰", "😘", "😗", "😙", "😚",
            "😋", "😛", "😝", "😜", "🤪", "🤨", "🧐", "🤓", "😎", "🤩",
            "🥳", "😏", "😒", "😞", "😔", "😟", "😕", "🙁", "☹️", "😣",
            "😖", "😫", "😩", "🥺", "😢", "😭", "😤", "😠", "😡", "🤬",
            "😈", "👿", "💀", "☠️", "💩", "🤡", "👹", "👺", "👻", "👽",
            "👾", "🤖", "😺", "😸", "😹", "😻", "😼", "😽", "🙀", "😿",
            "😾", "🙈", "🙉", "🙊", "💋", "💌", "💘", "💝", "💖", "💗",
            "💓", "💞", "💕", "💟", "❣️", "👍", "👎", "✊", "👊", "✌️"
    )
}


