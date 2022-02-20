package com.example.application.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchModel(
    @Json(name = "Response")
    val response: String,
    @Json(name = "Search")
    val search: List<Search>,
    @Json(name = "totalResults")
    val totalResults: String
)

@JsonClass(generateAdapter = true)
data class Search(
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "imdbID")
    val imdbID: String
)

@JsonClass(generateAdapter = true)
data class DetailsModel(
    @Json(name = "Actors")
    val actors: String,
    @Json(name = "Awards")
    val awards: String,
    @Json(name = "BoxOffice")
    val boxOffice: String,
    @Json(name = "Country")
    val country: String,
    @Json(name = "DVD")
    val dvd: String,
    @Json(name = "Director")
    val director: String,
    @Json(name = "Genre")
    val genre: String,
    @Json(name = "Language")
    val language: String,
    @Json(name = "MetaScore")
    val metaScore: String,
    @Json(name = "Plot")
    val plot: String,
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Production")
    val production: String,
    @Json(name = "Rated")
    val rated: String,
    @Json(name = "Ratings")
    val ratings: List<Rating>,
    @Json(name = "Released")
    val released: String,
    @Json(name = "Response")
    val response: String,
    @Json(name = "Runtime")
    val runtime: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Website")
    val website: String,
    @Json(name = "Writer")
    val writer: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "imdbID")
    val imdbID: String,
    @Json(name = "imdbRating")
    val imdbRating: String,
    @Json(name = "imdbVotes")
    val imdbVotes: String
)

@JsonClass(generateAdapter = true)
data class Rating(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
)