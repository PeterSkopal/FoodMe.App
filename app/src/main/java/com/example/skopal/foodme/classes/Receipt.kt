package com.example.skopal.foodme.classes

import com.google.gson.annotations.SerializedName

data class Receipt(
        @SerializedName("totalAmount") val totalAmount: TotalAmount,
        @SerializedName("lineAmounts") val lineAmounts: List<LineAmount>,
        @SerializedName("date") val date: ReceiptDate
)

data class LineAmount(
        @SerializedName("description") val description: String,
        @SerializedName("data") val data: Double,
        @SerializedName("index") val index: Number,
        @SerializedName("text") val text: String,
        @SerializedName("include") private var _include: Boolean? = true
) {
    var include
        get() = _include ?: true
        set(value) {
            _include = value
        }
}

data class ReceiptDate(
        @SerializedName("data") val data: String,
        @SerializedName("text") val text: String
)

data class TotalAmount(
        @SerializedName("index") val index: Number,
        @SerializedName("data") val data: Double,
        @SerializedName("text") val text: String
)
