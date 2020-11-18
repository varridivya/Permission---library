package com.hcl.permissionslibrary
import android.Manifest
import android.app.Activity
import android.app.PendingIntent.getActivity
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
import com.google.android.material.snackbar.Snackbar
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
            Toast.makeText(this,"permission Granted",Toast.LENGTH_SHORT).show()
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
    
    // handle permission result
   override fun onRequestPermissionsResult(requestCode:Int, @NonNull permissions:Array<String>, @NonNull grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.PERMISSION_REQUEST_CODE -> {
                //var gotAllPermission = false
                if (grantResults.size > 0 && permissions.size == grantResults.size) {
                    for (i in permissions.indices) {
                        var permission = permissions[i]
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, " Granted", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (checkSelfPermission(permissionsNeeded.get(i)) !== PackageManager.PERMISSION_GRANTED)))
                            {
                                //passing the string permission
                                if (shouldShowRequestPermissionRationale(permission))
                                {
                                    showMessageOKCancel("You need to allow access to both the permissions",
                                        DialogInterface.OnClickListener { dialog, which ->
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                            {
                                                ActivityCompat.requestPermissions(this, askPermission.toArray(arrayOfNulls<String>(askPermission.size)), Constants.PERMISSION_REQUEST_CODE)
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




