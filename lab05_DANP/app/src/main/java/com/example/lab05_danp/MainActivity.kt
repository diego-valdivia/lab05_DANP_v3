package com.example.lab05_danp

import android.os.Bundle

import android.util.Log
import android.view.ViewDebug.IntToString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.lab05_danp.ui.theme.Lab05_DANPTheme
import java.lang.Thread.sleep


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
        .intensidad(5)
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
                intensidadA += item.intensidad
                horas += item.hora.toString()
                fechas += item.fecha.toString()

            }
        },
        { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
    )


}