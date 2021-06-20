package com.example.weatherdetecte.logic.model

import java.io.Serializable

data class WeatherCollection(
    val cwbopendata: Cwbopendata
)

data class Cwbopendata(
    val version: String,
    val dataid: String,
    val dataset: Dataset,
    val identifier: String,
    val msgType: String,
    val scope: String,
    val sender: String,
    val sent: String,
    val source: String,
    val status: String
)

data class Dataset(
    val datasetInfo: DatasetInfo,
    val location: List<Location>
)

data class DatasetInfo(
    val datasetDescription: String,
    val issueTime: String,
    val update: String
)

data class Location(
    val locationName: String,
    val weatherElement: List<WeatherElement>
):Serializable

data class WeatherElement(
    val elementName: String,
    val time: List<Time>
):Serializable

data class Time(
    val endTime: String,
    val parameter: Parameter,
    val startTime: String
):Serializable

data class Parameter(
    val parameterName: String,
    val parameterUnit: String,
    val parameterValue: String
):Serializable