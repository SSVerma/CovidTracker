package com.ssverma.covidtracker.ui.stats

import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.data.model.domain.Symptom
import com.ssverma.covidtracker.databinding.RowItemSymptomBinding
import com.ssverma.covidtracker.ui.BaseListAdapter

class SymptomAdapter : BaseListAdapter<Symptom, RowItemSymptomBinding>(
    Symptom.DIFF_CALLBACK
) {
    override fun providerItemLayout(position: Int): Int {
        return R.layout.row_item_symptom
    }
}