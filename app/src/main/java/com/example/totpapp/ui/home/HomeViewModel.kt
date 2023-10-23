package com.example.totpapp.ui.home

import androidx.lifecycle.ViewModel

class HomeViewModel( orgV: String,  uV: String,  otpV: String,  pV: Int) : ViewModel() {

    private var orgValue: String
    private var userValue: String
    private var otpValue: String
    private var progressValue: Int

    init {
        orgValue = orgV
        userValue = uV
        otpValue = otpV
        progressValue = pV
    }

    fun getOrgValue(): String {
        return orgValue
    }
    fun getUserValue(): String {
        return userValue
    }
    fun getOtpValue(): String {
        return otpValue
    }
    fun getProgressValue(): Int {
        return progressValue
    }


    fun setOrgValue(newOrg: String){
        orgValue = newOrg
    }
    fun setUserValue(newUser: String){
        userValue = newUser
    }
    fun setOtpValue(newOtp: String){
        otpValue = newOtp
    }
    fun setProgressValue(newProgress: Int){
        progressValue = newProgress
    }

}