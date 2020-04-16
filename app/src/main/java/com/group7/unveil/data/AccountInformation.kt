package com.group7.unveil.data

import android.accounts.Account
import com.google.android.gms.auth.api.accounttransfer.AccountTransfer
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

object AccountInformation {
    var account: GoogleSignInAccount? = null

    fun getPhotoURI() = account!!.photoUrl
}