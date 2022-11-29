package com.inhaproject.karaoke3.ui.mypage.coin

data class BalanceData(
    val availableBalance: Int,
    val ownerId: String,
    val stakeList: StakeList,
    val stakedBalance: Int
)