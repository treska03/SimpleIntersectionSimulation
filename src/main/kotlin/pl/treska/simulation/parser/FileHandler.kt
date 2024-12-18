package pl.treska.simulation.parser

import java.io.File

class FileHandler {
    companion object {
        fun writeOutputFile(filepath: String, output: String) {
            File(filepath).writeText(output)
        }

        fun readInputFile(filepath: String): String =
            File(filepath).readText(Charsets.UTF_8)
    }
}