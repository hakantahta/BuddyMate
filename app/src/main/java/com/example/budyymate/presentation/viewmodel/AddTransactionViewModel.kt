package com.example.budyymate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.usecase.GetCategoriesUseCase
import com.example.budyymate.domain.usecase.AddTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionState())
    val state: StateFlow<AddTransactionState> = _state.asStateFlow()

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

    fun updateTitle(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun updateAmount(amount: String) {
        _state.update { it.copy(amount = amount) }
    }

    fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun updateSelectedCategory(categoryId: Int?) {
        _state.update { it.copy(selectedCategoryId = categoryId) }
    }

    fun toggleTransactionType() {
        _state.update { it.copy(isExpense = !it.isExpense) }
    }

    fun saveTransaction() {
        val currentState = _state.value
        
        // Validasyon
        if (currentState.title.trim().isEmpty()) {
            _state.update { it.copy(error = "Başlık boş olamaz") }
            return
        }
        
        if (currentState.amount.trim().isEmpty()) {
            _state.update { it.copy(error = "Miktar boş olamaz") }
            return
        }
        
        val amount = try {
            currentState.amount.toDouble()
        } catch (e: NumberFormatException) {
            _state.update { it.copy(error = "Geçerli bir miktar giriniz") }
            return
        }
        
        if (amount <= 0) {
            _state.update { it.copy(error = "Miktar 0'dan büyük olmalıdır") }
            return
        }
        
        if (currentState.selectedCategoryId == null) {
            _state.update { it.copy(error = "Kategori seçiniz") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null) }
            
            try {
                val finalAmount = if (currentState.isExpense) -amount else amount
                val selectedCategory = currentState.categories.find { it.id == currentState.selectedCategoryId }
                
                val transaction = Transaction(
                    id = 0, // Room otomatik ID atayacak
                    title = currentState.title.trim(),
                    categoryId = currentState.selectedCategoryId,
                    categoryName = selectedCategory?.name ?: "",
                    amount = finalAmount,
                    description = currentState.description.trim().takeIf { it.isNotEmpty() },
                    date = System.currentTimeMillis()
                )
                
                addTransactionUseCase(transaction)
                
                _state.update {
                    it.copy(
                        isSaving = false,
                        isSuccess = true,
                        title = "",
                        amount = "",
                        description = "",
                        selectedCategoryId = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        error = e.message ?: "İşlem kaydedilirken hata oluştu"
                    )
                }
            }
        }
    }

    fun clearSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
} 