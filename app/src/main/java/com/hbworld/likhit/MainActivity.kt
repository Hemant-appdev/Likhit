package com.hbworld.likhit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hbworld.likhit.navigation.LikhitNavigation
import com.hbworld.likhit.ui.theme.LikhitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppView()
        }
    }
}

@Composable
fun MyAppView() {
    LikhitTheme {
        LikhitNavigation()
    }

}

@Composable
@Preview()
fun LikhitPreview() {
    MyAppView()
}