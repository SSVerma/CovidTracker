package com.ssverma.covidtracker.ui.country

import android.widget.Filter
import android.widget.Filterable
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.data.model.domain.CovidStat
import com.ssverma.covidtracker.databinding.RowItemCountryStatBinding
import com.ssverma.covidtracker.ui.BaseListAdapter

class CountryStatAdapter : BaseListAdapter<CovidStat, RowItemCountryStatBinding>(
    CovidStat.DIFF_CALLBACK
), Filterable {

    private val originalItems = mutableListOf<CovidStat>()
    private val filteredItems = mutableListOf<CovidStat>()

    lateinit var onNoSearchItemFound: () -> Unit

    fun search(query: String?) {
        query?.let {
            filter.filter(it)
        }
    }

    fun submitList(list: List<CovidStat>?, isOriginal: Boolean = true) {
        if (isOriginal) {
            originalItems.addAll(list ?: emptyList())
        }
        super.submitList(list)
        if (!isOriginal) {
            /*Inefficient*/
            notifyDataSetChanged()
        }
    }

    override fun providerItemLayout(position: Int): Int {
        return R.layout.row_item_country_stat
    }

    override fun additionalBinding(
        binding: RowItemCountryStatBinding,
        position: Int,
        viewHolder: BaseViewHolder
    ) {
        binding.ivFlag.clipToOutline = true
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                filteredItems.clear()
                if (constraint.isNullOrBlank()) {
                    filteredItems.addAll(originalItems)
                } else {
                    val searchResults = originalItems.filter {
                        it.countryName?.startsWith(constraint, true) ?: false
                    }
                    filteredItems.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = filteredItems
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (filteredItems.isNullOrEmpty()) {
                    if (::onNoSearchItemFound.isInitialized) {
                        onNoSearchItemFound()
                    }
                }
                submitList(filteredItems, false)
            }
        }
    }

}