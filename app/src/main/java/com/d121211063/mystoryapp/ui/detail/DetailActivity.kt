package com.d121211063.mystoryapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.d121211063.mystoryapp.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    companion object {
        var EXTRA_IMAGE = "extra_image"
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
        var EXTRA_LATITUDE = "extra_latitude"
        var EXTRA_LONGITUDE = "extra_longitude"
        var EXTRA_DATE = "extra_date"
    }
}