package com.hcl.permissionslibrary

import android.app.Activity
import android.content.Intent
import androidx.annotation.NonNull
import java.io.Serializable

class PermissionsInit(@NonNull context: Activity, @NonNull permissions:List<String>):Intent() {
    init{
        val intent = Intent()
        intent.setClass(context, PermissionManager::class.java)
        intent.putExtra(constants.PERMISSION_LIST, permissions)
        context.startActivityForResult(intent, constants.INTENT_CODE)
    }
}