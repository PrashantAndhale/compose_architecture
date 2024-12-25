package com.example.data.mappers

 import com.example.data.network.model.MoviesItemDTO
import com.example.domain.model.MoviesItem

fun List<MoviesItemDTO>.toDomain(): List<MoviesItem> {
    return map {
        MoviesItem(
            id = it.id, image = it.image,
            imdb_url = it.imdb_url,
            movie = it.movie,
            rating = it.rating
        )

    }
}

fun MoviesItemDTO.toDomain(): MoviesItem {
    return MoviesItem(
        id = this.id,
        image = this.image,
        imdb_url = this.imdb_url,
        movie = this.movie,
        rating = this.rating
    )
}