package com.hcl.permissionslibrary

import android.widget.Toast

class PermissionManagerCallback:IPermissionManagerCallback {
    override fun onPermissionGranted(permission: String) {
        Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(permission: String) {
        Toast.makeText(this,"permission denied\n"+ deniedPermissions.toString(),Toast.LENGTH_SHORT).show()
    }
}