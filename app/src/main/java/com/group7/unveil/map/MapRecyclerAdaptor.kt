package com.group7.unveil.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.group7.unveil.R

class MapRecyclerAdaptor(val buttons: List<MapRouteButtonLayoutHolder>) :
    RecyclerView.Adapter<MapRecyclerAdaptor.ButtonView>() {
    class ButtonView(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        var button: Button

        init {
            button = viewItem.findViewById(R.id.routeButton)
        }

        fun setButtonResource(button: Button) {
            this.button.text = button.text
            this.button.setOnClickListener { button.callOnClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonView {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.map_route_button, parent, false)
        val bv = ButtonView(v)

        return bv
    }

    override fun getItemCount(): Int {
        return buttons.size
    }

    override fun onBindViewHolder(holder: ButtonView, position: Int) {
        val current = buttons[position]

        holder.setButtonResource(current.button)
    }
}