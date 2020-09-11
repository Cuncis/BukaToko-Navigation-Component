package com.cuncis.bukatoko.ui.base64

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Base64Data
import kotlinx.android.synthetic.main.item_base64.view.*

class Base64Adapter : RecyclerView.Adapter<Base64Adapter.ViewHolder>() {

    private var base64List = arrayListOf<Base64Data.Response.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_base64, parent, false)
        )
    }

    override fun getItemCount(): Int = base64List.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(base64List[position])
    }

    fun submitList(newList: List<Base64Data.Response.Data>) {
        base64List.clear()
        base64List.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        private val tvJudul: TextView = view.tv_judul
        private val tvCatatan: TextView = view.tv_catatan
        private val imgPoster: ImageView = view.img_poster

        fun bind(data: Base64Data.Response.Data) {
            tvJudul.text = data.judul
            tvCatatan.text = data.catatan
            Glide.with(view.context)
                .load(data.file)
                .into(imgPoster)
        }

    }
}