package pl.treska.simulation.command

import pl.treska.simulation.intersection.Vehicle
import pl.treska.simulation.intersection.traffic.TrafficController


enum class CommandType {
    STEP, ADD_VEHICLE
}

interface Command {
    val type: CommandType

    fun execute()
}

class StepCommand(private val trafficController: TrafficController): Command {
    override val type = CommandType.STEP

    override fun execute() {
        trafficController.step()
            .also { backlog.stepStatuses.add(it) }
    }

    companion object {
        val backlog: Backlog = Backlog()
    }
}

class AddVehicleCommand(private val vehicle: Vehicle): Command {
    override val type = CommandType.ADD_VEHICLE

    override fun execute() {
        vehicle.startRoad.addVehicle(vehicle)
    }
}

data class UnparsedCommand(
    val type: String,
    val vehicleId: String? = null,
    val startRoad: String? = null,
    val endRoad: String? = null,
)