package com.example.hollowknight.ui.theme.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hollowknight.R
import com.example.hollowknight.data.Character
import com.example.hollowknight.network.BASE_URL
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    character: Character,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF0F0F1A),
        topBar = {
            TopAppBar(
                title = { Text(character.name ?: "Detalhes", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1C1C2E)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF0F0F1A))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BASE_URL.trimEnd('/') + character.detailImage)
                    .crossfade(true)
                    .build(),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = character.name ?: "Sem nome",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = character.description ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            CharacterDetailItem("🗺 Localização", character.location)
            CharacterDetailItem("🎯 Aquisição", character.acquisition)

            if (!character.abilities.isNullOrEmpty()) {
                Text(
                    text = "🧠 Habilidades",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF5C90E8),
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
                character.abilities.forEach {
                    Text("• $it", color = Color.White)
                }
            }

            if (!character.drops.isNullOrEmpty()) {
                Text(
                    text = "🎁 Itens Soltados",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF5C90E8),
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
                character.drops.forEach {
                    Text("• $it", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CharacterDetailItem(title: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFF5C90E8))
            )
            Text(text = value, style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }
    }
}
