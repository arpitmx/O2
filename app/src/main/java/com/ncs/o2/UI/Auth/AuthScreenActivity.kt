package com.ncs.o2.UI.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ncs.o2.Domain.Utility.Issue
import com.ncs.o2.R
import com.ncs.o2.UI.Auth.ChooserScreen.ChooserFragment
import com.ncs.o2.UI.MainActivity
import com.ncs.o2.databinding.ActivityAuthScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthScreenActivity @Inject constructor() : AppCompatActivity() {

    private val binding: ActivityAuthScreenBinding by lazy {
        ActivityAuthScreenBinding.inflate(layoutInflater)
    }


    @Issue("Fragment duplicate on configuration change, implement that.")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpViews()

    }

    private fun setUpViews() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}