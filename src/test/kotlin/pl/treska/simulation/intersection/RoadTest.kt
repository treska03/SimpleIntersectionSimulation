package pl.treska.simulation.intersection

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RoadTest {

    @Nested
    inner class RoadPositionTest {

        @Test
        fun `Road position direction to should work`() {
            // given
            val north = RoadPosition.NORTH
            val east = RoadPosition.EAST
            val south = RoadPosition.SOUTH
            val west = RoadPosition.WEST

            // when

            // then
            assertEquals(RoadDirection.LEFT, north.directionTo(east))
            assertEquals(RoadDirection.FORWARD, north.directionTo(south))
            assertEquals(RoadDirection.RIGHT, north.directionTo(west))

            assertEquals(RoadDirection.LEFT, east.directionTo(south))
            assertEquals(RoadDirection.FORWARD, east.directionTo(west))
            assertEquals(RoadDirection.RIGHT, east.directionTo(north))

            assertEquals(RoadDirection.LEFT, south.directionTo(west))
            assertEquals(RoadDirection.FORWARD, south.directionTo(north))
            assertEquals(RoadDirection.RIGHT, south.directionTo(east))

            assertEquals(RoadDirection.LEFT, west.directionTo(north))
            assertEquals(RoadDirection.FORWARD, west.directionTo(east))
            assertEquals(RoadDirection.RIGHT, west.directionTo(south))
        }
    }
}