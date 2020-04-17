package com.group7.unveil.pages.alerts

import android.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.group7.unveil.R

class ToursAlert(val view: View) {


    private var dialog: AlertDialog? = null

    private fun openDialog(parent: Fragment) {
        if (dialog != null && dialog?.isShowing!!)
            return

        val builder = AlertDialog.Builder(parent.context)
        val info = parent.layoutInflater.inflate(R.layout.popular_tours_button, parent.view as ViewGroup, false)

        val d = ToursAlert(info)
        builder.setView(info)

    }
}