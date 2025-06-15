package com.example.hollowknight.ui.theme.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hollowknight.data.Character
import com.example.hollowknight.network.BASE_URL
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.hollowknight.R
import androidx.compose.material3.Text


@Composable
fun CharmScreen(
    charmViewModel: CharmViewModel = viewModel()
){
    val uiState by charmViewModel.uiState.collectAsState()
    when(uiState){
        is CharmUiState.Loading -> LoadingScreen()
        is CharmUiState.Sucess -> CharmList((uiState as CharmUiState.Sucess).charm)
        is CharmUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Carregando...")
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Erro ao carregar os charms")
    }
}
@Composable
fun CharmList(
    charm: List<Character>
){
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        columns = GridCells.Fixed(2)
    ) {
        items(charm){charm ->
            CharmEntry(charm = charm)
        }
    }

}
@Composable
fun CharmEntry(
    charm: Character
){
    Card(
        modifier = Modifier.padding(6.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Box(){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(BASE_URL.trimEnd('/') + charm.image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = charm.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape)
        )
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = charm.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White, fontWeight = FontWeight.Bold
            )
        )
        }
    }
}