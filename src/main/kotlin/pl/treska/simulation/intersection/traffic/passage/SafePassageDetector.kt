package pl.treska.simulation.intersection.traffic.passage

import pl.treska.simulation.intersection.Vehicle

class SafePassageDetector(private val conflictStrategies: List<SafePassageStrategy>): SafePassageStrategy {
    override fun canPass(vehicleA: Vehicle, vehicleB: Vehicle): Boolean =
        conflictStrategies.any { it.canPass(vehicleA, vehicleB) }
}