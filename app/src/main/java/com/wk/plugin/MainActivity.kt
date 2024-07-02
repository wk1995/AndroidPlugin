package com.wk.plugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.entertech.affective.sdk.api.IAffectiveDataAnalysisService
import cn.entertech.affective.sdk.bean.AffectiveServiceWay

class MainActivity : AppCompatActivity() {
    private val affectiveService by lazy {
        IAffectiveDataAnalysisService.getService(AffectiveServiceWay.AffectiveLocalService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "affectiveService: ${affectiveService?.hasConnectAffectiveService()}")
    }
}