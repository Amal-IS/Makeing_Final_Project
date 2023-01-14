package com.example.food.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.ActivityCategoryMealsBinding
import com.example.food.databinding.MealItemBinding
import com.example.food.pojo.MealsByCategory

class CategoryMealsAdapter :RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViwModel>(){
  private  var mealsList=ArrayList<MealsByCategory>()
    fun setMealsList(mealsList:List<MealsByCategory>){
        this.mealsList=mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }
    inner class CategoryMealsViwModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViwModel {
        return CategoryMealsViwModel(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsAdapter.CategoryMealsViwModel, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }
}