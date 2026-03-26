package com.example.cairometro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cairometroapp.presentation.ui.navigation.NavGraph
import com.example.cairometroapp.presentation.ui.theme.CairoMetroAppTheme
import data.datasource.MetroJsonDataSource
import data.repositoryImp.MetroRepositoryImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        // Setup Real Repository with JSON DataSource
        val dataSource = MetroJsonDataSource(this)
        val repository = MetroRepositoryImpl(dataSource)


        setContent {
            CairoMetroAppTheme {
                NavGraph(repository = repository)
            }
        }
    }
}
