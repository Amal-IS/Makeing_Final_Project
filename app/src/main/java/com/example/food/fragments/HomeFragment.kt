package com.example.food.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.food.activites.CategoryMealsActivity
import com.example.food.activites.MealActivity
import com.example.food.adapters.CategoriesAdapter
import com.example.food.adapters.MostPopularAdapter
import com.example.food.databinding.FragmentHomeBinding
import com.example.food.pojo.MealsByCategory
import com.example.food.pojo.Meal
import com.example.food.viewModel.HomeViewModel



class HomeFragment : Fragment() {
private lateinit var binding: FragmentHomeBinding
private lateinit var homeMvvm:HomeViewModel
private lateinit var randomMeal: Meal
private lateinit var popularItemsAdapter:MostPopularAdapter
private lateinit var categoriesAdapter:CategoriesAdapter

companion object{
    const val MEAL_ID="com.example.food.fragments.idMeal"
    const val MEAL_NAME="com.example.food.fragments.nameMeal"
    const val MEAL_THUMB="com.example.food.fragments.thumbMeal"
    const val CATEGORY_NAME="com.example.food.fragments.categoryName"
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     homeMvvm= ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter= MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        homeMvvm.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()





    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category ->
          val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)

        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter= CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
         adapter=categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories->
        categoriesAdapter.setCategoryList(categories)


        })
    }

    private fun onPopularItemClick() {
      popularItemsAdapter.onItemClick={meal->
         val intent=Intent(activity,MealActivity::class.java)
          intent.putExtra(MEAL_ID,meal.idMeal)
          intent.putExtra(MEAL_NAME,meal.strMeal)
          intent.putExtra(MEAL_THUMB,meal.strMealThumb)
          startActivity(intent)
      }
    }

    private fun preparePopularItemsRecyclerView() {
      binding.recViewMealsPopular.apply {
          layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
          adapter=popularItemsAdapter
      }
    }

    private fun observePopularItemsLiveData() {
       homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner,
       { mealList ->
popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
       }
       )
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
    homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
    ) { meal ->
        Glide.with(this@HomeFragment)
            .load(meal!!.strMealThumb)
            .into(binding.imgRandomMeal)

        this.randomMeal=meal
    }
    }


}