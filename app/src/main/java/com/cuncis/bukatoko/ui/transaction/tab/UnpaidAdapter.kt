package com.cuncis.bukatoko.ui.transaction.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Transaction
import com.cuncis.bukatoko.databinding.ItemTransactionBinding

class UnpaidAdapter: RecyclerView.Adapter<UnpaidAdapter.ViewHolder>() {

    var onUploadClick: ((String?) -> Unit)? = null

    private val unpaidList = arrayListOf<Transaction.Response.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = unpaidList.size

    fun submitUnpaidList(newList: List<Transaction.Response.Data>) {
        unpaidList.clear()
        unpaidList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.transaction = unpaidList[position]
        holder.binding.btnUpload.setOnClickListener {
            onUploadClick?.invoke(unpaidList[position].transaction_code)
        }
    }

    inner class ViewHolder(val binding: ItemTransactionBinding)
        : RecyclerView.ViewHolder(binding.root)
}