package com.pdfa.pdfa_app.data.model

data class CookbookSelectionState(
    val cookbook: Cookbook,
    val isSelected: Boolean,
    val wasAlreadyPresent: Boolean
)