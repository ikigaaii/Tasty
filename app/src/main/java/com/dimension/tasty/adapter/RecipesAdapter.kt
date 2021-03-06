package com.dimension.tasty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimension.tasty.databinding.ItemRecipePreviewBinding
import com.dimension.tasty.models.Recipe
import com.dimension.tasty.models.Result
import com.dimension.tasty.models.SavedRecipe


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemRecipePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesAdapter.ViewHolder {
        val binding = ItemRecipePreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesAdapter.ViewHolder, position: Int) {
        val recipe = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(recipe.image).into(ivRecipeImage)
            tvRecipeTitle.text = recipe.title
            holder.itemView.setOnClickListener{
                onSearchRecipeItemClickListener?.let { it(recipe) }
            }
        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onSearchRecipeItemClickListener: ((Result) -> Unit)? =  null
    fun setOnSearchRecipeItemClickListener(listener: (Result) -> Unit) {
        onSearchRecipeItemClickListener = listener
    }

}