package com.hcl.permissionslibrary
interface IPermissionManagerCallback {
    fun onPermissionGranted(permission:String)
    fun onPermissionDenied(permission: String)
}
