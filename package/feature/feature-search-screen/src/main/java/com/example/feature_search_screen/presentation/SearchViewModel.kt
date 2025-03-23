package com.example.feature_search_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_search_screen.domain.entity.SearchEntity
import com.example.feature_search_screen.domain.usecase.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchItemsUseCase: SearchItemsUseCase
) : ViewModel() {
    private val searchIntent = MutableSharedFlow<SearchIntent>()

    private val _searchScreenState = MutableStateFlow(SearchState())
    val searchScreenState: StateFlow<SearchState> = _searchScreenState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            searchIntent.collectLatest { event ->
                when (event) {
                    is SearchIntent.SearchItems -> searchItems(event.q)
                    SearchIntent.InitialState -> initial()
                }
            }
        }
    }

    private fun initial(){
        _searchScreenState.value = _searchScreenState.value.copy(status = SearchStatus.Idle, data = null, message = "")
    }

    private fun searchItems(q: String) {
        _searchScreenState.value = _searchScreenState.value.copy(status = SearchStatus.Loading)
        viewModelScope.launch {
            searchItemsUseCase.execute(q).onSuccess {
                if (it is SearchEntity) {
                    _searchScreenState.value = _searchScreenState.value.copy(
                        status = SearchStatus.Success,
                        data = it
                    )
                } else {
                    _searchScreenState.value = _searchScreenState.value.copy(
                        status = SearchStatus.Failed,
                        message = "No data"
                    )
                }
            }.onFailure {
                _searchScreenState.value = _searchScreenState.value.copy(
                    status = SearchStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }

    fun sendIntent(intent: SearchIntent) {
        viewModelScope.launch {
            searchIntent.emit(intent)
        }
    }
}