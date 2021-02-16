package com.example.foodapplication.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.ui.ArticleAdapter
import com.example.core.ui.CookingAdapter
import com.example.foodapplication.R
import com.example.foodapplication.databinding.FragmentHomeBinding
import com.example.foodapplication.ui.detail.food.DetailFoodActivity
import com.example.foodapplication.ui.home.listItem.ListItemFragment
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by inject()

    private var _binding: FragmentHomeBinding? = null
    private var btnForwardViewArticle: Button? = null
    private var btnForwardViewCooking: Button? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        btnForwardViewArticle = _binding!!.tvArticle
        btnForwardViewCooking = _binding!!.tvCookingMenu
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            requireActivity().window.statusBarColor = Color.parseColor(getString(R.string.orange))
            val cookAdapter = CookingAdapter()
            val articleAdapter = ArticleAdapter()
            val arrayListArticle = ArrayList<Article>()
            val arrayListCooking = ArrayList<Cooking>()

            cookAdapter.onItemClick = { selectedData ->
                val intent = Intent(context, DetailFoodActivity::class.java)
                intent.putExtra(DetailFoodActivity.EXTRA_TITLE_COOKING, selectedData.title)
                intent.putExtra(DetailFoodActivity.EXTRA_ID_COOKING, selectedData.cookingID)
                startActivity(intent)
            }

            articleAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailFoodActivity::class.java)
                intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TITLE, selectedData.title)
                intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TAG, selectedData.tags)
                intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_ID, selectedData.key)
                startActivity(intent)
            }

            homeViewModel.cook.observe(viewLifecycleOwner, { food ->
                if (food != null) {
                    when (food) {
                        is Resource.Loading -> {
                            binding.progressBarHome.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBarHome.visibility = View.GONE
                            cookAdapter.setData(food.data)
                            arrayListCooking.addAll(food.data!!)
                            btnForwardViewCooking?.setOnClickListener { direction ->
                                val mBundle = Bundle()
                                mBundle.putParcelableArrayList(ListItemFragment.COOKING, arrayListCooking)
                                direction.findNavController().navigate(R.id.action_navigation_home_to_listItemFragment, mBundle)
                            }
                            binding.randomButton.setOnClickListener {
                                val moveToRandom = Intent(activity, Class.forName("com.example.foodapplication.random.ui.RandomActivity"))
                                moveToRandom.putParcelableArrayListExtra("keyRandom", arrayListCooking)
                                startActivity(moveToRandom)
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(activity, getString(R.string.message_error), Toast.LENGTH_SHORT).show()
                            Log.e("error CookingHomeFragment", "error ${food.message}")
                        }
                    }
                }
            })

            homeViewModel.article.observe(viewLifecycleOwner, { article ->
                if (article != null) {
                    when (article) {
                        is Resource.Loading -> {
                            binding.progressBarHome.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            articleAdapter.setData(article.data)
                            arrayListArticle.addAll(article.data!!)
                            btnForwardViewArticle?.setOnClickListener { direction ->
                                val mBundle = Bundle()
                                mBundle.putParcelableArrayList(ListItemFragment.ARTICLE, arrayListArticle)
                                direction.findNavController().navigate(R.id.action_navigation_home_to_listItemFragment, mBundle)
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(activity, getString(R.string.message_error), Toast.LENGTH_SHORT).show()
                            Log.e("error ArticleHomeFragment", "error ${article.message}")
                        }
                    }
                }
            })

            binding.searchButton.setOnClickListener { direction ->
                direction.findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
            }
            with(binding.rvRecentFood) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = cookAdapter
            }
            with(binding.rvPopular) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = articleAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}