package com.example.konveyor.items.article

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.avito.konveyor.blueprint.Item

class Article(override val id: Long, val title: String, @StringRes val bodyPreview: Int, @DrawableRes val image: Int) : Item
