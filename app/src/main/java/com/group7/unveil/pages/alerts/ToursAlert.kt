package com.group7.unveil.pages.alerts

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.group7.unveil.R
import com.group7.unveil.data.Route
import com.group7.unveil.data.Routes
import com.group7.unveil.data.SelectedRouteFromHome
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.MapSelectedEventData
import kotlinx.android.synthetic.main.popular_tours_button.view.*
/**
 * Tours Alert class
 * @author Eldar Verdi
*/

class ToursAlert(val view: View, val route: Route) {

    companion object {

        private var dialog: AlertDialog? = null

        fun dismissDialog() {
            dialog?.dismiss()
        }


        fun openDialog(parent: Fragment, route: Route) {
            if (dialog != null && dialog?.isShowing!!)
                return

            val builder = AlertDialog.Builder(parent.context)
            val info = parent.layoutInflater.inflate(R.layout.popular_tours_button,parent.view as ViewGroup, false)

            val d = ToursAlert(info, route)
            d.buttonBehaviour()
            builder.setView(info)


            dialog = builder.create()
            dialog?.show()

        }
    }

    private fun buttonBehaviour() {
        view.routeTitle.text = route.getName()
        view.routeDesc.text = route.description
        view.tourButton.setOnClickListener() {
            EventBus.changeToMap(MapSelectedEventData(route))
            dismissDialog()
        }
    }
}