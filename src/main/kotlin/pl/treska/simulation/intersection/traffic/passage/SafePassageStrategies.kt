package pl.treska.simulation.intersection.traffic.passage

import pl.treska.simulation.intersection.RoadDirection
import pl.treska.simulation.intersection.Vehicle

// TODO: DO STRATEGY WITH GREEN ARROW

class SamePathButReversedSafePassageStrategy: SafePassageStrategy {
    override fun canPass(vehicleA: Vehicle, vehicleB: Vehicle): Boolean =
        vehicleA.startRoad == vehicleB.endRoad && vehicleA.endRoad == vehicleB.startRoad
}

class TwoRightsSafePassageStrategy: SafePassageStrategy {
    override fun canPass(vehicleA: Vehicle, vehicleB: Vehicle): Boolean =
        vehicleA.roadDirection == RoadDirection.RIGHT && vehicleB.roadDirection == RoadDirection.RIGHT
}

class ForwardAndRightWithoutArrowSafePassageStrategy: SafePassageStrategy {
    override fun canPass(vehicleA: Vehicle, vehicleB: Vehicle): Boolean =
        (vehicleA.roadDirection == RoadDirection.FORWARD && vehicleB.roadDirection == RoadDirection.RIGHT &&
                vehicleB.startRoad.position.next() != vehicleA.startRoad.position) ||
        (vehicleB.roadDirection == RoadDirection.FORWARD && vehicleA.roadDirection == RoadDirection.RIGHT &&
                vehicleA.startRoad.position.next() != vehicleB.startRoad.position)
}

class ForwardAndRightWithArrowSafePassageStrategy: SafePassageStrategy {
    override fun canPass(vehicleA: Vehicle, vehicleB: Vehicle): Boolean =
        (vehicleA.roadDirection == RoadDirection.FORWARD && vehicleB.roadDirection == RoadDirection.RIGHT) ||
        (vehicleB.roadDirection == RoadDirection.FORWARD && vehicleA.roadDirection == RoadDirection.RIGHT)
}