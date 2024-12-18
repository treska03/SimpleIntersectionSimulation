package pl.treska.simulation.validator

import pl.treska.simulation.exception.InvalidArgumentsException
import java.nio.file.Paths

class ArgumentsValidator {
    companion object {
        fun validate(args: Array<String>) {
            if(args.size < 2) throw InvalidArgumentsException(
                "Please provide arguments: <path/to/file/with/commands.json> <path/to/output/file.json>"
            )

            if(!args[0].endsWith(".json") || !args[1].endsWith(".json")) throw InvalidArgumentsException(
                "Please provide paths to files with json extension"
            )

            try {
                Paths.get(args[0])
                Paths.get(args[1])
            } catch (e: Exception) {
                throw InvalidArgumentsException("Please provide correct paths")
            }
        }
    }
}