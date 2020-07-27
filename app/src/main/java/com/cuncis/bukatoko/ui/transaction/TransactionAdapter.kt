package com.cuncis.bukatoko.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Transaction
import com.cuncis.bukatoko.databinding.ItemTransactionBinding
import com.cuncis.bukatoko.util.Utils.Companion.hideView

class TransactionAdapter: RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    var onUploadClick: ((String?) -> Unit)? = null

    private val transactionList = arrayListOf<Transaction.Response.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = transactionList.size

    fun submitList(newList: List<Transaction.Response.Data>) {
        transactionList.clear()
        transactionList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.transaction = transactionList[position]
        holder.binding.btnUpload.setOnClickListener {
            onUploadClick?.invoke(transactionList[position].transaction_code)
        }
    }

    inner class ViewHolder(val binding: ItemTransactionBinding)
        : RecyclerView.ViewHolder(binding.root)
}