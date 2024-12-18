package pl.treska.simulation.intersection.traffic.passage

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import pl.treska.simulation.getByPosition
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.intersection.Vehicle
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SafePassageStrategiesTest {
    private val intersection = Intersection()

    @Nested
    inner class SamePathButReversedSafePassageStrategyTest {
        private val strategy = SamePathButReversedSafePassageStrategy()

        @Test
        fun `Should work with simple turns`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.EAST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.EAST)
            )

            // when

            // then
            assertTrue { strategy.canPass(vehicle1, vehicle2) }
        }

        @Test
        fun `Should not work with different paths`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.EAST)
            )

            // when

            // then
            assertFalse { strategy.canPass(vehicle1, vehicle2) }
        }
    }

    @Nested
    inner class TwoRightsSafePassageStrategyTest {
        private val strategy = TwoRightsSafePassageStrategy()

        @Test
        fun `Should work with two right turns`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.EAST)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.EAST),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.WEST)
            )

            // when

            // then
            assertTrue { strategy.canPass(vehicle1, vehicle2) }
            assertTrue { strategy.canPass(vehicle2, vehicle3) }
            assertTrue { strategy.canPass(vehicle3, vehicle1) }
            assertTrue { strategy.canPass(vehicle1, vehicle3) }
        }

        @Test
        fun `Should not work with at least a single turn that is not right turn `() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            // when

            // then
            assertFalse { strategy.canPass(vehicle1, vehicle2) }
            assertFalse { strategy.canPass(vehicle2, vehicle3) }
        }
    }

    @Nested
    inner class ForwardAndRightWithoutArrowSafePassageStrategyTest {
        private val strategy = ForwardAndRightWithoutArrowSafePassageStrategy()

        @Test
        fun `Should work with one forward and one right turn`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.WEST)
            )

            // when

            // then
            assertTrue { strategy.canPass(vehicle1, vehicle2) }
            assertTrue { strategy.canPass(vehicle1, vehicle3) }
        }

        @Test
        fun `Should work no matter which order vehicles are passed in`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.WEST)
            )

            // when

            // then
            assertTrue { strategy.canPass(vehicle1, vehicle2) }
            assertTrue { strategy.canPass(vehicle1, vehicle3) }
            assertTrue { strategy.canPass(vehicle2, vehicle1) }
            assertTrue { strategy.canPass(vehicle3, vehicle1) }
        }

        @Test
        fun `Should not work if there is not forward and right turn combination`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.EAST)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.WEST)
            )

            // when

            // then
            assertFalse { strategy.canPass(vehicle1, vehicle2) }
            assertFalse { strategy.canPass(vehicle2, vehicle3) }
        }

        @Test
        fun `Should not work if there could be collision`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.EAST),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )
            // when

            // then
            assertFalse { strategy.canPass(vehicle1, vehicle2) }
        }
    }

    @Nested
    inner class ForwardAndRightWithArrowSafePassageStrategyTest {
        private val strategy = ForwardAndRightWithArrowSafePassageStrategy()

        @Test
        fun `Should work with one forward and one right turn`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.WEST)
            )

            val vehicle4 = Vehicle(
                "id4",
                intersection.roads.getByPosition(RoadPosition.EAST),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            // when

            // then
            assertTrue { strategy.canPass(vehicle1, vehicle2) }
            assertTrue { strategy.canPass(vehicle1, vehicle3) }
            assertTrue { strategy.canPass(vehicle1, vehicle4) }
        }

        @Test
        fun `Should work no matter which order vehicles are passed in`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.SOUTH),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.WEST)
            )

            val vehicle4 = Vehicle(
                "id4",
                intersection.roads.getByPosition(RoadPosition.EAST),
                intersection.roads.getByPosition(RoadPosition.NORTH)
            )

            // when

            // then
            assertTrue { strategy.canPass(vehicle1, vehicle2) }
            assertTrue { strategy.canPass(vehicle1, vehicle3) }
            assertTrue { strategy.canPass(vehicle1, vehicle4) }
            assertTrue { strategy.canPass(vehicle2, vehicle1) }
            assertTrue { strategy.canPass(vehicle3, vehicle1) }
            assertTrue { strategy.canPass(vehicle4, vehicle1) }
        }

        @Test
        fun `Should not work if there is not forward and right turn combination`() {
            // given
            val vehicle1 = Vehicle(
                "id1",
                intersection.roads.getByPosition(RoadPosition.WEST),
                intersection.roads.getByPosition(RoadPosition.SOUTH)
            )

            val vehicle2 = Vehicle(
                "id2",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.EAST)
            )

            val vehicle3 = Vehicle(
                "id3",
                intersection.roads.getByPosition(RoadPosition.NORTH),
                intersection.roads.getByPosition(RoadPosition.WEST)
            )

            // when

            // then
            assertFalse { strategy.canPass(vehicle1, vehicle2) }
            assertFalse { strategy.canPass(vehicle2, vehicle3) }
        }
    }
}