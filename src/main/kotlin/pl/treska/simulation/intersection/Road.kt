package pl.treska.simulation.intersection

import java.util.LinkedList

data class Road(
    val position: RoadPosition,
    private val vehicles: LinkedList<Vehicle> = LinkedList()
) {
    fun addVehicle(vehicle: Vehicle) {
        vehicles.add(vehicle)
    }

    fun moveFirstVehicle() {
        vehicles.removeFirst()
    }

    fun isEmpty(): Boolean = vehicles.isEmpty()

    fun firstVehicle(): Vehicle? = vehicles.firstOrNull()
}

enum class RoadPosition {
    NORTH, EAST, SOUTH, WEST;

    fun next(): RoadPosition =
        entries[(this.ordinal + 1) % RoadPosition.entries.size]

    fun directionTo(other: RoadPosition): RoadDirection =
        RoadDirection.entries[(other.ordinal - this.ordinal + entries.size) % entries.size - 1]
}

enum class RoadDirection {
    LEFT, FORWARD, RIGHT;
}