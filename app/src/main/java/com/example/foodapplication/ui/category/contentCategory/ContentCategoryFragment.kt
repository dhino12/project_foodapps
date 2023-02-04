package com.example.foodapplication.ui.category.contentCategory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.ListItemAdapter
import com.example.foodapplication.R
import com.example.foodapplication.databinding.FragmentListItemBinding
import com.example.foodapplication.ui.detail.food.DetailFoodActivity
import org.koin.android.ext.android.inject

class ContentCategoryFragment : Fragment() {

    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!
    private val contentViewModel: ContentCategoryViewModel by inject()

    companion object {
        const val CONTENT_CATEGORY_TAG = "content_tag"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListItemBinding.inflate(inflater, container, false)
        Toast.makeText(activity, arguments?.getString(CONTENT_CATEGORY_TAG).toString(), Toast.LENGTH_SHORT).show()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            requireActivity().window.statusBarColor = Color.parseColor(getString(R.string.orange))
            val getTag = arguments?.getString(CONTENT_CATEGORY_TAG)
//            Log.e("error_contentCategory", getTag.toString())

            binding.toolbarBack.title = getTag?.replace("-", " ")

            val listAdapter = ListItemAdapter {cooking ->
                val intent = Intent(context, DetailFoodActivity::class.java)
                intent.putExtra(DetailFoodActivity.EXTRA_TITLE_COOKING, cooking.title)
                intent.putExtra(DetailFoodActivity.EXTRA_ID_COOKING, cooking.cookingID)
                startActivity(intent)
            }

            contentViewModel.setSelectedCategory(getTag)
            contentViewModel.contentCategory.observe(viewLifecycleOwner) { contentData ->
                if (contentData != null) {
                    when (contentData) {
                        is Resource.Loading -> {
                            binding.progressBarCategoryList.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBarCategoryList.visibility = View.GONE
                            listAdapter.submitList(contentData.data)
//                            Log.e("error_contentCategory", contentData.data?.size.toString())
                        }
                        is Resource.Error -> {
                            binding.progressBarCategoryList.visibility = View.VISIBLE
                            Toast.makeText(
                                activity,
                                contentData.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            with(binding.rvListItem) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                adapter = listAdapter
            }

            binding.toolbarBack.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        Log.e("error_content", (activity == null).toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}