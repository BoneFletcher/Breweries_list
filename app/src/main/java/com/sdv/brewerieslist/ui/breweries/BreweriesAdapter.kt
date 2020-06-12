package com.sdv.brewerieslist.ui.breweries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sdv.brewerieslist.R
import com.sdv.brewerieslist.data.breweries.Breweries
import com.sdv.brewerieslist.data.utills.setInfoWithEmptyCheck
import kotlinx.android.synthetic.main.breweries_item_layout.view.*

/**
 * Created by FrostEagle on 20.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */
class BreweriesAdapter (private var listener: OnItemClickListener) :
    androidx.recyclerview.widget.ListAdapter<Breweries, BreweriesAdapter.PetitionViewHolder>(NotificationPetitionItemDiffCallback()) {
    var list: List<Breweries> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetitionViewHolder {
        return PetitionViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PetitionViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    interface OnItemClickListener {
       // fun onItemClick(model: Breweries)
        fun onShowMapClick(model: Breweries)
    }

    fun clearAdapter() {
        submitList(list)
    }
    class PetitionViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        constructor(parent: ViewGroup) :
                this(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.breweries_item_layout,
                        parent,
                        false
                    )
                )

        fun bind(model: Breweries, listener: OnItemClickListener) {
            itemView.tv_breweries_title.text = model.name
            itemView.tv_phone.setInfoWithEmptyCheck(itemView.context.getString(R.string.phone),model.phone)
            itemView.tv_website.setInfoWithEmptyCheck(itemView.context.getString(R.string.website),model.websiteUrl)
            itemView.tv_city.setInfoWithEmptyCheck(itemView.context.getString(R.string.country),model.city)
            itemView.tv_country.setInfoWithEmptyCheck(itemView.context.getString(R.string.state),model.country)
            itemView.tv_state.setInfoWithEmptyCheck(itemView.context.getString(R.string.city),model.state)
            itemView.tv_street.setInfoWithEmptyCheck(itemView.context.getString(R.string.street),model.street)
            itemView.btn_show_on_map.setOnClickListener {
                listener.onShowMapClick(model)
            }
        }
    }


    class NotificationPetitionItemDiffCallback : DiffUtil.ItemCallback<Breweries>() {
        override fun areItemsTheSame(oldItem: Breweries, newItem: Breweries): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Breweries, newItem: Breweries): Boolean =
            oldItem == newItem
    }
}