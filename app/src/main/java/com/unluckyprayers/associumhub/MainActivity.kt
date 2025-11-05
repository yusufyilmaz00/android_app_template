package com.unluckyprayers.associumhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unluckyprayers.associumhub.ui.components.AppNavigationDrawer
import com.unluckyprayers.associumhub.ui.components.BottomNavigationBar
import com.unluckyprayers.associumhub.ui.components.MainTopAppBar
import com.unluckyprayers.associumhub.ui.navigation.AppNavGraph
import com.unluckyprayers.associumhub.ui.theme.AssociumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssociumTheme(darkTheme = true) {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    // 1. Çekmecenin durumunu (açık/kapalı) yönetmek için bir state oluşturuluyor.
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // 2. Mevcut rotayı (route) takip etmek için backstack dinleniyor.
    // Bu, çekmecedeki seçili öğeyi vurgulamak için kullanılacak.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Çekmecenin içeriğini oluşturan Composable
            AppNavigationDrawer(
                navController = navController,
                drawerState = drawerState,
                currentRoute = currentRoute
            )
        }
    ) {
        // 4. Mevcut Scaffold yapısı, ModalNavigationDrawer içine alınıyor.
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            // 5. Sol üstteki menü ikonunu içeren üst bar ekleniyor.
            topBar = { MainTopAppBar(drawerState = drawerState) },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            AppNavGraph(
                navController = navController, // Buradaki "as NavHostController" cast'ına gerek kalmadı
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
