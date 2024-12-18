package pl.treska.simulation.intersection

class Intersection(
    val roads: List<Road> = listOf(
        Road(RoadPosition.NORTH),
        Road(RoadPosition.EAST),
        Road(RoadPosition.SOUTH),
        Road(RoadPosition.WEST),
    )
) {
    fun getRoad(roadPosition: RoadPosition): Road = roads.find { it.position == roadPosition }!!
}