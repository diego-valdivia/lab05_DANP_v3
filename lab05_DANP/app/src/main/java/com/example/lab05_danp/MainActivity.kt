package com.example.lab05_danp

import android.annotation.SuppressLint
import android.os.Bundle

import android.util.Log
import android.view.ViewDebug.IntToString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.LectorFoco
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lab05_danp.ui.theme.Lab05_DANPTheme
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Amplify.addPlugin(AWSApiPlugin()) // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            Log.i("Amplify", "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e("Amplify", "Could not initialize Amplify", e)
        }

        setContent {
            AppNavigation()
            readAll()
            botones()
        }

    }
}

@Composable
fun botones(){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val padding = Modifier.padding(vertical = 8.dp)

            //CREATE
            Button(
                onClick = { create() },
                modifier = padding
            ) {
                Text(text = "Create")
            }

            //Read All
            Button(
                onClick = { readAll() },
                modifier = padding
            ) {
                Text(text = "Listar")
            }


                val viewModel = viewModel<MainViewModel>()
                val state = viewModel.state
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(state.items.size) { i ->
                        val item = state.items[i]
                        if( i >= state.items.size - 1 && !state.endReached && !state.isLoading){
                            viewModel.loadNextItems()
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = item.intensidad.toString(),
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(item.hora)
                            Text(item.fecha)
                        }
                    }
                    item {
                        if(state.isLoading) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }

        }
    }
}


private fun create(){
    val item: LectorFoco = LectorFoco.builder()
        .intensidad(1000)
        .hora(Temporal.Time("22:30"))
        .fecha(Temporal.Date("1910-01-01"))
        .build()
    Amplify.DataStore.save(
        item,
        { success -> Log.i("Amplify", "Saved item: " + success.item().intensidad) },
        { error -> Log.e("Amplify", "Could not save item to DataStore", error) }
    )
}


var intensidadA: Array<Int> = arrayOf()
var horas: Array<String> = arrayOf()
var fechas: Array<String> = arrayOf()


private fun readAll(){
    Amplify.DataStore.query(
        LectorFoco::class.java,
        { items ->
            while (items.hasNext()) {
                val item = items.next()
                Log.i("Amplify", "Queried item: " + item.id + "INTENSIDAD DEL FOCO" + item.intensidad)
            }
        },
        { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
    )
}



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("charts") {
            CombinedChartsExample()
        }
        composable("list") {
            ListScreen(navController)
        }

    }
}

@Composable
fun NavDrawerMenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.body1)

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFCBE8BA), // Start color (#CBE8BA)
            Color.White     // End color (#FFD54F)
        )
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Home Screen") },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Drawer")
                    }
                }
            )
        },
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = gradientBackground) // Apply gradient background
            ) {
                Column {
                    Text(
                        text = "User Name",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                    Divider()

                    NavDrawerMenuItem(
                        icon = Icons.Default.Home,
                        title = "Home",
                        onClick = {
                            navController.navigate("home")
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )

                    NavDrawerMenuItem(
                        icon = Icons.Default.BarChart,
                        title = "Ver Gráficos",
                        onClick = {
                            navController.navigate("charts")
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )

                    NavDrawerMenuItem(
                        icon = Icons.Default.List,
                        title = "Lista",
                        onClick = {
                            navController.navigate("list")
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )

                    NavDrawerMenuItem(
                        icon = Icons.Default.Settings,
                        title = "Settings",
                        onClick = {
                            navController.navigate("settings")
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Divider()

                    NavDrawerMenuItem(
                        icon = Icons.Default.ExitToApp,
                        title = "Log Out",
                        onClick = {
                            navController.navigate("login")
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "HOME", style = MaterialTheme.typography.h4)
        }
    }
}
