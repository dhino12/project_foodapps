package com.example.foodapplication.favorite.ui

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
import com.example.core.ui.ListItemAdapter
import com.example.favorite.R
import com.example.foodapplication.databinding.FragmentListItemBinding
import com.example.foodapplication.ui.detail.food.DetailFoodActivity
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class FavoritesFragment : Fragment() {

    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!
    private val viewModelFavorite: FavoriteFoodViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListItemBinding.inflate(inflater, container, false)
        Toast.makeText(activity, "ada di Favorite", Toast.LENGTH_SHORT).show()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            requireActivity().window.statusBarColor = Color.parseColor(getString(R.string.orange))

            val listAdapter = ListItemAdapter { cooking ->
                val intent = Intent(activity, DetailFoodActivity::class.java)
                intent.putExtra(DetailFoodActivity.EXTRA_TITLE_COOKING, cooking.title)
                startActivity(intent)
            }

            loadKoinModules(viewModelModule)

            binding.toolbarBack.title = getString(R.string.favorite)
            viewModelFavorite.favoriteData.observe(viewLifecycleOwner) { dataFavorite ->
                if (dataFavorite.isNullOrEmpty()) {
                    binding.imgNoItem.visibility = View.VISIBLE
                    binding.btnBackToMain.visibility = View.VISIBLE
                    binding.tvMessageFavorite.visibility = View.VISIBLE
                } else {
                    binding.btnBackToMain.visibility = View.GONE
                    binding.imgNoItem.visibility = View.GONE
                    binding.tvMessageFavorite.visibility = View.GONE
                }
                listAdapter.submitList(dataFavorite)
                listAdapter.notifyDataSetChanged()

                with(binding.rvListItem) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = listAdapter
                }
            }

            binding.btnBackToMain.setOnClickListener {
                requireActivity().onBackPressed()
            }
            binding.toolbarBack.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}