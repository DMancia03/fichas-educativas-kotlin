package sv.edu.udb.fichaseducativas.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ModalDrawer(
    navHostController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    currentRoute : String
) {
    ModalDrawerSheet {
        Text(
            text = NavigationStrings.NombreApp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(all = 10.dp),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary
        )

        ItemMenu.ItemsMenu.forEach {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = it.Nombre
                    )
                },
                icon = {
                    Icon(
                        it.Icon,
                        contentDescription = it.IconDescription
                    )
                },
                selected = currentRoute == it.Route,
                onClick = {
                    navHostController.navigate(it.Route)

                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    }
}