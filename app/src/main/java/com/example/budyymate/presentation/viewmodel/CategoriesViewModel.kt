package com.example.budyymate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budyymate.domain.model.Category
import com.example.budyymate.domain.usecase.GetCategoriesUseCase
import com.example.budyymate.domain.usecase.AddCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            try {
                val categories = getCategoriesUseCase()
                _state.update {
                    it.copy(
                        isLoading = false,
                        categories = categories
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Kategoriler yüklenirken hata oluştu"
                    )
                }
            }
        }
    }

    fun updateNewCategoryName(name: String) {
        _state.update { it.copy(newCategoryName = name) }
    }

    fun updateNewCategoryColor(color: String) {
        _state.update { it.copy(newCategoryColor = color) }
    }

    fun addCategory() {
        val currentState = _state.value
        val name = currentState.newCategoryName.trim()
        
        if (name.isEmpty()) {
            _state.update { it.copy(error = "Kategori adı boş olamaz") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isAddingCategory = true, error = null) }
            
            try {
                val newCategory = Category(
                    id = 0, // Room otomatik ID atayacak
                    name = name,
                    color = currentState.newCategoryColor
                )
                
                addCategoryUseCase(newCategory)
                
                // Kategorileri yeniden yükle
                loadCategories()
                
                // Form'u temizle
                _state.update {
                    it.copy(
                        isAddingCategory = false,
                        newCategoryName = "",
                        newCategoryColor = "#FF0000"
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isAddingCategory = false,
                        error = e.message ?: "Kategori eklenirken hata oluştu"
                    )
                }
            }
        }
    }

    fun refresh() {
        loadCategories()
    }
} 