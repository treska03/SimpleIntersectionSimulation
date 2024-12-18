package pl.treska.simulation.parser

import com.fasterxml.jackson.databind.ObjectMapper
import pl.treska.simulation.command.StepCommand

class OutputParser(
    private val objectMapper: ObjectMapper
) {
    fun saveProgramOutput(filepath: String) {
        FileHandler.writeOutputFile(filepath, getBacklogAsString())
    }

    private fun getBacklogAsString(): String =
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(StepCommand.backlog)
}