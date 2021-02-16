package com.example.foodapplication.random.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import com.example.foodapplication.random.databinding.ActivityRandomBinding
import com.example.foodapplication.random.di.viewModelModule
import com.example.foodapplication.random.util.DataToViewRandom
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class RandomActivity : AppCompatActivity(), ObservableScrollViewCallbacks {

    private lateinit var binding: ActivityRandomBinding
    private val viewModelRandom: RandomViewModel by inject()
    private lateinit var dataToViewRandom: DataToViewRandom

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarOverlay)
        val toolbars = supportActionBar
        if (toolbars != null) supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (Build.VERSION.SDK_INT >= 19) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            //make fully Android Transparent Status bar
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                window.statusBarColor = Color.TRANSPARENT
            }
        }

        loadKoinModules(viewModelModule)
        dataToViewRandom = DataToViewRandom(binding, this, viewModelRandom)

        val dataRandomCooking = intent.getParcelableArrayListExtra<Cooking>("keyRandom")
        val keyCollection = mutableListOf<String>()

        if (dataRandomCooking != null) {
            val dataRandom = dataRandomCooking.random()
            if (keyCollection.isNullOrEmpty()) {
                keyCollection.add(dataRandom.title ?: "")
                keyCollection.add(dataRandom.cookingID ?: "")
            } else {
                keyCollection[0] = dataRandom.title ?: ""
                keyCollection[1] = dataRandom.cookingID ?: ""
            }

            viewModelRandom.setRandomData(keyCollection)
            viewModelRandom.cookingData.observe(this, { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {
                            dataToViewRandom.showLoading(true)
                        }
                        is Resource.Success -> {
                            dataToViewRandom.showLoading(false)
                            dataToViewRandom.implementToView(response.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(this, "Error ${response.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
            binding.scroll.setScrollViewCallbacks(this)

            ScrollUtils.addOnGlobalLayoutListener(binding.scroll) {
                onScrollChanged(0, firstScroll = false, dragging = false)
            }
        }
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        dataToViewRandom.updateFlexibleView(scrollY)
    }

    override fun onDownMotionEvent() {

    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

    }
}