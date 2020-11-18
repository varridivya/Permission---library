package com.hcl.permissionslibrary

import android.widget.Toast

class PermissionManagerCallback:IPermissionManagerCallback {
    override fun onPermissionGranted(permission: String) {
        Toast.makeText(getContext(),"Permission Granted",Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(permission: String) {
        Toast.makeText(this,"permission denied\n"+ permission.toString(),Toast.LENGTH_SHORT).show()
    }
}