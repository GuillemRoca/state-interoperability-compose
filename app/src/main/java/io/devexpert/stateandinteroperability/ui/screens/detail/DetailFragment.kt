package io.devexpert.stateandinteroperability.ui.screens.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.devexpert.stateandinteroperability.R

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productName = requireArguments().getString("productName")
        val title = view.findViewById<TextView>(R.id.title)
        title.text = productName
    }
}