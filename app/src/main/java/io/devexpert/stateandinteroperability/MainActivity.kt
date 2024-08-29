package io.devexpert.stateandinteroperability

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import io.devexpert.stateandinteroperability.ui.screens.detail.DetailScreen

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /*supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, HomeFragment())
            .commit()*/

        setContent {
            DetailScreen()
        }
    }
}