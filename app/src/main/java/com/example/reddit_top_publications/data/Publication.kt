package com.example.reddit_top_publications.data
import kotlinx.serialization.Serializable

@Serializable
data class Listing(
    val kind: String? = null,
    val data: ListingData = ListingData(null, 0, emptyList())
)

@Serializable
data class ListingData(
    val after: String?,
    val dist: Int,
    val children: List<Child>
)

@Serializable
data class Child(
    val kind: String? = null,
    val data: PublicationData = PublicationData("", "", null, 0.0, 0)
)

@Serializable
data class PublicationData(
    val subreddit: String,
    val author: String,
    var thumbnail: String? = null,
    val created: Double,
    val num_comments: Int,
    var url_overridden_by_dest: String? = null
)
