package com.example.konveyor.items.photo

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.example.konveyor.R

class PhotoBlueprint(override val presenter: ItemPresenter<PhotoView, Photo>) : ItemBlueprint<PhotoView, Photo> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            R.layout.view_photo,
            { _, view -> PhotoViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is Photo
}
