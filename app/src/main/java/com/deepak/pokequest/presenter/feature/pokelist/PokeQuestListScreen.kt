package com.deepak.pokequest.presenter.feature.pokelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deepak.pokequest.R
import com.deepak.pokequest.presenter.navigation.NavigationItem

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onSearch = { viewModel.searchPokemonList(it) },
                onSortOptionSelected = { sortOption ->
                    viewModel.sortPokemonList(sortOption)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
    onSortOptionSelected: (SortOption) -> Unit = {}
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        // Search Bar
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 2.dp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp)
                .fillMaxWidth(0.88f)
        ) {
            Box {
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onSearch(it.text)
                    },
                    placeholder = {
                        Text(text = hint)
                    },
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Sort Icon
        Box(modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)) {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "Sort"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                DropdownMenuItem(
                    text = { Text("Sort by HP") },
                    onClick = {
                        onSortOptionSelected(SortOption.HP)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Sort by Level") },
                    onClick = {
                        onSortOptionSelected(SortOption.LEVEL)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        itemsIndexed(pokemonList) { index, entry ->
            if (index >= pokemonList.size - 1 && !endReached && !isLoading && !isSearching) {
                LaunchedEffect(Unit) {
                    viewModel.loadPokemonPaginated()
                }
            }
            PokedexEntry(entry = entry, navController = navController)
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
        if (loadError.isNotEmpty()) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = loadError, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}


@Composable
fun PokedexEntry(
    entry: PokeQuestListEntity,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    fun isDarkColor(color: Color): Boolean {
        val luminance = (0.2126 * color.red + 0.7152 * color.green + 0.0722 * color.blue)
        return luminance < 0.5
    }

    val textColor = if (isDarkColor(dominantColor)) Color.White else Color.Black

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(dominantColor)
            .clickable {
                navController.navigate(
                    "${NavigationItem.Details.route}/${dominantColor.toArgb()}/${entry.id}"
                )
            }
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(entry.smallImage)
                    .crossfade(true)
                    .build(),
                contentDescription = entry.name,
                modifier = Modifier
                    .height(120.dp)
                    .width(80.dp),
                onSuccess = { result ->
                    viewModel.calcDominantColor(result.result.drawable) { color ->
                        dominantColor = color
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = entry.name ?: "",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    color = textColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                entry.types?.let {
                    Text(
                        text = "Types: ${it.joinToString(", ")}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        color = textColor, // Apply text color
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                entry.level?.let {
                    Text(
                        text = "Level: $it",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        color = textColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                entry.hp?.let {
                    Text(
                        text = "HP: $it",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        color = textColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


