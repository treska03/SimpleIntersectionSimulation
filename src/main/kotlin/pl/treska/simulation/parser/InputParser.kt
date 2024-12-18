package pl.treska.simulation.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import pl.treska.simulation.command.AddVehicleCommand
import pl.treska.simulation.command.Command
import pl.treska.simulation.command.StepCommand
import pl.treska.simulation.command.UnparsedCommand
import pl.treska.simulation.exception.InvalidInputException
import pl.treska.simulation.exception.UnknownCommandException
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.Road
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.intersection.Vehicle
import pl.treska.simulation.intersection.traffic.TrafficController
import java.io.File

class InputParser(
    private val intersection: Intersection,
    private val trafficController: TrafficController,
    private val objectMapper: ObjectMapper
) {

    fun parseInput(filepath: String): List<Command> {
        val inputJson = parseJson(FileHandler.readInputFile(filepath))

        val unparsedCommands = inputJson["commands"] ?: throw InvalidInputException("Cannot find commands")

        return unparsedCommands.map { command -> command.parseToCommand()}
    }

    private fun parseJson(json: String): Map<String, List<UnparsedCommand>> {
        try {
            return objectMapper.readValue(json)
        } catch (e: MismatchedInputException) {
            throw InvalidInputException("Incorrect format of the command. Make sure that they all contain correct fields")
        }
    }

    private fun UnparsedCommand.parseToCommand(): Command =
        when(type) {
            "step" -> StepCommand(trafficController)
            "addVehicle" -> AddVehicleCommand(vehicleInstance())
            else -> throw UnknownCommandException("Unknown command found inside input file")
        }


    private fun UnparsedCommand.vehicleInstance(): Vehicle { // TODO: test it
        return Vehicle(
            vehicleId = vehicleId ?: throw InvalidInputException("vehicleId is not present"),
            startRoad = startRoad?.toRoad() ?: throw InvalidInputException("startRoad is not present"),
            endRoad = endRoad?.toRoad() ?: throw InvalidInputException("endRoad is not present"),
        )
    }

    private fun String.toRoad(): Road =
        intersection.getRoad(RoadPosition.valueOf(this.uppercase()))
}