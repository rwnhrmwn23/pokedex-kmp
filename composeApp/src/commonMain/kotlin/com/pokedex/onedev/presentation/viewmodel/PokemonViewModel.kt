package com.pokedex.onedev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedev.pokedex.domain.repository.PokemonRepository
import com.pokedex.onedev.presentation.intent.PokemonIntent
import com.pokedex.onedev.presentation.state.PokemonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonState>(PokemonState.Idle)
    val state: StateFlow<PokemonState> = _state.asStateFlow()

    fun dispatch(intent: PokemonIntent) {
        when (intent) {
            is PokemonIntent.LoadPokemonList -> getList()
            is PokemonIntent.LoadPokemonDetail -> getDetail(intent.id)
        }
    }

    private fun getList() {
        viewModelScope.launch {
            _state.value = PokemonState.Loading
            try {
                val list = repository.getPokemonList(offset = 0, limit = 20)
                _state.value = PokemonState.ListLoaded(list)
            } catch (e: Exception) {
                _state.value = PokemonState.Error(e.message ?: "Unknown Error!")
            }
        }
    }

    private fun getDetail(id: Int) {
        viewModelScope.launch {
            _state.value = PokemonState.Loading
            try {
                val detail = repository.getPokemonDetail(id)
                _state.value = PokemonState.DetailLoaded(detail)
            } catch (e: Exception) {
                _state.value = PokemonState.Error(e.message ?: "Unknown Error!")
            }
        }
    }
}