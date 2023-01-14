package com.example.food.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food.R
import com.example.food.adapters.CategoryMealsAdapter
import com.example.food.databinding.ActivityCategoryMealsBinding
import com.example.food.fragments.HomeFragment
import com.example.food.viewModel.CategoryMealsViewModel


class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsApapter:CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preperRecyclerView()
        categoryMealsViewModel=ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer {mealsList->
        binding.tvCategoryCount.text=mealsList.size.toString()
            categoryMealsApapter.setMealsList(mealsList)
        })
    }

    private fun preperRecyclerView() {
      categoryMealsApapter= CategoryMealsAdapter()
      binding.rvMeals.apply {
      layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
      adapter=categoryMealsApapter
      }
    }
}