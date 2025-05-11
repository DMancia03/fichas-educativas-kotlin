package sv.edu.udb.fichaseducativas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import sv.edu.udb.fichaseducativas.ui.theme.FichasEducativasTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sv.edu.udb.fichaseducativas.components.ScreenFichas
import sv.edu.udb.fichaseducativas.components.ScreenFichasForm
import sv.edu.udb.fichaseducativas.components.ScreenInicio
import sv.edu.udb.fichaseducativas.components.ScreenRevisarFichas
import sv.edu.udb.fichaseducativas.components.ScreenTematica
import sv.edu.udb.fichaseducativas.navigation.ModalDrawer
import sv.edu.udb.fichaseducativas.navigation.NavigationStrings
import sv.edu.udb.fichaseducativas.navigation.TopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FichasEducativasTheme {
                Surface {
                    MainComponent()
                }
            }
        }
    }
}

@Composable
fun MainComponent(
    modifier: Modifier = Modifier
) {
    val navHostController : NavHostController = rememberNavController()

    val drawerState : DrawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope : CoroutineScope = rememberCoroutineScope()

    val currentNavBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute : String = currentNavBackStackEntry?.destination?.route ?: NavigationStrings.ItemMenuRouteInicio

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawer(
                navHostController,
                drawerState,
                scope,
                currentRoute
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    drawerState,
                    scope,
                    currentRoute
                )
            }
        ) { innerPadding ->

            NavHost (
                navController = navHostController,
                startDestination = NavigationStrings.ItemMenuRouteInicio,
                modifier = Modifier.padding(innerPadding).padding(all = 10.dp)
            ){
                composable(
                    "${NavigationStrings.ItemMenuRouteInicio}"
                ){
                    ScreenInicio(
                        modifier = modifier,
                        navHostController = navHostController
                    )
                }

                composable(
                    "${NavigationStrings.ItemMenuRouteTematicas}"
                ){
                    ScreenTematica(
                        modifier = modifier,
                        navHostController = navHostController
                    )
                }

                composable(
                    route = "${NavigationStrings.ItemMenuRouteFichas}?idTematica={idTematica}",
                    arguments = listOf(
                        navArgument("idTematica"){
                            type = NavType.IntType
                            defaultValue = 0
                        }
                    )
                ){
                    ScreenFichas(
                        modifier = modifier,
                        navHostController = navHostController
                    )
                }

                composable(
                    route = "${NavigationStrings.ItemMenuRouteFichasRevisar}?idTematica={idTematica}",
                    arguments = listOf(
                        navArgument("idTematica"){
                            type = NavType.IntType
                            defaultValue = 0
                        }
                    )
                ){
                    ScreenRevisarFichas(
                        modifier = modifier,
                        navHostController = navHostController
                    )
                }

                composable(
                    route = "${NavigationStrings.ItemMenuRouteFichasForm}?action={action}&idTematica={idTematica}&id={id}",
                    arguments = listOf(
                        navArgument("action"){
                            type = NavType.StringType
                            defaultValue = NavigationStrings.ActionCreate
                        },
                        navArgument("idTematica"){
                            type = NavType.IntType
                            defaultValue = 0
                        },
                        navArgument("id"){
                            type = NavType.IntType
                            defaultValue = 0
                        },
                    )
                ){
                    ScreenFichasForm(
                        modifier = modifier,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainComponentPreview() {
    FichasEducativasTheme {
        Surface {
            MainComponent()
        }
    }
}