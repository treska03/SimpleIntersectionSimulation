package pl.treska.simulation.intersection.traffic

import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.Road
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.intersection.Vehicle
import pl.treska.simulation.intersection.traffic.passage.SafePassageDetector

class PassableVehicleFinder(private val safePassageDetector: SafePassageDetector) {

    fun findVehiclesToMove(intersection: Intersection, currentPriorityIndex: Int): Pair<Int, List<Vehicle>> {
        val positions = RoadPosition.entries

        for (i in positions.indices) {
            val index = (currentPriorityIndex + i) % positions.size
            val road = intersection.getRoad(positions[index])

            // If there's vehicle on the road -> return vehicles that can pass with it
            if (!road.isEmpty()) {
                return Pair(index, findNonConflictingListOfVehicles(allRoads = intersection.roads, startRoad = road))
            }
        }
        // If no vehicles are on the intersection -> return
        return Pair(currentPriorityIndex, emptyList())
    }


    private fun findNonConflictingListOfVehicles(allRoads: List<Road>, startRoad: Road): List<Vehicle> {
        val startVehicle = startRoad.firstVehicle()!!

        val candidateMoves = mutableListOf<Vehicle>()
        candidateMoves.add(startVehicle)

        // Check other roads if we can add them without conflict
        val candidateRoads = allRoads.filter { it != startRoad }
            .filter { !it.isEmpty() }

        candidateRoads.forEach {
            val vehicle = it.firstVehicle()!!
            if (newVehicleIsOkay(candidateMoves, vehicle)) {
                candidateMoves.add(vehicle)
            }
        }

        return candidateMoves
    }

    private fun newVehicleIsOkay(selected: List<Vehicle>, newVehicle: Vehicle): Boolean =
        selected.all { canPass(it, newVehicle) }

    private fun canPass(vehicleA: Vehicle, vehicleB: Vehicle): Boolean {
        return safePassageDetector.canPass(vehicleA, vehicleB)
    }
}