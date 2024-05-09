package com.example.androidproject.models.npm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NpmRegistryRoot(
    val objects: List<Object> = listOf(),
)
@Serializable
data class Object(
    @SerialName("package")
    val packageField: Package,
    val flags: Flags,
    val score: Score,
    val searchScore: Double,
)
@Serializable
data class Package(
    val name: String = "",
    val scope: String = "",
    val version: String = "",
    val description: String = "",
    val keywords: List<String> = listOf(),
    val date: String = "",
    val links: Links = Links(),
    val publisher: Publisher = Publisher(),
    val maintainers: List<Maintainer> = listOf(),
)
@Serializable
data class Links(
    val npm: String = "",
    val homepage: String = "",
    val repository: String = "",
    val bugs: String = "",
)
@Serializable
data class Publisher(
    val username: String = "",
    val email: String = "",
)

@Serializable
data class Maintainer(
    val username: String = "",
    val email: String = "",
)
@Serializable
data class Flags(
    val insecure: Long = 0,
)
@Serializable
data class Score(
    val final: Double = 0.0,
    val detail: Detail = Detail(),
)
@Serializable
data class Detail(
    val quality: Double = 0.0,
    val popularity: Double = 0.0,
    val maintenance: Double = 0.0,
)
