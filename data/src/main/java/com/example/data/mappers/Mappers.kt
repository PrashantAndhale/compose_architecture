package com.example.data.mappers

import com.example.data.network.model.IssuesDTO
import com.example.data.network.model.NewsDetailsDTO
import com.example.data.network.model.NewsPaperDTO
import com.example.data.network.model.NewspapersDTO
import com.example.domain.model.Issues
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers


fun NewspapersDTO.toDomain(): Newspapers {
    return Newspapers(
        lccn = this.lccn, url = this.url, state = this.state, title = this.title
    )
}

fun NewsPaperDTO.toDomain(): List<Newspapers> {
    return this.newspapers.map { it.toDomain() }
}

// Convert NewsDetails DTO to Domain model
fun NewsDetailsDTO.toDomain(): NewsDetails {
    return NewsDetails(
        placeOfPublication = this.placeOfPublication,
        lccn = this.lccn,
        startYear = this.startYear,
        place = this.place,
        name = this.name,
        publisher = this.publisher,
        url = this.url,
        endYear = this.endYear,
        issues = this.issues.map { it.toDomain() }, // Map Issues DTO to Domain model
        subject = this.subject
    )
}

// Convert Issues DTO to Domain model
fun IssuesDTO.toDomain(): Issues {
    return Issues(
        url = this.url,
        dateIssued = this.dateIssued
    )
}



