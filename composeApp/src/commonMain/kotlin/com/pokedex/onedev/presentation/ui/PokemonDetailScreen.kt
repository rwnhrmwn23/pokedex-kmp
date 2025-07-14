package com.pokedex.onedev.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pokedex.onedev.presentation.intent.PokemonDetailIntent
import com.pokedex.onedev.presentation.state.PokemonDetailState
import com.pokedex.onedev.presentation.ui.theme.PokeColors.BugGreen
import com.pokedex.onedev.presentation.ui.theme.PokeColors.DarkBlack
import com.pokedex.onedev.presentation.ui.theme.PokeColors.DragonBlue
import com.pokedex.onedev.presentation.ui.theme.PokeColors.ElectricYellow
import com.pokedex.onedev.presentation.ui.theme.PokeColors.FairyPink
import com.pokedex.onedev.presentation.ui.theme.PokeColors.FightingPink
import com.pokedex.onedev.presentation.ui.theme.PokeColors.FireOrange
import com.pokedex.onedev.presentation.ui.theme.PokeColors.FlyingBlue
import com.pokedex.onedev.presentation.ui.theme.PokeColors.GhostBlue
import com.pokedex.onedev.presentation.ui.theme.PokeColors.GrassGreen
import com.pokedex.onedev.presentation.ui.theme.PokeColors.GroundBrown
import com.pokedex.onedev.presentation.ui.theme.PokeColors.IceBlue
import com.pokedex.onedev.presentation.ui.theme.PokeColors.NormalGray
import com.pokedex.onedev.presentation.ui.theme.PokeColors.PoisonPurple
import com.pokedex.onedev.presentation.ui.theme.PokeColors.PsychicPink
import com.pokedex.onedev.presentation.ui.theme.PokeColors.RockGray
import com.pokedex.onedev.presentation.ui.theme.PokeColors.StatsGray
import com.pokedex.onedev.presentation.ui.theme.PokeColors.SteelGray
import com.pokedex.onedev.presentation.ui.theme.PokeColors.TextBlack
import com.pokedex.onedev.presentation.ui.theme.PokeColors.TextGrayLight
import com.pokedex.onedev.presentation.ui.theme.PokeColors.WaterBlue
import com.pokedex.onedev.presentation.utils.formatId
import com.pokedex.onedev.presentation.utils.formatImage
import com.pokedex.onedev.presentation.utils.formatName
import com.pokedex.onedev.presentation.utils.mapStatName
import com.pokedex.onedev.presentation.viewmodel.PokemonDetailViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import pokedex.composeapp.generated.resources.Res
import pokedex.composeapp.generated.resources.ic_arrow_back
import pokedex.composeapp.generated.resources.ic_straighten
import pokedex.composeapp.generated.resources.ic_weight
import pokedex.composeapp.generated.resources.type_bug
import pokedex.composeapp.generated.resources.type_dark
import pokedex.composeapp.generated.resources.type_dragon
import pokedex.composeapp.generated.resources.type_electric
import pokedex.composeapp.generated.resources.type_fairy
import pokedex.composeapp.generated.resources.type_fighting
import pokedex.composeapp.generated.resources.type_fire
import pokedex.composeapp.generated.resources.type_flying
import pokedex.composeapp.generated.resources.type_ghost
import pokedex.composeapp.generated.resources.type_grass
import pokedex.composeapp.generated.resources.type_ground
import pokedex.composeapp.generated.resources.type_ice
import pokedex.composeapp.generated.resources.type_normal
import pokedex.composeapp.generated.resources.type_poison
import pokedex.composeapp.generated.resources.type_psychic
import pokedex.composeapp.generated.resources.type_rock
import pokedex.composeapp.generated.resources.type_steel
import pokedex.composeapp.generated.resources.type_water

