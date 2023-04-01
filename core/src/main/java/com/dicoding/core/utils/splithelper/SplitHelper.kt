package com.dicoding.core.utils.splithelper

import android.content.Context
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

object SplitHelper {

    fun installModule(context: Context, module: String): Boolean {
        var result = false
        val splitInstallManager = SplitInstallManagerFactory.create(context)
        if (splitInstallManager.installedModules.contains(module)) {
            Toast.makeText(context, "Open module $module", Toast.LENGTH_SHORT).show()
            result = true
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(module)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(context, "Success installing module", Toast.LENGTH_SHORT).show()
                    result = true
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
        return result
    }

}