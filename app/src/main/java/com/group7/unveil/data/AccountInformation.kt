package com.group7.unveil.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * Google account information that can be accessed throughout the app
 * @author M. Rose
 */
object AccountInformation {
    var account: GoogleSignInAccount? = null

    fun getPhotoURI() = account!!.photoUrl
}