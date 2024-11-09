package com.example.reddit_top_publications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.LazyPagingItems
import com.example.reddit_top_publications.model.Repository
import com.example.reddit_top_publications.network.ApiService
import com.example.reddit_top_publications.ui.theme.ReddittoppublicationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ReddittoppublicationsTheme {
                RenderApp(AppContainer())
            }
        }
    }
}

@Composable
fun RenderApp(appContainer: AppContainer) {
    val apiService = appContainer.createService(ApiService::class.java)
    val repository = Repository(apiService)
    val viewModel: MainActivityViewModel = viewModel(factory = MainActivityViewModelFactory(repository))

    val topPublications = viewModel.topPublications.collectAsLazyPagingItems()

    LazyColumn {
        items(topPublications.itemCount) { index ->
            val publication = topPublications[index]
            publication?.let {
                Text(text = it.toString())
            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReddittoppublicationsTheme {
        Greeting("Android")
    }
}