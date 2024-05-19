package ua.kpi.its.lab.security.dto

import kotlinx.serialization.Serializable
@Serializable
data class TrainRequest(
    var brand: String,
    var model: String,
    var manufacturer: String,
    var manufactureDate: String,
    var maxSpeed: Double,
    var price: String,
    var isABS: Boolean,
    var battery: RouteRequest
)

@Serializable
data class TrainResponse(
    var id: Long,
    var brand: String,
    var model: String,
    var manufacturer: String,
    var manufactureDate: String,
    var maxSpeed: Double,
    var price: String,
    var isABS: Boolean,
    var battery: RouteResponse
)

@Serializable
data class RouteRequest(
    var model: String,
    var manufacturer: String,
    var type: String,
    var capacity: Int,
    var manufactureDate: String,
    var chargeTime: Double,
    var isFastCharge: Boolean
)

@Serializable
data class RouteResponse(
    var id: Long,
    var model: String,
    var manufacturer: String,
    var type: String,
    var capacity: Int,
    var manufactureDate: String,
    var chargeTime: Double,
    var isFastCharge: Boolean
)