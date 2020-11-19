package com.hcl.testdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.hcl.permissionslibrary.Constants
import com.hcl.permissionslibrary.PermissionsInit
import com.hcl.permissionslibrary.PermissionsList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btPermission).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var permission = ArrayList<String>()
                permission.add(PermissionsList.READ_EXTERNAL_STORAGE)
                permission.add(PermissionsList.ACCESS_FINE_LOCATION)
                if (permission.size > 0) {
                    PermissionsInit(this@MainActivity, permission)
                }

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.INTENT_CODE ->
                if (data != null)
                {
                    val isGotAllPermissions = data.getBooleanExtra(Constants.IS_GOT_ALL_PERMISSION, false)
                    if (data.hasExtra(Constants.IS_GOT_ALL_PERMISSION))
                    {
                        if (isGotAllPermissions)
                        {
                            Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            Toast.makeText(this, "All permission not Granted", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}
