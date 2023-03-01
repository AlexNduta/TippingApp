package com.example.tippingapp.utils

fun calculateTotaltip(totalBill: Double, tipPrcent:Int): Double {
    return  if (totalBill > 1 && totalBill.toString().isNotEmpty()){
        totalBill * tipPrcent/ 100
    }else 0.0

}

fun totalPerperson(
    total:Double,
    Split:Int,
    percentage: Int
): Double {
    val bill= calculateTotaltip(totalBill = total, tipPrcent = percentage)+ total
return bill/Split

}