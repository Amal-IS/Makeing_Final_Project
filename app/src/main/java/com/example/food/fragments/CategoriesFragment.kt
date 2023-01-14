package com.example.food.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.food.R
import com.example.food.activites.CategoryMealsActivity
import com.example.food.activites.MainActivity
import com.example.food.activites.MealActivity
import com.example.food.adapters.CategoriesAdapter
import com.example.food.databinding.FragmentCategoriesBinding
import com.example.food.databinding.FragmentHomeBinding
import com.example.food.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.food.pojo.MealsByCategory
import com.example.food.viewModel.CategoriesViewModel
import com.example.food.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: CategoriesViewModel



    companion object {
        const val MEAL_ID = "com.example.food.fragments.idMeal"
        const val MEAL_NAME = "com.example.food.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.food.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.food.fragments.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategories()
        // viewModel.observeCategoriesLiveData()
        preparRecyclerView()
        obervew()
        onCategoryClick()
    }
   /** private fun onPopularItemClick() {
        CategoriesAdapter.={meal->
            val intent=Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }**/

    private fun obervew() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun preparRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CategoriesFragment.CATEGORY_NAME, category.strCategory)
            startActivity(intent)

        }


    }
}