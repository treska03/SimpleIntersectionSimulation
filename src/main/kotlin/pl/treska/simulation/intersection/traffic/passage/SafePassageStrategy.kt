package pl.treska.simulation.intersection.traffic.passage

import pl.treska.simulation.intersection.Vehicle

interface SafePassageStrategy {
    fun canPass(vehicleA: Vehicle, vehicleB: Vehicle): Boolean
}