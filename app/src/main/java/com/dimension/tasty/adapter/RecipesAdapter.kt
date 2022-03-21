package com.dimension.tasty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimension.tasty.databinding.ItemRecipePreviewBinding
import com.dimension.tasty.models.Recipe


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemRecipePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesAdapter.ViewHolder {
        val binding = ItemRecipePreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

  //  override fun onBindViewHolder(holder: RecipesAdapter.ViewHolder, position: Int) {
  //      val recipe = differ.currentList[position]
  //      holder.binding.apply {
  //          Glide.with(holder.itemView).load(recipe.strMealThumb).into(ivRecipeImage)
  //          tvTitle.text = recipe.strMeal
  //          tvDescription.text = recipe.strInstructions
  //          tvOrigin.text = recipe.strArea
  //          tvTag.text = recipe.strCategory
  //          holder.itemView.setOnClickListener{
  //              onItemClickListener?.let { it(recipe) }
  //          }
  //      }
//
//
  //  }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Recipe) -> Unit)? =  null
    fun setOnItemClickListener(listener: (Recipe) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}