package com.example.konveyor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.validation.ValidationPolicy
import com.example.konveyor.items.VerticalSpaceItemDecoration
import com.example.konveyor.items.article.ArticleBlueprint
import com.example.konveyor.items.article.ArticlePresenter
import com.example.konveyor.items.photo.PhotoBlueprint
import com.example.konveyor.items.photo.PhotoPresenter

class MainActivity : AppCompatActivity() {

    private val itemList: RecyclerView by lazy { findViewById(R.id.itemList) as RecyclerView }
    private val dataSource = LocalDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemOffset = resources.getDimension(R.dimen.recycler_view_spacing).toInt()
        itemList.layoutManager = LinearLayoutManager(this)
        itemList.addItemDecoration(VerticalSpaceItemDecoration(itemOffset))

        val binder = ItemBinder.Builder()
                .registerItem(ArticleBlueprint(ArticlePresenter()))
                .registerItem(PhotoBlueprint(PhotoPresenter()))
                .setValidationPolicy(object : ValidationPolicy {
                    override var validateEagerly = true
                        get() = true
                })
                .build()

        val presenter = SimpleAdapterPresenter(binder, binder)
        itemList.adapter = SimpleRecyclerAdapter(presenter, binder)

        presenter.onDataSourceChanged(dataSource)
        itemList.adapter.notifyDataSetChanged()
    }
}
