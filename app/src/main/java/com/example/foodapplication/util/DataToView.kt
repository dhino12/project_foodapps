package com.example.foodapplication.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.ui.IngredientsAdapter
import com.example.core.ui.TimelineAdapter
import com.example.foodapplication.R
import com.example.foodapplication.databinding.ActivityDetailFoodBinding
import com.example.foodapplication.ui.detail.food.DetailViewModel
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import com.nineoldandroids.view.ViewHelper

class DataToView(
    private var binding: ActivityDetailFoodBinding,
    private var context: Context,
    private var viewModel: DetailViewModel
) {
    private lateinit var adaptersTL: TimelineAdapter
    private lateinit var adapterIngredients: IngredientsAdapter

    fun implementToView(
        cookingDetailEntity: Cooking? = null,
        articleDetailEntity: Article? = null,
    ) {

        if (cookingDetailEntity != null) {
            Glide.with(context)
                .load(cookingDetailEntity.thumb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.placeholderOf(R.drawable.baseline_hourglass_bottom_black_18dp).error(R.drawable.ic_error))
                .into(binding.imgFoodDetail)

            binding.titleDetail.text = cookingDetailEntity.title
            binding.servingsContentDetail.text = cookingDetailEntity.servings
            binding.timesContentDetail.text = cookingDetailEntity.times
            binding.difficultyContentDetail.text = cookingDetailEntity.difficulty

            val stringToArrayStep = cookingDetailEntity.step?.removeSurrounding("[", "]")?.split(",,")
            val stringToArrayIngredient = cookingDetailEntity.ingredient?.removeSurrounding("[", "]")?.split(",,")

            if (stringToArrayStep != null) {
                setTLRecyclerView(stringToArrayStep)
            }
            if (stringToArrayIngredient != null) {
                setIngRecyclerView(stringToArrayIngredient)
            }

            var stateFavorite = cookingDetailEntity.isFavorite
            if (stateFavorite != null) {
                setStatusFavorite(stateFavorite)
                binding.btnFavorite.setOnClickListener {
                    stateFavorite = !stateFavorite!!
                    viewModel.setFavoriteFood(cookingDetailEntity, stateFavorite!!)
                    setStatusFavorite(stateFavorite!!)
                }
            }
        } else {
            binding.rvIngredients.visibility = View.GONE
            binding.tvChangeStep.visibility = View.GONE
            binding.rvStep.visibility = View.GONE
            binding.btnFavorite.visibility = View.GONE

            binding.tvDesc.visibility = View.VISIBLE

            Glide.with(context)
                .load(articleDetailEntity?.thumb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgFoodDetail)
            binding.titleDetail.text = articleDetailEntity?.title
            binding.tvChangeIngredients.text = context.getString(R.string.description)
            binding.tvDesc.text = articleDetailEntity?.description

            binding.tvTitleServings.visibility = View.GONE
            binding.servingsContentDetail.visibility = View.GONE
            binding.tvTitleTime.visibility = View.GONE
            binding.timesContentDetail.visibility = View.GONE
            binding.tvTitleDifficulty.visibility = View.GONE
            binding.difficultyContentDetail.visibility = View.GONE
        }
    }

    private fun setIngRecyclerView(ingredients: List<String>) {
        with(binding.rvIngredients) {
            adapterIngredients = IngredientsAdapter()
            adapterIngredients.submitList(ingredients)
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterIngredients
        }
    }

    private fun setTLRecyclerView(step: List<String>) {
        with(binding.rvStep) {
            adaptersTL = TimelineAdapter()
            adaptersTL.submitList(step)
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adaptersTL
        }
    }

    private fun setStatusFavorite(state: Boolean) {
        if (state) {
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24))
        } else {
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24))
        }
    }

    fun showLoading(switch: Boolean) {
        if (switch) {
            binding.loadingBar.visibility = View.VISIBLE
            binding.bgColorView.visibility = View.VISIBLE
        } else {
            binding.loadingBar.visibility = View.GONE
            binding.bgColorView.visibility = View.GONE
        }
    }

    fun updateFlexibleView(scrollY: Int) {
        val mActionBarSize = getActionBarSize()
        val mFlexibleSpaceImageHeight = context.resources.getDimensionPixelSize(R.dimen.flexible_image_height_detail)

        val flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize.toFloat()
        val minOverlayTransitionY = mActionBarSize - binding.overlay.height
        val minActionBarOverlayTransitionY = mActionBarSize - binding.toolbarOverlay.height
        val minStatusBarOverlayTransitionY = mActionBarSize - binding.statusBarCustom.height

        ViewHelper.setTranslationY(binding.overlay, ScrollUtils.getFloat(-scrollY.toFloat(), minOverlayTransitionY.toFloat(), 0f))
        ViewHelper.setTranslationY(binding.imgFoodDetail, ScrollUtils.getFloat(-scrollY / 2.toFloat(), minOverlayTransitionY.toFloat(), 0f))
        ViewHelper.setTranslationY(binding.toolbarOverlay, ScrollUtils.getFloat(-scrollY.toFloat(), minActionBarOverlayTransitionY.toFloat(), 0f))
        ViewHelper.setTranslationY(binding.statusBarCustom, ScrollUtils.getFloat(-scrollY.toFloat(), minStatusBarOverlayTransitionY.toFloat(), 0f))

        ViewHelper.setAlpha(binding.overlay, ScrollUtils.getFloat(scrollY.toFloat() / flexibleRange, 0f, 1f))
        ViewHelper.setAlpha(binding.toolbarOverlay, ScrollUtils.getFloat(scrollY.toFloat() / flexibleRange, 0f, 1f))
        ViewHelper.setAlpha(binding.statusBarCustom, ScrollUtils.getFloat(scrollY.toFloat() / flexibleRange, 0f, 1f))
    }

    private fun getActionBarSize(): Int {
        val typedValue = TypedValue()
        val textSizeAttr = intArrayOf(androidx.constraintlayout.widget.R.attr.actionBarSize)
        val indexOfAttrTextSize = 0
        val a = context.obtainStyledAttributes(typedValue.data, textSizeAttr)
        val actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1)
        a.recycle()
        return actionBarSize
    }
}