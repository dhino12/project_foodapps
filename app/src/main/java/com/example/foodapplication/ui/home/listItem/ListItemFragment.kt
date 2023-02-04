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
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.domain.model.Search
import com.example.core.ui.ListArticleAdapter
import com.example.core.ui.ListItemAdapter
import com.example.foodapplication.R
import com.example.foodapplication.databinding.FragmentListItemBinding
import com.example.foodapplication.ui.detail.food.DetailFoodActivity

class ListItemFragment : Fragment() {

    private var _binding: FragmentListItemBinding? = null
    private lateinit var toolbar: Toolbar
    private val binding get() = _binding!!

    companion object {
        const val ARTICLE = "list_Article"
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
            var listAdapter: Any? = null

            if (dataArticleList != null) {
                // Search list =============
                toolbar.title = getString(R.string.Search_list)

                listAdapter = ListArticleAdapter { article ->
                    val intent = Intent(activity, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_ID, article.key)
                    intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TAG, article.tags)
                    intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TITLE, article.title)
                    startActivity(intent)
                }
                listAdapter.submitList(dataArticleList)
            }

            if (dataCookingList != null) {
                // cooking list =============
                toolbar.title = getString(R.string.cooking_list)
                listAdapter = ListItemAdapter { cooking ->
                    val intent = Intent(activity, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.EXTRA_TITLE_COOKING, cooking.title)
                    intent.putExtra(DetailFoodActivity.EXTRA_ID_COOKING, cooking.cookingID)
                    startActivity(intent)
                }
                listAdapter.submitList(dataCookingList)
            }

            with(binding.rvListItem) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                adapter = listAdapter as RecyclerView.Adapter<*>?
            }

            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}