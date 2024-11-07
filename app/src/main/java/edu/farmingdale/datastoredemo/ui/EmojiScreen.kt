package edu.farmingdale.datastoredemo.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.farmingdale.datastoredemo.R
import edu.farmingdale.datastoredemo.data.local.LocalEmojiData


/*
 * Screen level composable
 */
@Composable
fun EmojiReleaseApp(
    emojiViewModel: EmojiScreenViewModel = viewModel(
        factory = EmojiScreenViewModel.Factory
    )
) {
    // Observe dark mode
    val isDarkMode = emojiViewModel.isDarkMode.value

    // Apply dark or light theme dynamically
    MaterialTheme(
        colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        EmojiScreen(
            uiState = emojiViewModel.uiState.collectAsState().value,
            selectLayout = emojiViewModel::selectLayout,
            toggleTheme = emojiViewModel::toggleTheme,
            isDarkMode = isDarkMode
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmojiScreen(
    uiState: EmojiReleaseUiState,
    selectLayout: (Boolean) -> Unit,
    toggleTheme: () -> Unit,
    isDarkMode: Boolean
) {
    val context = LocalContext.current
    val isLinearLayout = uiState.isLinearLayout

    // Define the onEmojiClick lambda function to show a toast
    val onEmojiClick: (String) -> Unit = { emoji ->
        Toast.makeText(context, "You clicked on: $emoji", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emoji Release") },
                actions = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = {
                            toggleTheme()  // Toggle the theme
                            Toast.makeText(context, "Theme changed", Toast.LENGTH_SHORT).show()
                        }
                    )
                    IconButton(
                        onClick = {
                            selectLayout(!isLinearLayout)
                        }
                    ) {
                        Icon(
                            painter = painterResource(uiState.toggleIcon),
                            contentDescription = stringResource(uiState.toggleContentDescription),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary
                )
            )
        }
    ) { innerPadding ->
        val modifier = Modifier
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium),
            )
        if (isLinearLayout) {
            // Pass the onEmojiClick lambda to the layout
            EmojiReleaseLinearLayout(
                modifier = modifier.fillMaxWidth(),
                contentPadding = innerPadding,
                onEmojiClick = onEmojiClick  // Pass the lambda here
            )
        } else {
            // Pass the onEmojiClick lambda to the layout
            EmojiReleaseGridLayout(
                modifier = modifier,
                contentPadding = innerPadding,
                onEmojiClick = onEmojiClick  // Pass the lambda here
            )
        }
    }
}

@Composable
fun EmojiReleaseLinearLayout(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onEmojiClick: (String) -> Unit // Add the onEmojiClick parameter
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
    ) {
        items(
            items = LocalEmojiData.EmojiList,
            key = { e -> e }
        ) { e ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = e, fontSize = 50.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .clickable {
                            // Here we use the onEmojiClick function when an emoji is clicked
                            onEmojiClick(e)  // Pass the emoji to onEmojiClick
                        },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun EmojiReleaseGridLayout(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onEmojiClick: (String) -> Unit // Add the onEmojiClick parameter
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(
            items = LocalEmojiData.EmojiList,
            key = { e -> e }
        ) { e ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(110.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = e, fontSize = 50.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(dimensionResource(R.dimen.padding_small))
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            // Here we use the onEmojiClick function when an emoji is clicked
                            onEmojiClick(e)  // Pass the emoji to onEmojiClick
                        },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}