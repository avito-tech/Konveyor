package com.example.konveyor.items.article

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.example.konveyor.R

class ArticleBlueprint(override val presenter: ItemPresenter<ArticleView, Article>) : ItemBlueprint<ArticleView, Article> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            R.layout.view_article,
            { _, view -> ArticleViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is Article
}
