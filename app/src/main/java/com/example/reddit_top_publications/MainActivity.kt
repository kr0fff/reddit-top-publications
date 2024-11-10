package com.example.reddit_top_publications

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.reddit_top_publications.data.Child
import com.example.reddit_top_publications.data.PublicationData
import com.example.reddit_top_publications.model.Repository
import com.example.reddit_top_publications.network.ApiService
import com.example.reddit_top_publications.ui.theme.ReddittoppublicationsTheme
import com.example.reddit_top_publications.utils.toRelativeTime

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
    val viewModel: MainActivityViewModel =
        viewModel(factory = MainActivityViewModelFactory(repository))
    val context = LocalContext.current
    val topPublications = viewModel.topPublications.collectAsLazyPagingItems()
    Scaffold { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(topPublications.itemCount) { index ->
                val publication = topPublications[index]
                publication?.let {
                    PublicationCard(publication.data, context)
                }
            }
        }
    }

}

@Composable
fun PublicationCard(item: PublicationData, context: Context) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Log.d("ICON_RESPONSE", item.thumbnail.toString() ?: "No thumbnail")
            val painter =
                if (item.thumbnail != null && item.thumbnail.contains("thumbs.redditmedia.com")) {
                    rememberAsyncImagePainter(model = item.thumbnail)
                } else {
                    painterResource(id = R.drawable.baseline_image_24)
                }
            Image(
                painter = painter,
                contentDescription = "Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(80.dp)
                    .clickable {
                        item.url_overridden_by_dest?.let { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                    }
            )
            Column(
                modifier = Modifier
                    .height(80.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Label("Author", item.author)
                Label("Comments", item.num_comments.toString())
                Label("Created", item.created.toRelativeTime())
            }
        }
    }
}

@Composable
fun Label(prefix: String, text: String) {
    Text(
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ), text = "$prefix: $text"
    )
}



