package com.example.konveyor.items.photo

import androidx.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.example.konveyor.R
import com.squareup.picasso.Picasso

interface PhotoView : ItemView {
    fun setImage(@DrawableRes image: Int)
    fun setTitle(title: String)
}

class PhotoViewHolder(view: View) : BaseViewHolder(view), PhotoView {
    private val image = view.findViewById(R.id.photoImage) as ImageView
    private val title = view.findViewById(R.id.photoTitle) as TextView

    override fun setImage(image: Int) = Picasso.get()
            .load(image)
            .fit()
            .centerCrop()
            .into(this.image)

    override fun setTitle(title: String) {
        this.title.text = title
    }
}
