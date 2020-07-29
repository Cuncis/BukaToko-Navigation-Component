package com.cuncis.bukatoko.ui.transaction.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Transaction
import com.cuncis.bukatoko.databinding.ItemTransactionBinding

class PaidAdapter: RecyclerView.Adapter<PaidAdapter.ViewHolder>() {

    var onUploadClick: ((String?) -> Unit)? = null

    private val paidList = arrayListOf<Transaction.Response.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = paidList.size

    fun submitPaidList(newList: List<Transaction.Response.Data>) {
        paidList.clear()
        paidList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.transaction = paidList[position]
        holder.binding.btnUpload.setOnClickListener {
            onUploadClick?.invoke(paidList[position].transaction_code)
        }
    }

    inner class ViewHolder(val binding: ItemTransactionBinding)
        : RecyclerView.ViewHolder(binding.root)

}