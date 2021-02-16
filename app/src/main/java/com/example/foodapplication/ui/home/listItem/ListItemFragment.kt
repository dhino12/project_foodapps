package com.example.foodapplication.ui.home.listItem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.ui.ListItemAdapter
import com.example.foodapplication.R
import com.example.foodapplication.databinding.FragmentListItemBinding
import com.example.foodapplication.ui.detail.food.DetailFoodActivity

class ListItemFragment : Fragment() {

    private var _binding: FragmentListItemBinding? = null
    private lateinit var toolbar: Toolbar
    private val binding get() = _binding!!

    companion object {
        const val ARTICLE = "list_article"
        const val COOKING = "list_cooking"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentListItemBinding.inflate(inflater, container, false)
        toolbar = _binding!!.toolbarBack
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()

        if (activity != null) {
            val dataArticleList = arguments?.getParcelableArrayList<Article>(ARTICLE)
            val dataCookingList = arguments?.getParcelableArrayList<Cooking>(COOKING)

            val listAdapter = ListItemAdapter()

            if (dataArticleList != null) {
                // article list =============
                toolbar.title = getString(R.string.article_list)

                listAdapter.onItemClickArticle = { selectedData ->
                    val intent = Intent(activity, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_ID, selectedData.key)
                    intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TAG, selectedData.tags)
                    intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TITLE, selectedData.title)
                    startActivity(intent)
                }
                listAdapter.setData(newListDataArticle = dataArticleList)

            } else if (dataCookingList != null) {
                // cooking list =============
                toolbar.title = getString(R.string.cooking_list)
                listAdapter.onItemClickCooking = { selectedData ->
                    val intent = Intent(activity, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.EXTRA_TITLE_COOKING, selectedData.title)
                    intent.putExtra(DetailFoodActivity.EXTRA_ID_COOKING, selectedData.cookingID)
                    startActivity(intent)
                }
                listAdapter.setData(newListDataCooking = dataCookingList)

            }

            with(binding.rvListItem) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                adapter = listAdapter
            }

            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}