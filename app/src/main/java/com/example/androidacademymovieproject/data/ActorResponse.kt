package com.example.androidacademymovieproject.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorResponse(

    val id: Int,
    val cast: List <JsonActor>,
    val crew: List<Crew>
)

@Serializable
data class Crew (

    var adult : Boolean,
    var gender : Int,
    var id : Int,
    @SerialName("known_for_department") var knownForDepartment : String,
    @SerialName("name") var name : String,
    @SerialName("original_name") var originalName : String,
    @SerialName("popularity") var popularity : Double,
    @SerialName("profile_path") var profilePath : String?,
    @SerialName("credit_id") var creditId : String,
    var department : String,
    var job : String

)