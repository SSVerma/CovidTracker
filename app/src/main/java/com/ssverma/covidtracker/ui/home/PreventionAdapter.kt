package com.ssverma.covidtracker.ui.home

import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.data.model.domain.Prevention
import com.ssverma.covidtracker.databinding.RowItemPreventionBinding
import com.ssverma.covidtracker.ui.BaseListAdapter

class PreventionAdapter : BaseListAdapter<Prevention, RowItemPreventionBinding>(
    Prevention.DIFF_CALLBACK
) {
    override fun providerItemLayout(position: Int): Int {
        return R.layout.row_item_prevention
    }

    override fun additionalBinding(
        binding: RowItemPreventionBinding,
        position: Int,
        viewHolder: BaseViewHolder
    ) {
        binding.tvTitle.setText(getItem(position).title)
        binding.ivIllustration.setImageResource(getItem(position).imageId)
    }
}