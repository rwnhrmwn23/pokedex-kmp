package com.pokedex.onedev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedev.pokedex.domain.repository.PokemonRepository
import com.pokedex.onedev.presentation.intent.PokemonIntent
import com.pokedex.onedev.presentation.state.PokemonListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListUiState())
    val state: StateFlow<PokemonListUiState> = _state.asStateFlow()

    private var currentOffset = 0
    private val pageSize = 20
    private var isFetching = false

    fun dispatch(intent: PokemonIntent) {
        when (intent) {
            is PokemonIntent.LoadInitial -> loadInitial()
            is PokemonIntent.LoadMorePokemon -> loadMorePokemon()
            is PokemonIntent.Search -> searchPokemon(intent.query)
        }
    }

    private fun loadInitial() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val list = repository.getPokemonList(currentOffset, pageSize)
                _state.update {
                    it.copy(
                        pokemonList = list,
                        isLoading = false,
                        error = null
                    )
                }
                currentOffset += pageSize
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun loadMorePokemon() {
        if (isFetching) return
        isFetching = true
        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            try {
                val list = repository.getPokemonList(currentOffset, pageSize)
                _state.update { pokemon ->
                    pokemon.copy(
                        pokemonList = (pokemon.pokemonList + list).distinctBy { it.name },
                        isLoadingMore = false
                    )
                }
                currentOffset += pageSize
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingMore = false) }
            } finally {
                isFetching = false
            }
        }
    }

    private fun searchPokemon(query: String) {
        viewModelScope.launch {
            _state.update {
                val filtered = if (query.isBlank()) {
                    it.pokemonList
                } else {
                    it.pokemonList.filter { pokemon ->
                        pokemon.name?.contains(query, ignoreCase = true) == true
                    }
                }
                it.copy(
                    searchQuery = query,
                    filteredList = filtered
                )
            }
        }
    }
}