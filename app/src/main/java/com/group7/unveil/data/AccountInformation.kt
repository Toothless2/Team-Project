package com.group7.unveil.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

object AccountInformation {
    var account: GoogleSignInAccount? = null

    fun getPhotoURI() = account!!.photoUrl
}