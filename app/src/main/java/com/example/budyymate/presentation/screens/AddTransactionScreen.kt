package com.example.budyymate.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budyymate.presentation.viewmodel.AddTransactionViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showCategoryDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Yeni İşlem Ekle",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // İşlem Türü Seçimi
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (state.isExpense) 
                    MaterialTheme.colorScheme.errorContainer 
                else 
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.toggleTransactionType() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.isExpense) 
                            MaterialTheme.colorScheme.error 
                        else 
                            MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(if (state.isExpense) "Gider" else "Gelir")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Başlık
        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.updateTitle(it) },
            label = { Text("Başlık") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Miktar
        OutlinedTextField(
            value = state.amount,
            onValueChange = { viewModel.updateAmount(it) },
            label = { Text("Miktar (₺)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kategori Seçimi
        OutlinedTextField(
            value = state.categories.find { it.id == state.selectedCategoryId }?.name ?: "",
            onValueChange = { },
            label = { Text("Kategori") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showCategoryDialog = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Kategori Seç")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Açıklama
        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("Açıklama (Opsiyonel)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Kaydet Butonu
        Button(
            onClick = { viewModel.saveTransaction() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isSaving && state.title.isNotEmpty() && 
                     state.amount.isNotEmpty() && state.selectedCategoryId != null
        ) {
            if (state.isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (state.isSaving) "Kaydediliyor..." else "Kaydet")
        }

        // Hata Mesajı
        state.error?.let { error ->
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        // Başarı Mesajı
        if (state.isSuccess) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "İşlem başarıyla kaydedildi!",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }

    // Kategori Seçim Dialog'u
    if (showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showCategoryDialog = false },
            title = { Text("Kategori Seçin") },
            text = {
                LazyColumn {
                    items(state.categories) { category ->
                        ListItem(
                            headlineContent = { Text(category.name) },
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(
                                            color = try {
                                                Color(android.graphics.Color.parseColor(category.color))
                                            } catch (e: Exception) {
                                                MaterialTheme.colorScheme.primary
                                            },
                                            shape = MaterialTheme.shapes.small
                                        )
                                )
                            },
                            modifier = Modifier.clickable {
                                viewModel.updateSelectedCategory(category.id)
                                showCategoryDialog = false
                            }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCategoryDialog = false }) {
                    Text("İptal")
                }
            }
        )
    }
}