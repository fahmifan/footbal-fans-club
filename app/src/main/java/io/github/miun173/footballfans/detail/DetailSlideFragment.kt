package io.github.miun173.footballfans.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.miun173.footballfans.R

class DetailSlideFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return  inflater.inflate(R.layout.fragment_detail_slider, container, false)
    }
}
