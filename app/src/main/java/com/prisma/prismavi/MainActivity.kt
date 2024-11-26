package com.prisma.prismavi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.prisma.prismavi.core.permissions.PermissionManager
import com.prisma.prismavi.ui.navigation.ViewManager
import com.prisma.prismavi.viewmodel.ViewModelManager

class MainActivity : ComponentActivity() {

    private lateinit var viewManager: ViewManager
    private lateinit var permissionManager: PermissionManager
    private lateinit var viewModelManager: ViewModelManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelManager = ViewModelManager(
            ViewModelProvider(
                ViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )
        )
        permissionManager = PermissionManager(this)
        viewManager = ViewManager(this, permissionManager, viewModelManager)

    }
}
