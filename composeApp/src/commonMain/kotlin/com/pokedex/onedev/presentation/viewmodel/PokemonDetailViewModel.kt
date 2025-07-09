package com.pokedex.onedev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedev.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.pokedex.onedev.presentation.intent.PokemonDetailIntent
import com.pokedex.onedev.presentation.state.PokemonDetailState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Idle)
    val state: StateFlow<PokemonDetailState> = _state

    private val intentChannel = Channel<PokemonDetailIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    fun dispatchIntent(intent: PokemonDetailIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.receiveAsFlow().collect { intent ->
                when (intent) {
                    is PokemonDetailIntent.LoadDetail -> loadPokemonDetail(intent.name)
                }
            }
        }
    }

    private suspend fun loadPokemonDetail(name: String) {
        _state.value = PokemonDetailState.Loading
        try {
            val detail = getPokemonDetailUseCase(name)
            _state.value = PokemonDetailState.Success(detail)
        } catch (e: Exception) {
            _state.value = PokemonDetailState.Error(e.message ?: "Unknown error")
        }
    }
}
