package pl.treska.simulation.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.*
import org.junit.jupiter.api.assertThrows
import pl.treska.simulation.command.AddVehicleCommand
import pl.treska.simulation.command.StepCommand
import pl.treska.simulation.equalTo
import pl.treska.simulation.exception.InvalidInputException
import pl.treska.simulation.exception.UnknownCommandException
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.intersection.Vehicle
import pl.treska.simulation.intersection.traffic.TrafficController
import kotlin.test.Test
import kotlin.test.assertTrue

class InputParserTest {
    private val intersection = Intersection()
    private val trafficController = mockk<TrafficController>()
    private val objectMapper = jacksonObjectMapper()
    val inputParser = InputParser(intersection, trafficController, objectMapper)


    @Test
    fun `Should parse input correctly`() {
        // given
        mockkObject(FileHandler)
        every { FileHandler.readInputFile(any()) } returns inputJson1

        // when
        val commands = inputParser.parseInput(filepath)

        // then
        assertTrue(outputCommands[0].equalTo(commands[0]))
        assertTrue(outputCommands[1].equalTo(commands[1]))
        assertTrue(outputCommands[2].equalTo(commands[2]))
        assertTrue(outputCommands[3].equalTo(commands[3]))
        assertTrue(outputCommands[4].equalTo(commands[4]))
        assertTrue(outputCommands[5].equalTo(commands[5]))
        assertTrue(outputCommands[6].equalTo(commands[6]))
        assertTrue(outputCommands[7].equalTo(commands[7]))
    }

    @Test
    fun `Should throw exception when parsing incorrect json file`() {
        // given
        mockkObject(FileHandler)
        every { FileHandler.readInputFile(any()) } returns inputJsonInvalidFormat

        // when

        // then
        assertThrows<InvalidInputException> { inputParser.parseInput(filepath) }
    }

    @Test
    fun `Should throw exception when parsing incorrect commands body`() {
        // given
        mockkObject(FileHandler)
        every { FileHandler.readInputFile(any()) } returns inputJsonInvalidCommand

        // when

        // then
        assertThrows<InvalidInputException> { inputParser.parseInput(filepath) }
    }

    @Test
    fun `Should throw exception when found unknown command type`() {
        // given
        mockkObject(FileHandler)
        every { FileHandler.readInputFile(any()) } returns inputJsonInvalidCommandType

        // when

        // then
        assertThrows<UnknownCommandException> { inputParser.parseInput(filepath) }
    }

    private val filepath = "testfilepath1.json"
    private val inputJson1 = """
        {

          "commands": [

            {

              "type": "addVehicle",

              "vehicleId": "vehicle1",

              "startRoad": "south",

              "endRoad": "north"

            },

            {

              "type": "addVehicle",

              "vehicleId": "vehicle2",

              "startRoad": "north",

              "endRoad": "south"

            },

            {

              "type": "step"

            },

            {

              "type": "step"

            },

            {

              "type": "addVehicle",

              "vehicleId": "vehicle3",

              "startRoad": "west",

              "endRoad": "south"

            },

            {

              "type": "addVehicle",

              "vehicleId": "vehicle4",

              "startRoad": "west",

              "endRoad": "south"

            },

            {

              "type": "step"

            },

            {

              "type": "step"

            }

          ]

        }
    """.trimIndent()

    private val inputJsonInvalidFormat = """
        {}}
    """.trimIndent()

    private val inputJsonInvalidCommand = """
        {
          "commands": [
            {
              "x": "d"
            }
          ]
        }
    """.trimIndent()

    private val inputJsonInvalidCommandType = """
        {
          "commands": [
            {
              "type": "42"
            }
          ]
        }
    """.trimIndent()

    private val outputCommands = listOf(
        AddVehicleCommand(Vehicle("vehicle1", intersection.getRoad(RoadPosition.SOUTH), intersection.getRoad(RoadPosition.NORTH))),
        AddVehicleCommand(Vehicle("vehicle2", intersection.getRoad(RoadPosition.NORTH), intersection.getRoad(RoadPosition.SOUTH))),
        StepCommand(trafficController),
        StepCommand(trafficController),
        AddVehicleCommand(Vehicle("vehicle3", intersection.getRoad(RoadPosition.WEST), intersection.getRoad(RoadPosition.SOUTH))),
        AddVehicleCommand(Vehicle("vehicle4", intersection.getRoad(RoadPosition.WEST), intersection.getRoad(RoadPosition.SOUTH))),
        StepCommand(trafficController),
        StepCommand(trafficController)
    )
}