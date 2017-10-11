package com.example.konveyor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.data_source.ListDataSource
import com.example.konveyor.items.VerticalSpaceItemDecoration
import com.example.konveyor.items.article.Article
import com.example.konveyor.items.article.ArticleBlueprint
import com.example.konveyor.items.article.ArticlePresenter
import com.example.konveyor.items.photo.Photo
import com.example.konveyor.items.photo.PhotoBlueprint
import com.example.konveyor.items.photo.PhotoPresenter

class MainActivity : AppCompatActivity() {

    private val itemList: RecyclerView by lazy { findViewById(R.id.itemList) as RecyclerView }
    private val dataSource = ListDataSource(listOf(
            Article(1, "Northern Lights", R.string.article_body_preview, R.drawable.article1),
            Photo(1, "Full Moon", R.drawable.photo1),
            Photo(2, "The Starry Sky", R.drawable.photo2),
            Article(2, "An Ancient Discovery", R.string.article_body_preview, R.drawable.article2),
            Photo(3, "Racing Through the Sand", R.drawable.photo3),
            Article(3, "An Increase in Nightlife", R.string.article_body_preview, R.drawable.article3),
            Article(4, "The Effects of a Day at the Beach", R.string.article_body_preview, R.drawable.article4),
            Photo(4, "A Normal Day", R.drawable.photo4)
    ))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemOffset = resources.getDimension(R.dimen.recycler_view_spacing).toInt()
        itemList.layoutManager = LinearLayoutManager(this)
        itemList.addItemDecoration(VerticalSpaceItemDecoration(itemOffset))

        val binder = ItemBinder.Builder()
                .registerItem(ArticleBlueprint(ArticlePresenter()))
                .registerItem(PhotoBlueprint(PhotoPresenter()))
                .build()

        val presenter = SimpleAdapterPresenter(binder, binder)
        itemList.adapter = SimpleRecyclerAdapter(presenter, binder).apply { setHasStableIds(true) }

        presenter.onDataSourceChanged(dataSource)
        itemList.adapter.notifyDataSetChanged()
    }
}
