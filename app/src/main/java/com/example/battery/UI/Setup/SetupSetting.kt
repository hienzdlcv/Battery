package com.example.battery.UI.Setup

import com.example.battery.Constants.Constants


data class SetupSetting(
	var id: String = "",
	var duration: Int = Constants.DURATION_5,
	var closingMethod: Int = Constants.SINGLE_TAP,
	var hasSound: Boolean = true,
)