@Composable
fun PokemonDetailScreenRoute(
    pokemonName: String, viewModel: PokemonDetailViewModel, navController: NavHostController
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
                onBackClick = {
                    navController.popBackStack()
                },
                name = formatName(data.name),
                number = formatId(data.id),
                imageUrl = formatImage(data.id),
                types = data.types?.mapNotNull { it.name } ?: emptyList(),
                weight = "${data.weight?.div(10f)} kg",
                height = "${data.height?.div(10f)} m",
                moves = data.abilities?.mapNotNull { it.name } ?: emptyList(),
                stats = data.stats?.mapNotNull { stat ->
                    stat.name?.let { name ->
                        mapStatName(name) to (stat.baseStat ?: 0)
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
fun getImageDetailPokemon(type: String): Painter {
    return when (type) {
        "bug" -> painterResource(Res.drawable.type_bug)
        "dark" -> painterResource(Res.drawable.type_dark)
        "dragon" -> painterResource(Res.drawable.type_dragon)
        "electric" -> painterResource(Res.drawable.type_electric)
        "fairy" -> painterResource(Res.drawable.type_fairy)
        "fighting" -> painterResource(Res.drawable.type_fighting)
        "fire" -> painterResource(Res.drawable.type_fire)
        "flying" -> painterResource(Res.drawable.type_flying)
        "ghost" -> painterResource(Res.drawable.type_ghost)
        "grass" -> painterResource(Res.drawable.type_grass)
        "ground" -> painterResource(Res.drawable.type_ground)
        "ice" -> painterResource(Res.drawable.type_ice)
        "normal" -> painterResource(Res.drawable.type_normal)
        "poison" -> painterResource(Res.drawable.type_poison)
        "psychic" -> painterResource(Res.drawable.type_psychic)
        "rock" -> painterResource(Res.drawable.type_rock)
        "steel" -> painterResource(Res.drawable.type_steel)
        "water" -> painterResource(Res.drawable.type_water)
        else -> painterResource(Res.drawable.type_grass)
    }
}

@Composable
fun getColorTheme(type: String): Color {
    return when (type) {
        "bug" -> BugGreen
        "dark" -> DarkBlack
        "dragon" -> DragonBlue
        "electric" -> ElectricYellow
        "fairy" -> FairyPink
        "fighting" -> FightingPink
        "fire" -> FireOrange
        "flying" -> FlyingBlue
        "ghost" -> GhostBlue
        "grass" -> GrassGreen
        "ground" -> GroundBrown
        "ice" -> IceBlue
        "normal" -> NormalGray
        "poison" -> PoisonPurple
        "psychic" -> PsychicPink
        "rock" -> RockGray
        "steel" -> SteelGray
        "water" -> WaterBlue
        else -> GrassGreen
    }
}

@Composable
fun PokemonDetailScreen(
    onBackClick: () -> Unit,
    name: String,
    number: String,
    imageUrl: String,
    types: List<String>,
    weight: String,
    height: String,
    moves: List<String>,
    stats: Map<String, Int>?
) {
    val painterResource = asyncPainterResource(imageUrl)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getColorTheme(types[0]))
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Header
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onBackClick()
                        },
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = "Back Button"
                )

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            Text(
                text = number,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

        // White Card Detail
        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
        ) {
            Box(
                modifier = Modifier
                    .offset(y = (200).dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    // Types
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            types.forEach { type ->
                                Text(
                                    text = type.capitalize(LocaleList()),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .background(
                                            color = getColorTheme(type),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "About",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = getColorTheme(types[0])
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val infoHeight = 40.dp

                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            // Weight
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier.height(infoHeight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            modifier = Modifier.size(14.dp),
                                            painter = painterResource(Res.drawable.ic_weight),
                                            contentDescription = "Weight"
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            weight,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = TextBlack
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Weight", fontSize = 12.sp, color = TextGrayLight)
                            }

                            // Height
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier.height(infoHeight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            modifier = Modifier.size(14.dp),
                                            painter = painterResource(Res.drawable.ic_straighten),
                                            contentDescription = "Height"
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            height,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = TextBlack
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Height", fontSize = 12.sp, color = TextGrayLight)
                            }

                            // Moves
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier.height(infoHeight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        moves.forEach {
                                            Text(
                                                it,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = TextBlack
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Moves", fontSize = 12.sp, color = TextGrayLight)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Base Stats",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = getColorTheme(types[0])
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    stats?.forEach { (stat, value) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stat,
                                modifier = Modifier.width(50.dp),
                                color = getColorTheme(types[0]),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = value.toString().padStart(3, '0'),
                                modifier = Modifier.width(30.dp)
                            )
                            LinearProgressIndicator(
                                progress = { value / 100f },
                                modifier = Modifier.fillMaxWidth().height(6.dp),
                                color = getColorTheme(types[0]),
                                trackColor = StatsGray
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            // Background Type Pokemon
            Image(
                painter = getImageDetailPokemon(types[0]),
                contentDescription = "Type background",
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .size(220.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = (40).dp),
                contentScale = ContentScale.Fit
            )

            // Image Pokemon
            KamelImage(
                resource = { painterResource },
                contentDescription = "Pokemon",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 50.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}
