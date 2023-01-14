package com.example.food.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.food.R
import com.example.food.databinding.ActivityMealBinding
import com.example.food.fragments.HomeFragment
import com.example.food.pojo.Meal
import com.example.food.viewModel.MealViewModel


class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink:String
    private  lateinit var mealMvvm:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

      mealMvvm=ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationFromIntent()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDeatialsLiveData()
        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
       binding.imgYoutube.setOnClickListener{
         val intent= Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
           startActivity(intent)
       }
    }

    private fun observerMealDeatialsLiveData() {
       mealMvvm.observerMealDetailsLiveData().observe(this,object:Observer<Meal>{
           override fun onChanged(t: Meal?) {
               onResponseCase()
               val meal=t
               binding.tvCategory.text="Category : ${meal!!.strCategory}"
               binding.tvArea.text="Area : ${meal.strArea}"
               binding.tvInstructionsSteps.text=meal.strInstructions

               youtubeLink=meal.strYoutube
           }

       })
    }

    private fun setInformationFromIntent() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
        val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
    private fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        //binding.btnAddToFav.visibility= View.INVISIBLE
        binding.tvInstructions.visibility=View.INVISIBLE
        binding.tvCategory.visibility=View.INVISIBLE
        binding.tvArea.visibility=View.INVISIBLE
        binding.imgYoutube.visibility=View.INVISIBLE

    }
    private fun onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
       // binding.btnAddToFav.visibility= View.VISIBLE
        binding.tvInstructions.visibility=View.VISIBLE
        binding.tvCategory.visibility=View.VISIBLE
        binding.tvArea.visibility=View.VISIBLE
        binding.imgYoutube.visibility=View.VISIBLE

    }
}