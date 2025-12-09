package com.unluckyprayers.associumhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unluckyprayers.associumhub.data.local.model.UserState
import com.unluckyprayers.associumhub.domain.viewmodel.AppViewModel
import com.unluckyprayers.associumhub.ui.components.AppNavigationDrawer
import com.unluckyprayers.associumhub.ui.components.BottomNavigationBar
import com.unluckyprayers.associumhub.ui.components.MainTopAppBar
import com.unluckyprayers.associumhub.ui.navigation.AppNavGraph
import com.unluckyprayers.associumhub.ui.theme.AssociumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Aktivite seviyesinde AppViewModel'i alıyoruz
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState by appViewModel.uiState.collectAsStateWithLifecycle()
            val authState by appViewModel.authState.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                appViewModel.checkUserSession(this@MainActivity)
            }

            AssociumTheme(darkTheme = true) {
                Box(modifier = Modifier.fillMaxSize()) {
                    MainScreen(
                        navController = navController,
                        // AppViewModel'deki fonksiyonları doğrudan lambda olarak iletiyoruz
                        authState = authState,
                        onShowAppLoading = appViewModel::showAppLoading,
                        onChangeLanguage = { langCode ->
                            println("DEBUG: onChangeLanguage MainActivity'de çağrıldı - langCode: $langCode")
                            val currentLocale = AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()
                            println("DEBUG: Mevcut locale: $currentLocale")

                            val appLocale = LocaleListCompat.forLanguageTags(langCode)
                            println("DEBUG: Yeni locale ayarlanıyor: $langCode")
                            AppCompatDelegate.setApplicationLocales(appLocale)
                            println("DEBUG: setApplicationLocales çağrıldı")
                        }
                    )

                    // Yükleme ekranı (Tüm UI'ın üzerinde, merkezi kontrol)
                    AnimatedVisibility(
                        visible = appState.isAppLoading || authState is UserState.Loading,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                                .clickable(enabled = false, onClick = {}),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun MainScreen(
    navController: NavController,
    authState: UserState,
    onShowAppLoading: (Boolean) -> Unit,
    onChangeLanguage: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavigationDrawer(
                navController = navController,
                drawerState = drawerState,
                currentRoute = currentRoute
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MainTopAppBar(drawerState = drawerState) },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            AppNavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                authState = authState,
                onShowAppLoading = onShowAppLoading,
                onChangeLanguage = onChangeLanguage
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
