package com.hcl.permissionslibrary
// rename interface
interface IPermissionManagerCallback {
    fun onPermissionGranted(permission:String)
    fun onPermissionDenied(permission: String)
}
