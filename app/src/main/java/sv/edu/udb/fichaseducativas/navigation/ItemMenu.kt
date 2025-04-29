package sv.edu.udb.fichaseducativas.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

class ItemMenu {
    lateinit var Nombre : String
    lateinit var Icon : ImageVector
    lateinit var IconDescription : String
    lateinit var Route : String

    constructor(_nombre : String, _icon : ImageVector, _iconDescription : String, _route : String){
        Nombre = _nombre
        Icon = _icon
        IconDescription = _iconDescription
        Route = _route
    }

    companion object{
        val ItemsMenu : List<ItemMenu> = listOf(
            ItemMenu(
                NavigationStrings.ItemMenuTituloInicio,
                Icons.Filled.Home,
                NavigationStrings.ItemMenuTituloInicio,
                NavigationStrings.ItemMenuRouteInicio
            ),
            ItemMenu(
                NavigationStrings.ItemMenuTituloTematicas,
                Icons.Filled.Create,
                NavigationStrings.ItemMenuTituloTematicas,
                NavigationStrings.ItemMenuRouteTematicas
            )
        )
    }
}