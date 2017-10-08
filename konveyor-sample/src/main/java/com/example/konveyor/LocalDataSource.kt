package com.example.konveyor

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.data_source.DataSource
import com.example.konveyor.items.article.Article
import com.example.konveyor.items.photo.Photo

class LocalDataSource : DataSource<Item> {
    private val items = listOf(
            Article(1, "Northern Lights", R.string.article_body_preview, R.drawable.article1),
            Photo(1, "Full Moon", R.drawable.photo1),
            Photo(2, "The Starry Sky", R.drawable.photo2),
            Article(2, "An Ancient Discovery", R.string.article_body_preview, R.drawable.article2),
            Photo(3, "Racing Through the Sand", R.drawable.photo3),
            Article(3, "An Increase in Nightlife", R.string.article_body_preview, R.drawable.article3),
            Article(4, "The Effects of a Day at the Beach", R.string.article_body_preview, R.drawable.article4),
            Photo(4, "A Normal Day", R.drawable.photo4)
    )

    override fun isEmpty() = items.isEmpty()

    override fun getCount() = items.size

    override fun getItem(position: Int) = items[position]
}