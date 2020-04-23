package com.group7.unveil.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.group7.unveil.R

/**
 * @author M. Rose
 * Adaptor for the map recycler view
 */
class MapRecyclerAdaptor(private val buttons: List<MapRouteButtonModel>) : RecyclerView.Adapter<MapRecyclerAdaptor.ButtonViewHolder>() {
    /**
     * View holder for the button in the recycler view
     */
    class ButtonViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private var button: Button = viewItem.findViewById(R.id.routeButton)

        /**
         * Sets what the button actually does
         */
        fun setButtonResource(button: Button) {
            this.button.text = button.text
            this.button.setOnClickListener { button.callOnClick() }
        }
    }

    /**
     * Creates the view holder of the button to be added to the list
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.map_route_button, parent, false)
        return ButtonViewHolder(v)
    }

    /**
     * Number of buttons in the list
     */
    override fun getItemCount(): Int {
        return buttons.size
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val current = buttons[position]

        holder.setButtonResource(current.button)
    }
}