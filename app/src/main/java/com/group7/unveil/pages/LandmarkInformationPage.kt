package com.group7.unveil.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.group7.unveil.R
import com.group7.unveil.landmarks.Landmark
import kotlinx.android.synthetic.main.landmark_information_fragment.*

/**
 * Fills out information the "more landmark info" page
 * @author M. Rose
 */
class LandmarkInformationPage(private val landmark : Landmark) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.landmark_information_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        landmarkInformationTitle.text = landmark.name
        landmarkInformationDescription.text = landmark.descriptor
        landmarkInformationVisited.text = resources.getString(landmark.visited.takeIf { it }?.let {R.string.landmarkVisitedText }?:R.string.landmarkNotVisitedText)
    }
}
