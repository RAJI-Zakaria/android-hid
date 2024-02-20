package com.example.redeemers_faz_com.ui.screens

// Import statements
import android.view.KeyEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.NewLabel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.redeemers_faz_com.Shortcut
import com.example.redeemers_faz_com.room.AppDatabase
import com.example.redeemers_faz_com.room.Key
import com.example.redeemers_faz_com.util.DialogNewKeys
import compose.icons.AllIcons
import compose.icons.FeatherIcons
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
// Data class representing a tile
data class Tile(
    var name: String,
    var command: String,
    var icon: String
)

// Composable function for displaying a list of keyboard shortcut cards
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KeysList(
    selectedBoardId: Int=0,
    onPressCard: (shortcut: Shortcut, releaseModifiers: Boolean) -> Boolean,
    database: AppDatabase
) {
    // Mutable state for managing the list of tiles
    val keys by remember {
        mutableStateOf(mutableStateListOf<Tile?>(*Array<Tile>(12) { Tile(name="", command="", icon="") }))
    }

    var openDialogNewKey = remember { mutableStateOf(false) }
    var newKey = remember { mutableIntStateOf(-1) }
    var allIcons = remember { FeatherIcons.AllIcons }

    fun getIcon(icon : String): ImageVector {
        allIcons.forEach {
            if(it.name == icon) {
                return it
            }
        }
        return Icons.Filled.Abc
    }

    fun addKey(key : Int, name : String, command : String, icon : String) {
        keys[key] = Tile(name, command, icon)
        openDialogNewKey.value = false

        // Insert the new key into the database
        GlobalScope.launch {
            // Insert the new key asynchronously
            database.keysDao().insertKeys(Key(0, 0, name, command, icon))
        }
    }
    Column {
        Text(text = "selectedBoardId : $selectedBoardId")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
            items(keys) { key ->
            // Each item wrapped in a Column
                Row {

                if (key == null || key.icon == "" && key.name == "" && key.command == "") {
                    Box(modifier = Modifier.padding(15.dp)) {
                        Button(
                            onClick = {
                                newKey.value = keys.indexOf(key)
                                openDialogNewKey.value = true
                            },
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Center)
                        ) {
                            Icon(Icons.Filled.NewLabel, "Floating action button.")
                            Text("add")
                        }
                    }
                } else {
                    Box(modifier = Modifier.padding(10.dp)) {
                        Card(
                            onClick = {
                                onPressCard(
                                    Shortcut(KeyEvent.keyCodeFromString(key.command)),
                                    true
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            elevation = 8.dp
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = getIcon(key.icon),
                                    contentDescription = "Icon",
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Column {
                                    if (key.name == "") {
                                        Text(
                                            text = "Without Name",
                                            fontStyle = FontStyle.Italic
                                        )
                                    } else {
                                        Text(text = key.name)
                                    }
                                    Text(text = key.command)
                                }
                            }
                        }
                    }
                }
                }
            }
        }
    }

    // Display the dialog for adding new keys
    when {
        openDialogNewKey.value -> {
            DialogNewKeys(
                newKeys = newKey.value,
                icons = allIcons,
                getIcon = { icon -> getIcon(icon) },
                onDismissRequest = { openDialogNewKey.value = false },
                onConfirmation = { key, name, command, icon -> addKey(key, name, command, icon) }
            )
        }
    }
}

