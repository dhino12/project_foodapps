package com.example.foodapplication.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.SearchAdapter
import com.example.foodapplication.R
import com.example.foodapplication.databinding.FragmentSearchBinding
import com.example.foodapplication.ui.detail.food.DetailFoodActivity
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    private lateinit var _binding: FragmentSearchBinding
    private val binding get() = _binding
    private val viewModel: SearchViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val searchAdapter = SearchAdapter()

            searchAdapter.onItemClicked = { selectedItem ->
                val intent = Intent(activity, DetailFoodActivity::class.java)
                intent.putExtra(DetailFoodActivity.EXTRA_TITLE_COOKING, selectedItem.title)
                intent.putExtra(DetailFoodActivity.EXTRA_ID_COOKING, selectedItem.cookingID)
                startActivity(intent)
            }

            binding.imgSearchFood.visibility = View.VISIBLE
            binding.tvSearchMessage.visibility = View.VISIBLE
            binding.searchBar.queryHint = getString(R.string.queryHint)
            binding.searchBar.setOnCloseListener {
                requireActivity().onBackPressed()
                false
            }
            binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.searchBar.showKeyboard()
                } else {
                    binding.searchBar.hideKeyboard()
                }
            }
            binding.searchBar.isIconified = false
            binding.searchBar.requestFocusFromTouch()
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.e("error SearchQuery", query.toString())
                    if (query != null) {
                        binding.imgSearchFood.visibility = View.GONE
                        binding.tvSearchMessage.visibility = View.GONE

                        viewModel.setQuerySearch(query)
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.search.observe(viewLifecycleOwner, { foodSearch ->
                            when (foodSearch) {
                                is Resource.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Resource.Success -> {
                                    if (foodSearch.data.isNullOrEmpty()) {
                                        Toast.makeText(activity, "Maaf Data kosong silahkan refresh kembali ${foodSearch.message} / mohon tunggu ", Toast.LENGTH_LONG).show()
                                    }
                                    binding.imgSearchNotFound.visibility = View.GONE
                                    binding.tvSearchResult.visibility = View.VISIBLE
                                    binding.progressBar.visibility = View.GONE

                                    searchAdapter.setData(foodSearch.data)
                                    Log.e("error SearchData", foodSearch.data?.size.toString())

                                    with(binding.rvSearchFood) {
                                        layoutManager = LinearLayoutManager(activity)
                                        setHasFixedSize(true)
                                        adapter = searchAdapter
                                    }

                                }
                                is Resource.Error -> {
                                    binding.imgSearchNotFound.visibility = View.VISIBLE
                                    Toast.makeText(activity, foodSearch.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}