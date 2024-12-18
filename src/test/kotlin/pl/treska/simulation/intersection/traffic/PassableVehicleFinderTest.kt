package pl.treska.simulation.intersection.traffic

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.treska.simulation.*
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.Road
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.intersection.Vehicle
import pl.treska.simulation.intersection.traffic.PassableVehicleFinder
import pl.treska.simulation.intersection.traffic.passage.SafePassageDetector
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PassableVehicleFinderTest {
    private val safePassageDetector = mockk<SafePassageDetector>(relaxed = true)
    private val vehicleFinder = PassableVehicleFinder(safePassageDetector)
    private var intersection = Intersection()
    private val priorityIdx = 0

    @BeforeEach
    fun clearIntersection() {
        intersection = Intersection()
    }

    @Test
    fun `Should list all viable vehicles`(){
        // given
        val vehicle1 = intersection.addVehicleOnRoad(1, RoadPosition.SOUTH, RoadPosition.NORTH)
        val vehicle2 = intersection.addVehicleOnRoad(2, RoadPosition.NORTH, RoadPosition.SOUTH)

        every { safePassageDetector.canPass(vehicle2, vehicle1) } returns true

        // when
        val (_, vehicles) = vehicleFinder.findVehiclesToMove(intersection, priorityIdx)

        // then
        assertEqualsLists(listOf(vehicle1, vehicle2), vehicles)
    }

    @Test
    fun `Should return default values if every road is empty`() {
        // given

        // when
        val (idx, vehicles) = vehicleFinder.findVehiclesToMove(intersection, priorityIdx)

        // then
        assertTrue { vehicles.isEmpty() }
        assertEquals(priorityIdx, idx)
    }

    @Test
    fun `Should respect priority`() {
        // given
        val vehicle1 = intersection.addVehicleOnRoad(1, RoadPosition.NORTH, RoadPosition.SOUTH)
        val vehicle2 = intersection.addVehicleOnRoad(2, RoadPosition.EAST, RoadPosition.WEST)
        val vehicle3 = intersection.addVehicleOnRoad(3, RoadPosition.SOUTH, RoadPosition.NORTH)
        val vehicle4 = intersection.addVehicleOnRoad(4, RoadPosition.NORTH, RoadPosition.SOUTH)

        every { safePassageDetector.canPass(vehicle1, vehicle2) } returns false
        every { safePassageDetector.canPass(vehicle1, vehicle3) } returns true
        every { safePassageDetector.canPass(vehicle2, vehicle4) } returns false

        // when
        val (idx1, vehicles1) = vehicleFinder.findVehiclesToMove(intersection, 0)
        vehicles1.forEach { it.startRoad.moveFirstVehicle() }
        val (idx2, vehicles2) = vehicleFinder.findVehiclesToMove(intersection, 1)
        vehicles2.forEach { it.startRoad.moveFirstVehicle() }
        val (idx3, vehicles3) = vehicleFinder.findVehiclesToMove(intersection, 2)
        vehicles3.forEach { it.startRoad.moveFirstVehicle() }


        // then
        assertEqualsLists(listOf(vehicle1, vehicle3), vehicles1)
        assertEqualsLists(listOf(vehicle2), vehicles2)
        assertEqualsLists(listOf(vehicle4), vehicles3)
    }

    @Test
    fun `Should return idx based on what road was starter road`() {
        // given
        val vehicle1 = intersection.addVehicleOnRoad(1, RoadPosition.NORTH, RoadPosition.SOUTH)
        val vehicle2 = intersection.addVehicleOnRoad(2, RoadPosition.EAST, RoadPosition.WEST)
        val vehicle3 = intersection.addVehicleOnRoad(3, RoadPosition.SOUTH, RoadPosition.NORTH)
        val vehicle4 = intersection.addVehicleOnRoad(4, RoadPosition.NORTH, RoadPosition.SOUTH)

        every { safePassageDetector.canPass(vehicle1, vehicle2) } returns false
        every { safePassageDetector.canPass(vehicle1, vehicle3) } returns true
        every { safePassageDetector.canPass(vehicle2, vehicle4) } returns false

        // when
        val (idx1, vehicles1) = vehicleFinder.findVehiclesToMove(intersection, 0)
        vehicles1.forEach { intersection.removeVehicle(it) }
        val (idx2, vehicles2) = vehicleFinder.findVehiclesToMove(intersection, 1)
        vehicles2.forEach { it.startRoad.moveFirstVehicle() }
        val (idx3, vehicles3) = vehicleFinder.findVehiclesToMove(intersection, 2)
        vehicles3.forEach { it.startRoad.moveFirstVehicle() }


        // then
        assertEquals(0, idx1)
        assertEquals(1, idx2)
        assertEquals(0, idx3)
    }


}