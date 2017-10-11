package com.example.konveyor.items.article

import com.avito.konveyor.blueprint.ItemPresenter

class ArticlePresenter : ItemPresenter<ArticleView, Article> {
    override fun bindView(view: ArticleView, item: Article, position: Int) {
        view.setImage(item.image)
        view.setTitle(item.title)
        view.setBodyPreview(item.bodyPreview)
    }
}
