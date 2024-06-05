package com.example.pescamines.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockUserPreferencesRepository(context: Context) : UserPreferencesRepository(context) {
    private val _userNameFlow = MutableStateFlow("Aitana GOATmatí")
    private val _gridSizeFlow = MutableStateFlow(8)
    private val _timerEnabledFlow = MutableStateFlow(true)
    private val _bombPercentageFlow = MutableStateFlow(10)

    override val userNameFlow: Flow<String> = _userNameFlow
    override val gridSizeFlow: Flow<Int> = _gridSizeFlow
    override val timerEnabledFlow: Flow<Boolean> = _timerEnabledFlow
    override val bombPercentageFlow: Flow<Int> = _bombPercentageFlow
}
