package pl.treska.simulation.intersection

data class Vehicle(
        val vehicleId: String,
        val startRoad: Road,
        val endRoad: Road
) {
        val roadDirection: RoadDirection = startRoad.position.directionTo(endRoad.position)

        override fun toString(): String {
                return "$vehicleId startRoad=${startRoad.position} endRoad=${endRoad.position}"
        }

        override fun hashCode(): Int {
                return super.hashCode()
        }
}