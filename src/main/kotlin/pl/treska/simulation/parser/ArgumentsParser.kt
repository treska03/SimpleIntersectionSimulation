package pl.treska.simulation.parser

import pl.treska.simulation.validator.ArgumentsValidator

class ArgumentsParser {
    companion object {
        fun parseArguments(args: Array<String>): Pair<String, String>  {
            ArgumentsValidator.validate(args)

            return Pair(args[0], args[1])
        }
    }
}