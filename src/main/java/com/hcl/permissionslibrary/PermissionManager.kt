package com.hcl.permissionslibrary
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import java.io.Serializable
import java.util.*
import java.util.Collection
import kotlin.collections.ArrayList


class PermissionManager:Activity(), IPermissionManager {
    var permissionsNeeded: ArrayList<String> = ArrayList()
    var askPermission: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
    }
    override fun requestPermissions() {
//TODO need to check catch block is necessary or not
        if ((getIntent() != null && getIntent().getSerializableExtra(constants.PERMISSION_LIST) != null)) {
            try {
                permissionsNeeded.addAll(getIntent().getSerializableExtra(constants.PERMISSION_LIST) as Collection<out String>)
            } catch (e: Exception) {
                Toast.makeText(this, "Oops!, something went wrong", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        if (permissionsNeeded.size > 0) {
            for (i in 0 until permissionsNeeded.size) {
                if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permissionsNeeded.get(i)) !== PackageManager.PERMISSION_GRANTED)
                ) {
                    askPermission.add(permissionsNeeded.get(i))
                }
            }
        } else {
            finish()
            //TODO check whether finish is ending abruptly
        }

        if (askPermission != null && askPermission.size > 0) {
            ActivityCompat.requestPermissions(this, askPermission.toArray(arrayOfNulls<String>(askPermission.size)),
                constants.PERMISSION_REQUEST_CODE
            )
        } else {
            val intent = Intent()
            intent.putExtra(constants.IS_GOT_ALL_PERMISSION, true)
            setResult(constants.INTENT_CODE, intent)
            finish()
        }
    }

    }





