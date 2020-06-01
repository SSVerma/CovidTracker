package com.ssverma.covidtracker.ui.home

import androidx.core.content.ContextCompat
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.data.model.domain.Stat
import com.ssverma.covidtracker.databinding.RowItemStatBinding
import com.ssverma.covidtracker.ui.BaseListAdapter

class StatAdapter : BaseListAdapter<Stat, RowItemStatBinding>(
    Stat.DIFF_CALLBACK
) {
    override fun providerItemLayout(position: Int): Int {
        return R.layout.row_item_stat
    }

    override fun additionalBinding(
        binding: RowItemStatBinding,
        position: Int,
        viewHolder: BaseViewHolder
    ) {
        binding.tvLabel.setText(getItem(position).labelResId)
        binding.cvContentHolder.setCardBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                getItem(position).backgroundColor
            )
        )
    }

}