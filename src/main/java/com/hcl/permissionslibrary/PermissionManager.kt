package com.hcl.permissionslibrary
import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import java.io.Serializable
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import java.util.Collection


class PermissionManager:Activity(), IPermissionManager {
    var permissionsNeeded: ArrayList<String> = ArrayList()
    var askPermission: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
    }
    override fun requestPermissions() {

        if ((getIntent() != null && getIntent().getSerializableExtra(Constants.PERMISSION_LIST) != null)) {
            try {
                permissionsNeeded.addAll(getIntent().getSerializableExtra(Constants.PERMISSION_LIST) as Collection<out String>)
            } catch (e: Exception) {
                Toast.makeText(this, "Oops!, something went wrong", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
// checking for permission if not asking for permission
        if (permissionsNeeded.size > 0) {
            for (i in 0 until permissionsNeeded.size) {
                if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permissionsNeeded.get(i)) !== PackageManager.PERMISSION_GRANTED)) {
                    askPermission.add(permissionsNeeded.get(i))
                }
            }
        } else {
            finish()
            //onPermissionGranted(pemissionsNeeded)
            //TODO check whether finish is ending abruptly
        }

        if (askPermission != null && askPermission.size > 0) {
            ActivityCompat.requestPermissions(this, askPermission.toArray(arrayOfNulls<String>(askPermission.size)),
                Constants.PERMISSION_REQUEST_CODE)
        }
        else {
            val intent = Intent()
            intent.putExtra(Constants.IS_GOT_ALL_PERMISSION, true)
            setResult(Constants.INTENT_CODE, intent)
            finish()
        }
    }

  /*override fun onRequestPermissionsResult(requestCode:Int, @NonNull permissions:Array<String>, @NonNull grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.PERMISSION_REQUEST_CODE -> {
                var gotAllPermission = true
                if (permissionsNeeded.size > 0)
                {
                    val deniedPermissions = ArrayList<String>()
                    for (i in 0 until permissionsNeeded.size)
                    {
                        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (checkSelfPermission(permissionsNeeded.get(i)) !== PackageManager.PERMISSION_GRANTED)))
                        {
                            gotAllPermission = false
                            deniedPermissions.add(permissionsNeeded.get(i))
                        }
                    }
                    val intent = Intent()
                    intent.putExtra(Constants.IS_GOT_ALL_PERMISSION, gotAllPermission)
                    if (deniedPermissions.size > 0)
                    {
                        intent.putExtra(Constants.DENIED_PERMISSION_LIST,deniedPermissions as Serializable)
                    }
                    setResult(Constants.INTENT_CODE, intent)
                    finish()
                }
            }
        }
    }*/

    // handle permissions result
       override fun onRequestPermissionsResult(requestCode:Int, @NonNull permissions:Array<String>, @NonNull grantResults:IntArray)
        {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         when (requestCode) {
             Constants.PERMISSION_REQUEST_CODE -> {
                // var gotAllPermission = true
                 if (permissionsNeeded.size > 0)
                 {
                     for (i in 0 until permissionsNeeded.size)
                     {
                         //Assigning the permissions[i] to string of permission
                         //TODO to check why the string permission is not allowed in shouldShowRequestPermissionRationale(deniedpermission)
                         String deniedpermission = permissions[i]
                         if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (checkSelfPermission(permissionsNeeded.get(i)) !== PackageManager.PERMISSION_GRANTED)))
                         {
                             //passing the string permission
                             if (shouldShowRequestPermissionRationale(deniedpermission)) {
                                 showMessageOKCancel("You need to allow access to both the permissions",
                                     DialogInterface.OnClickListener { dialog, which ->
                                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                             ActivityCompat.requestPermissions(this, askPermission.toArray(arrayOfNulls<String>(askPermission.size)),Constants.PERMISSION_REQUEST_CODE)
                                         }
                                     })
                                 return
                             }

                         }
                     }

                 }
             }
         }
     }
    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}




