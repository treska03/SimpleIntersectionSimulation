package pl.treska.simulation.intersection.traffic

import pl.treska.simulation.command.StepBacklog
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.intersection.Vehicle

class TrafficController(private val intersection: Intersection, private val passableVehicleFinder: PassableVehicleFinder) {
    private var currentPriorityIndex = 0

    fun step(): StepBacklog =
        StepBacklog(runCycle().map { it.vehicleId })



    private fun runCycle(): List<Vehicle>{
        val (firstVehicleIdx, vehiclesToMove) = passableVehicleFinder.findVehiclesToMove(intersection, currentPriorityIndex)

        vehiclesToMove.forEach { vehicle ->
            vehicle.startRoad.moveFirstVehicle()
        }

        currentPriorityIndex = (firstVehicleIdx + 1) % RoadPosition.entries.size

        return vehiclesToMove
    }


}