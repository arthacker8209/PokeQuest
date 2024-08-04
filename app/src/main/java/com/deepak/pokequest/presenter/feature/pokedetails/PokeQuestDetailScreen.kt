import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deepak.pokequest.presenter.feature.pokedetails.PokeQuestDetailsViewModel

@Composable
fun PokeQuestDetailsScreen(
    navController: NavController,
    dominantColor: Int,
    pokemonId: String
) {
    val viewModel: PokeQuestDetailsViewModel = hiltViewModel()

    LaunchedEffect(pokemonId) {
        viewModel.getPokemonDetails(pokemonId)
    }

    val pokemon by viewModel.pokemon
    val pokemonStats by viewModel.pokemonStats

    pokemon?.let { pokemonEntity ->

        Surface(
            color = Color(dominantColor),
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(pokemonEntity.largeImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Pokemon Name
                    Text(
                        text = pokemonEntity.name,
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        DetailSection("Types", pokemonEntity.types.joinToString(", "), Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        DetailSection("Sub Types", pokemonEntity.subTypes.joinToString(", "), Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // HP, Level, Damage with Progress Bars
                    pokemonStats?.let { stats ->
                        StatSection("Level", stats.level, maxValue = 100, color = Color.Green)
                        StatSection("HP", stats.hp, maxValue = 200, color = Color.Red)
                        stats.attacks.forEach { (name, damage) ->
                            StatSection("Attack: $name", damage, maxValue = 100, color = Color.Blue)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Weaknesses, Abilities, Resistances Sections
                    DetailsSection(title = "Weaknesses", items = pokemonEntity.weaknesses.map { "${it.type} - Value: ${it.value}" })
                    DetailsSection(title = "Abilities", items = pokemonEntity.abilities.map { "${it.name} - Type: ${it.type} - ${it.text}" })
                    DetailsSection(title = "Resistances", items = pokemonEntity.resistances.map { "${it.type} - Value: ${it.value}" })
                }
            }
        }
    } ?: run {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun StatSection(title: String, value: Int, maxValue: Int, color: Color) {

    var animatedProgress by remember { mutableFloatStateOf(0f) }
    val progress by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(value) {
        animatedProgress = value / maxValue.toFloat()
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = progress,
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$value / $maxValue",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DetailSection(title: String, details: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = details,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DetailsSection(title: String, items: List<String>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        items.forEach { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
