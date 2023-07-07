package com.abufoda.quotegenerator.adapter

import com.abufoda.quotegenerator.model.entity.MyDataItem

interface OnClick {
    fun onClick(quote: MyDataItem)
}