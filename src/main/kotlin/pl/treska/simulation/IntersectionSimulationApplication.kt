package pl.treska.simulation

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import pl.treska.simulation.intersection.Intersection
import pl.treska.simulation.intersection.Road
import pl.treska.simulation.intersection.RoadPosition
import pl.treska.simulation.command.CommandExecutor
import pl.treska.simulation.parser.InputParser
import pl.treska.simulation.command.StepCommand
import pl.treska.simulation.intersection.traffic.passage.ForwardAndRightWithoutArrowSafePassageStrategy
import pl.treska.simulation.intersection.traffic.passage.SamePathButReversedSafePassageStrategy
import pl.treska.simulation.intersection.traffic.passage.TwoRightsSafePassageStrategy
import pl.treska.simulation.parser.OutputParser
import pl.treska.simulation.intersection.traffic.PassableVehicleFinder
import pl.treska.simulation.intersection.traffic.passage.SafePassageDetector
import pl.treska.simulation.intersection.traffic.TrafficController
import pl.treska.simulation.parser.ArgumentsParser

fun main(args: Array<String>) {

	val (inputpath, outputpath) = ArgumentsParser.parseArguments(args)

	val intersection = Intersection()

	val safePassageDetector = SafePassageDetector(
		listOf(
			TwoRightsSafePassageStrategy(),
			SamePathButReversedSafePassageStrategy(),
			ForwardAndRightWithoutArrowSafePassageStrategy()
		)
	)

	val passableVehicleFinder = PassableVehicleFinder(
		safePassageDetector
	)

	val trafficController = TrafficController(
		intersection,
		passableVehicleFinder
	)

	val objectMapper = jacksonObjectMapper()

	val commands = InputParser(
		intersection,
		trafficController,
		objectMapper
	).parseInput(inputpath)

	commands.forEach { CommandExecutor.execute(it) }

	OutputParser(objectMapper).saveProgramOutput(outputpath)
}
