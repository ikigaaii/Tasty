package com.dimension.tasty.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dimension.tasty.databinding.ItemRecipePreviewBinding
import com.dimension.tasty.models.SavedRecipe


class SavedRecipesAdapter : RecyclerView.Adapter<SavedRecipesAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemRecipePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<SavedRecipe>() {
        override fun areItemsTheSame(oldItem: SavedRecipe, newItem: SavedRecipe): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: SavedRecipe, newItem: SavedRecipe): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipesAdapter.ViewHolder {
        val binding = ItemRecipePreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedRecipesAdapter.ViewHolder, position: Int) {
        val recipe = differ.currentList[position]
        holder.binding.apply {

            ivRecipeImage.setImageBitmap(BitmapFactory.decodeByteArray(recipe.image, 0, recipe.image.size))
            tvRecipeTitle.text = recipe.title
            holder.itemView.setOnClickListener{
                onSavedRecipeItemClickListener?.let { it(recipe) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onSavedRecipeItemClickListener: ((SavedRecipe) -> Unit)? =  null
    fun setOnSavedRecipeItemClickListener(listener: (SavedRecipe) -> Unit) {
        onSavedRecipeItemClickListener = listener
    }


}