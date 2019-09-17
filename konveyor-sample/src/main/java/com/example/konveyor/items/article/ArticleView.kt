package com.example.konveyor.items.article

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.example.konveyor.R
import com.squareup.picasso.Picasso

interface ArticleView : ItemView {
    fun setImage(@DrawableRes image: Int)
    fun setTitle(title: String)
    fun setBodyPreview(@StringRes bodyPreview: Int)
}

class ArticleViewHolder(view: View) : BaseViewHolder(view), ArticleView {
    private val image = view.findViewById(R.id.articleImage) as ImageView
    private val title = view.findViewById(R.id.articleTitle) as TextView
    private val bodyPreview = view.findViewById(R.id.articleBodyPreview) as TextView

    override fun setImage(@DrawableRes image: Int) = Picasso.get()
            .load(image)
            .fit()
            .centerCrop()
            .into(this.image)

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setBodyPreview(@StringRes bodyPreview: Int) = this.bodyPreview.setText(bodyPreview)
}
