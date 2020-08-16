package com.cuncis.bukatoko.ui.transaction.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.TransactionDetail
import com.cuncis.bukatoko.databinding.ItemTransactionDetailBinding

class TransactionDetailAdapter : RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder>() {

    private var detailList = arrayListOf<TransactionDetail.Response.Data.Detail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction_detail,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = detailList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.detail = detailList[position]
    }

    fun submitList(newDetailList: List<TransactionDetail.Response.Data.Detail>) {
        detailList.clear()
        detailList.addAll(newDetailList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTransactionDetailBinding)
        : RecyclerView.ViewHolder(binding.root)

}