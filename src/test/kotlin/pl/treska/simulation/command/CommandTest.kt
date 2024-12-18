package pl.treska.simulation.command

import io.mockk.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import pl.treska.simulation.assertEqualsLists
import pl.treska.simulation.intersection.Road
import pl.treska.simulation.intersection.Vehicle
import pl.treska.simulation.intersection.traffic.TrafficController

class CommandTest {

    @Nested
    inner class StepCommandTest {
        private val trafficController = mockk<TrafficController>(relaxed = true)

        @Test
        fun `Should delegate to traffic controller`() {
            // given
            val command = StepCommand(trafficController)
            val stepBacklog = StepBacklog()
            every { trafficController.step() } returns stepBacklog

            // when
            command.execute()

            // then
            verify(exactly = 1) { trafficController.step() }
        }

        @Test
        fun `Should successfully append to backlog`() {
            // given
            StepCommand.backlog.stepStatuses.clear()
            val stepBacklog = StepBacklog(
                listOf("vehicle1", "vehicle2", "vehicle3")
            )

            val command = StepCommand(trafficController)
            every { trafficController.step() } returns stepBacklog

            // when
            command.execute()

            // then
            assertEqualsLists(StepCommand.backlog.stepStatuses, mutableListOf(stepBacklog))
        }
    }

    @Nested
    inner class AddVehicleCommandTest {

        @Test
        fun `Should add vehicle`() {
            // given
            val road = mockk<Road>(relaxed = true)
            val vehicle = mockk<Vehicle>(relaxed = true) {
                every { startRoad } returns road
            }
            val command = AddVehicleCommand(vehicle)

            // when
            command.execute()

            // then
            verify(exactly = 1) { road.addVehicle(vehicle) }
        }
    }
}