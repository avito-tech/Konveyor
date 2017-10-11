package com.example.konveyor.items.photo

import com.avito.konveyor.blueprint.ItemPresenter

class PhotoPresenter : ItemPresenter<PhotoView, Photo> {
    override fun bindView(view: PhotoView, item: Photo, position: Int) {
        view.setImage(item.image)
        view.setTitle(item.title)
    }
}
