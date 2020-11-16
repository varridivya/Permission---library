package com.hcl.permissionslibrary
// rename interface
interface IPMCallback {
    fun onPermissionGranted(permission:String)
    fun onPermissionDenied(permission: String)
}