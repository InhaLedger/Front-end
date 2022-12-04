package com.inhaproject.karaoke3.ui.mypage.coin

data class BalanceData(
    val availableBalance: Double,
    val ownerId: String,
    val stakeList: Map<Long ,DepositData>,
    val stakedBalance: Double
)