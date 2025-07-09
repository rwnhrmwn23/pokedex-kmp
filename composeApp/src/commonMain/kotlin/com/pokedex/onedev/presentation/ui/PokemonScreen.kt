package com.pokedex.onedev.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onedev.pokedex.domain.model.Pokemon
import com.pokedex.onedev.presentation.intent.PokemonIntent
import com.pokedex.onedev.presentation.state.PokemonListUiState
import com.pokedex.onedev.presentation.viewmodel.PokemonViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import pokedex.composeapp.generated.resources.Res
import pokedex.composeapp.generated.resources.ic_hashtag
import pokedex.composeapp.generated.resources.ic_pokeboll
import pokedex.composeapp.generated.resources.ic_search

@Composable
fun PokemonListScreen(
    viewModel: PokemonViewModel,
    onClickItem: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyGridState()

    LaunchedEffect(viewModel) {
        if (viewModel.state.value.pokemonList.isEmpty()) {
            viewModel.dispatch(PokemonIntent.LoadInitial)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val totalItemsCount = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                val isAtEnd = lastVisibleItemIndex >= totalItemsCount - 3
                if (isAtEnd && !state.isLoading && !state.isLoadingMore) {
                    viewModel.dispatch(PokemonIntent.LoadMorePokemon)
                }
            }
    }

    PokemonScreen(
        state = state,
        listState = listState,
        onClickItem = onClickItem,
        onSearch = { query -> viewModel.dispatch(PokemonIntent.Search(query)) }
    )
}


@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onClickHashtag: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_search),
                    contentDescription = "Search",
                    modifier = Modifier.size(16.dp)
                )
            }

            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        if (text.isEmpty()) {
                            Text("Search", color = Color.Gray, fontSize = 14.sp)
                        }
                        innerTextField()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFFFFF))
                .clickable { onClickHashtag() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_hashtag),
                contentDescription = "Hashtag",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun PokemonScreen(
    state: PokemonListUiState,
    listState: LazyGridState,
    onClickItem: (String) -> Unit = {},
    onSearch: (String) -> Unit
) {
    var search by remember { mutableStateOf(state.searchQuery) }
    val pokemons = state.filteredList.ifEmpty { state.pokemonList }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDC0A2D))
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Res.drawable.ic_pokeboll),
                contentDescription = "Pokeball"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Pokédex",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SearchBar(
            text = search,
            onTextChange = {
                search = it
                onSearch(it)
            },
            onClickHashtag = { }
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.error}", color = Color.Red)
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFFFFF)),
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        state = listState,
                        contentPadding = PaddingValues(4.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(pokemons) { pokemon ->
                            PokemonCard(
                                pokemon = pokemon,
                                onClick = { pokemon.name?.let { onClickItem(it) } }
                            )
                        }

                        if (state.isLoadingMore) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Spacer(modifier = Modifier.height(64.dp))
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color(0xFFDC0A2D)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon, onClick: () -> Unit) {
    val painterResource = asyncPainterResource(pokemon.imageUrl.orEmpty())

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE2E2E2))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "#${pokemon.id?.toString()?.padStart(3, '0') ?: "000"}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }
                KamelImage(
                    resource = { painterResource },
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = pokemon.name ?: "-",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}




