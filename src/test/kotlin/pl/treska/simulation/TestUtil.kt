package pl.treska.simulation

import pl.treska.simulation.command.AddVehicleCommand
import pl.treska.simulation.command.Command
import pl.treska.simulation.command.CommandType
import pl.treska.simulation.command.StepCommand
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.Road
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.intersection.Vehicle
import java.util.*
import kotlin.test.assertEquals

fun List<Road>.getByPosition(position: RoadPosition): Road =
    find { position == it.position }!!

fun Intersection.addVehicle(vehicle: Vehicle) {
    getRoad(vehicle.startRoad.position).addVehicle(vehicle)
}

fun Intersection.addVehicleOnRoad(id: Any, startRoad: RoadPosition, endRoad: RoadPosition) =
    Vehicle(id.toString(), getRoad(startRoad), getRoad(endRoad))
        .also { addVehicle(it) }

fun Intersection.removeVehicle(vehicle: Vehicle) {
    (vehicle.startRoad.javaClass.getDeclaredField("vehicles").also { it.isAccessible = true }.get(vehicle.startRoad) as LinkedList<Vehicle>)
        .remove(vehicle)
}

fun Intersection.firstVehicles(): List<Vehicle> =
    roads.mapNotNull { it.firstVehicle() }

fun <T> assertEqualsLists(expected: List<T>, actual: List<T>) {
    assertEquals(expected.sortedBy { it.hashCode() }, actual.sortedBy { it.hashCode() })
}

fun StepCommand.equalTo(other: StepCommand): Boolean =
    type == other.type

fun AddVehicleCommand.equalTo(other: AddVehicleCommand): Boolean {
    val vehicle = (this.javaClass.getDeclaredField("vehicle").also { it.isAccessible = true }.get(this) as Vehicle)
    val otherVehicle = (other.javaClass.getDeclaredField("vehicle").also { it.isAccessible = true }.get(other) as Vehicle)

    return type == other.type &&
            vehicle.vehicleId == otherVehicle.vehicleId &&
            vehicle.startRoad == otherVehicle.startRoad &&
            vehicle.endRoad == otherVehicle.endRoad
}

fun Command.equalTo(other: Command): Boolean {
    if (this.type != other.type) return false
    return when(type) {
        CommandType.STEP -> (this as StepCommand).equalTo(other as StepCommand)
        CommandType.ADD_VEHICLE -> (this as AddVehicleCommand).equalTo(other as AddVehicleCommand)
    }
}