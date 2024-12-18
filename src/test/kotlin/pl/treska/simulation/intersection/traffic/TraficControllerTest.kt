package pl.treska.simulation.intersection.traffic

import io.mockk.*
import org.junit.jupiter.api.Test
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.Road
import pl.treska.simulation.intersection.Vehicle

class TraficControllerTest {
    private val intersection = mockk<Intersection>()
    private val passableVehicleFinder = mockk<PassableVehicleFinder>()
    private val trafficController = TrafficController(intersection, passableVehicleFinder)

    @Test
    fun `Should moves all vehicles`(){
        // given
        val startRoad1 = mockk<Road>(relaxed = true)
        val startRoad2 = mockk<Road>(relaxed = true)
        val vehicle1 = mockk<Vehicle>(relaxed = true) {
            every { startRoad } returns startRoad1
        }
        val vehicle2 = mockk<Vehicle>(relaxed = true) {
            every { startRoad } returns startRoad2
        }

        every { passableVehicleFinder.findVehiclesToMove(any(), any()) } returns Pair(0, listOf(vehicle1, vehicle2))
        every { startRoad1.moveFirstVehicle() } just runs
        every { startRoad2.moveFirstVehicle() } just runs

        // when
        trafficController.step()

        // then
        verify(exactly = 1) { passableVehicleFinder.findVehiclesToMove(any(), any()) }
        verify(exactly = 1) { startRoad1.moveFirstVehicle() }
        verify(exactly = 1) { startRoad2.moveFirstVehicle() }
    }
}