package com.futuristic.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.futuristic.app.data.local.AppDatabase
import com.futuristic.app.data.local.UserEntity
import com.futuristic.app.data.remote.DashboardItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// State Management
sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    val userFlow = userDao.getUser()

    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val loginState: StateFlow<UiState<Boolean>> = _loginState.asStateFlow()

    private val _dashboardState = MutableStateFlow<UiState<List<DashboardItem>>>(UiState.Idle)
    val dashboardState: StateFlow<UiState<List<DashboardItem>>> = _dashboardState.asStateFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            delay(1500) // Simulate network delay
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                val user = UserEntity(name = "Commander", email = email, token = "neon-token-123")
                userDao.saveUser(user)
                _loginState.value = UiState.Success(true)
            } else {
                _loginState.value = UiState.Error("Email dan password tidak boleh kosong!")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userDao.clearSession()
            _loginState.value = UiState.Idle
            _dashboardState.value = UiState.Idle
        }
    }

    fun fetchDashboardData() {
        viewModelScope.launch {
            _dashboardState.value = UiState.Loading
            delay(1000) // Simulate network
            try {
                // Mocking API Response untuk mencegah crash jika endpoint API palsu diakses
                val fakeData = listOf(
                    DashboardItem(1, "Sistem Inti", "Online"),
                    DashboardItem(2, "Baterai", "98%"),
                    DashboardItem(3, "Suhu Reaktor", "45°C"),
                    DashboardItem(4, "Kecepatan Warp", "Mach 5")
                )
                _dashboardState.value = UiState.Success(fakeData)
            } catch (e: Exception) {
                _dashboardState.value = UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
            }
        }
    }
}
