package com.jetpacket.paging.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.jetpacket.paging.R
import com.jetpacket.paging.base.DataBindingViewHolder
import com.jetpacket.paging.databinding.LayoutProjectListItemBinding
import com.jetpacket.paging.model.DataX

/**
 * @author zhangshuai
 * @date 2022/5/3 星期二
 * @email zhangshuai@dushu365.com
 * @description
 */
class ProjectListAdapter :
    PagingDataAdapter<DataX, DataBindingViewHolder<LayoutProjectListItemBinding>>(
        diffCallback = object :
            DiffUtil.ItemCallback<DataX>() {

            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                oldItem == newItem

        }
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<LayoutProjectListItemBinding> {
//        val itemViewBinding = LayoutProjectListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataBindingViewHolder.create(R.layout.layout_project_list_item, parent)
    }


    override fun onBindViewHolder(
        holder: DataBindingViewHolder<LayoutProjectListItemBinding>,
        position: Int
    ) {
        val data = getItem(position)
        data?.let {
            val binding = holder.mBinding
            binding.data = it
            binding.networkImage = it.envelopePic
        }
    }
}