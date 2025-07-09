package com.pokedex.onedev.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pokedex.onedev.presentation.intent.PokemonDetailIntent
import com.pokedex.onedev.presentation.state.PokemonDetailState
import com.pokedex.onedev.presentation.viewmodel.PokemonDetailViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun PokemonDetailScreenRoute(
    pokemonName: String,
    viewModel: PokemonDetailViewModel
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatchIntent(PokemonDetailIntent.LoadDetail(pokemonName))
    }

    when (val currentState = state) {
        is PokemonDetailState.Idle, is PokemonDetailState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is PokemonDetailState.Success -> {
            val data = currentState.data
            PokemonDetailScreen(
                name = data.name ?: "Unknown",
                number = "#${data.id}",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${data.id}.png",
                types = data.types?.mapNotNull { it.name } ?: emptyList(),
                weight = "${data.weight?.div(10f)} kg",
                height = "${data.height?.div(10f)} m",
                moves = data.abilities?.mapNotNull { it.name } ?: emptyList(),
                description = "No description available",
                stats = data.stats?.mapNotNull { stat ->
                        stat.name?.let { name ->
                            name to (stat.baseStat ?: 0)
                        }
                    }?.toMap()
            )
        }

        is PokemonDetailState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = currentState.message, color = Color.Red)
            }
        }
    }
}

@Composable
fun PokemonDetailScreen(
    name: String,
    number: String,
    imageUrl: String,
    types: List<String>,
    weight: String,
    height: String,
    moves: List<String>,
    description: String,
    stats: Map<String, Int>?
) {
    val painterResource = asyncPainterResource(imageUrl)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF78C850))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = number,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

        // Pokémon Image
        KamelImage(
            resource = { painterResource },
            contentDescription = name,
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        // White Card Detail
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Types
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    types.forEach { type ->
                        Text(
                            text = type,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    color = when (type.lowercase()) {
                                        "grass" -> Color(0xFF78C850)
                                        "poison" -> Color(0xFFA040A0)
                                        else -> Color.Gray
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "About",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF78C850)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Weight", color = Color.Gray)
                        Text(weight, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Height", color = Color.Gray)
                        Text(height, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Moves", color = Color.Gray)
                        moves.forEach {
                            Text(it, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = description, fontSize = 14.sp, lineHeight = 20.sp)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Base Stats",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF78C850)
                )

                Spacer(modifier = Modifier.height(16.dp))

                stats?.forEach { (stat, value) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stat,
                            modifier = Modifier.width(50.dp),
                            color = Color(0xFF78C850),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = value.toString().padStart(3, '0'),
                            modifier = Modifier.width(30.dp)
                        )
                        LinearProgressIndicator(
                            progress = value / 100f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),
                            color = Color(0xFF78C850),
                            trackColor = Color(0xFFB0E5A4)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
