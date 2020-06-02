package com.gmail.goyter012.labs_realtimesys_android.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.gmail.goyter012.labs_realtimesys_android.R

class ThirdFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_third, container, false)
        val layout = view.findViewById<LinearLayout>(R.id.third_layout)

        layout.background = ResourcesCompat.getDrawable(resources, R.drawable.wallpaper3, null)


        return view
    }


    companion object {
        fun newInstance(): ThirdFragment = ThirdFragment()
    }


}