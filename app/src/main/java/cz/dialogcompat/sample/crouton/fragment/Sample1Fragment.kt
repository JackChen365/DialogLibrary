package cz.dialogcompat.sample.crouton.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.dialogcompat.library.crouton

import cz.dialogcompat.library.crouton.Crouton
import cz.dialogcompat.sample.R

/**
 * Created by cz on 11/23/16.
 */

class Sample1Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById(R.id.btn_show).setOnClickListener {
            crouton(R.id.tv_text) { text=getString(R.string.refresh_complete) }
        }
    }
}
