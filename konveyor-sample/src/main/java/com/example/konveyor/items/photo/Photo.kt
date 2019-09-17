package com.example.konveyor.items.photo

import androidx.annotation.DrawableRes
import com.avito.konveyor.blueprint.Item

class Photo(override val id: Long, val title: String, @DrawableRes val image: Int) : Item
