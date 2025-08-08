package com.example.budyymate.data.local.entity

import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.model.Category

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    title = title,
    categoryId = categoryId,
    categoryName = categoryName,
    amount = amount,
    description = description,
    date = date
)

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    categoryName = categoryName,
    amount = amount,
    description = description,
    date = date
)

fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name,
    color = color,
    iconRes = iconRes
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    color = color,
    iconRes = iconRes
)