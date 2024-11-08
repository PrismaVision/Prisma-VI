package com.prisma.prismavi

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.prisma.prismavi.ui.navigation.ViewManager
import com.prisma.prismavi.core.permissions.PermissionManager
import com.prisma.prismavi.ui.navigation.Screen

class MainActivity : ComponentActivity() {

    private lateinit var viewManager: ViewManager
    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o PermissionManager e o ViewManager
        permissionManager = PermissionManager(this)
        viewManager = ViewManager(this, permissionManager)

    }
}
