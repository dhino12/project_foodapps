package com.example.foodapplication.ui.category

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.core.data.Resource
import com.example.core.ui.ListCategoryAdapter
import com.example.foodapplication.R
import com.example.foodapplication.databinding.FragmentCategoryBinding
import com.example.foodapplication.ui.category.contentCategory.ContentCategoryFragment
import org.koin.android.ext.android.inject

class ListCategoryFragment : Fragment() {

    private val listCategoryViewModel: ListCategoryViewModel by inject()

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val listCategoryAdapter = ListCategoryAdapter()
            requireActivity().window.statusBarColor = Color.parseColor(getString(R.string.young_red))

            listCategoryAdapter.onClick = { selectedData, direction ->
                val mBundle = Bundle()
                mBundle.putString(ContentCategoryFragment.CONTENT_CATEGORY_TAG, selectedData.key)
                direction.findNavController().navigate(R.id.action_navigation_category_to_contentCategoryFragment, mBundle)
            }

            listCategoryViewModel.listCategory.observe(viewLifecycleOwner, { foodCategory ->
                if (foodCategory != null) {
                    when (foodCategory) {
                        is Resource.Loading -> {
                            binding.progressBarCategory.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBarCategory.visibility = View.GONE
                            listCategoryAdapter.setData(foodCategory.data)
                        }
                        is Resource.Error -> {
                            binding.progressBarCategory.visibility = View.VISIBLE
                            Toast.makeText(activity, "Error ${foodCategory.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(binding.rvListCategory) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = listCategoryAdapter
            }

            Glide.with(this)
                    .asGif()
                    .load(R.drawable.chef_cooking)
                    .into(binding.coverCategory)
        }
    }
}