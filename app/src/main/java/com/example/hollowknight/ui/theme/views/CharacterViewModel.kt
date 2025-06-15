package com.example.hollowknight.ui.theme.views

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hollowknight.data.Character
import com.example.hollowknight.network.OpenHollowKnightAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface CharmUiState{
    object Loading: CharmUiState

    data class Sucess (val charm : List<Character> ): CharmUiState

    object Error: CharmUiState
}

class CharmViewModel: ViewModel(){

    private var _uiState : MutableStateFlow<CharmUiState> = MutableStateFlow(CharmUiState.Loading)
    val uiState: StateFlow<CharmUiState> = _uiState.asStateFlow()

    init{
        getCharms()
    }

    private fun getCharms(){
        viewModelScope.launch {
            try {
                _uiState.value = CharmUiState.Sucess(
                    OpenHollowKnightAPI.retrofitService.getCharms()
                )
                Log.d("CharmViewModel", "Charms carregados com sucesso")
            } catch(e: IOException) {
                Log.e("CharmViewModel", "Erro de IO: ${e.message}", e)
                _uiState.value = CharmUiState.Error
            } catch (e: HttpException) {
                Log.e("CharmViewModel", "Erro HTTP: ${e.message}", e)
                _uiState.value = CharmUiState.Error
            } catch (e: Exception) {
                Log.e("CharmViewModel", "Erro inesperado: ${e.message}", e)
                _uiState.value = CharmUiState.Error
            }
        }
    }
}