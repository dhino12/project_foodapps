package com.example.foodapplication.ui.detail.food

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.core.data.Resource
import com.example.foodapplication.databinding.ActivityDetailFoodBinding
import com.example.foodapplication.util.DataToView
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION")
class DetailFoodActivity : AppCompatActivity(), ObservableScrollViewCallbacks {
    private lateinit var binding: ActivityDetailFoodBinding

    private val viewModel: DetailViewModel by inject()
    private lateinit var dataToView: DataToView

    companion object {
        const val EXTRA_TITLE_COOKING = "cooking_title_key"
        const val EXTRA_ID_COOKING = "cooking_id_key"
        const val EXTRA_ARTICLE_TITLE = "article_title_key"
        const val EXTRA_ARTICLE_ID = "article_id_key"
        const val EXTRA_ARTICLE_TAG = "article_tag_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarOverlay)
        val toolbars = supportActionBar
        if (toolbars != null) supportActionBar?.setDisplayHomeAsUpEnabled(true)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //make fully Android Transparent Status bar
        window.statusBarColor = Color.TRANSPARENT

        binding.titles.text = title
        title = null

        val extras = intent.extras
        val keyCollection = mutableListOf<String>()
        val titleCooking = extras?.getString(EXTRA_TITLE_COOKING)
        val idCooking = extras?.getString(EXTRA_ID_COOKING)
        val titleArticle = extras?.getString(EXTRA_ARTICLE_TITLE)
        val idArticle = extras?.getString(EXTRA_ARTICLE_ID)
        val tagArticle = extras?.getString(EXTRA_ARTICLE_TAG)

        Log.e("error TitleContent", titleCooking.toString())
        Log.e("error IDContent", idCooking.toString())

        dataToView = DataToView(binding, this, viewModel)

        if (titleCooking != null || idCooking != null) {
            if (keyCollection.isNullOrEmpty()) {
                keyCollection.add(titleCooking.toString())
                keyCollection.add(idCooking.toString())
            } else {
                keyCollection[0] = titleCooking.toString()
                keyCollection[1] = idCooking.toString()
            }

            viewModel.setSelectedCook(titleCookingDb = keyCollection)
            viewModel.cookingDetail.observe(this) { titleKey ->
                if (titleKey != null) {
                    when (titleKey) {
                        is Resource.Loading -> {
                            dataToView.showLoading(true)
                        }
                        is Resource.Success -> {
                            dataToView.showLoading(false)
                            dataToView.implementToView(cookingDetailEntity = titleKey.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(this, titleKey.message.toString(), Toast.LENGTH_LONG)
                                .show()
                            Log.e("error CookingDetailActivity", "error ${titleKey.message}")
                        }
                    }
                }
            }
        } else if (titleArticle != null && idArticle != null && tagArticle != null) {
            if (keyCollection.isNullOrEmpty()) {
                keyCollection.add(titleArticle)
                keyCollection.add(idArticle)
                keyCollection.add(tagArticle)
            } else {
                keyCollection[0] = titleArticle
                keyCollection[1] = idArticle
                keyCollection[2] = tagArticle
            }

            viewModel.setSelectedCook(titleArticleDb = keyCollection)
            viewModel.articleDetail.observe(this) { titleKey ->
                if (titleKey != null) {
                    when (titleKey) {
                        is Resource.Loading -> {
                            dataToView.showLoading(true)
                        }
                        is Resource.Success -> {
                            dataToView.showLoading(false)
                            dataToView.implementToView(articleDetailEntity = titleKey.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(this, titleKey.message.toString(), Toast.LENGTH_LONG)
                                .show()
                            Log.e("error ArticleDetailActivity", "error ${titleKey.message}")
                        }
                    }
                }
            }
        }

        binding.scroll.setScrollViewCallbacks(this)

        ScrollUtils.addOnGlobalLayoutListener(binding.scroll) {
            onScrollChanged(0, false, dragging = false)
        }
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        dataToView.updateFlexibleView(scrollY)
    }

    override fun onDownMotionEvent() {}
    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {}

    override fun onDestroy() {
        super.onDestroy()
        Glide.get(this).clearMemory()
    }
}