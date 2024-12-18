package pl.treska.simulation.parser

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArgumentsParserTest {
    val tested = ArgumentsParser

    @Test
    fun `Should parse correct args`() {
        // given
        val inputfile = "input.json"
        val outputfile = "output.json"
        val args = arrayOf(inputfile, outputfile)

        // when
        val (o1, o2) = tested.parseArguments(args)

        // then
        assertEquals(inputfile, o1)
        assertEquals(outputfile, o2)
    }


}