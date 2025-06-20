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

sealed interface CharacterUiState{
    object Loading: CharacterUiState

    data class Success (val characters : List<Character> ): CharacterUiState

    object Error: CharacterUiState
}

class CharacterViewModel: ViewModel(){

    private var _uiState : MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Loading)
    val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    init{
        getCharacter()
    }

    private fun getCharacter(){
        viewModelScope.launch {
            try {
                _uiState.value = CharacterUiState.Success(
                    OpenHollowKnightAPI.retrofitService.getCharacter()
                )
                Log.d("CharmViewModel", "Charms carregados com sucesso")
            } catch(e: IOException) {
                Log.e("CharmViewModel", "Erro de IO: ${e.message}", e)
                _uiState.value = CharacterUiState.Error
            } catch (e: HttpException) {
                Log.e("CharmViewModel", "Erro HTTP: ${e.message}", e)
                _uiState.value = CharacterUiState.Error
            } catch (e: Exception) {
                Log.e("CharmViewModel", "Erro inesperado: ${e.message}", e)
                _uiState.value = CharacterUiState.Error
            }
        }
    }
}