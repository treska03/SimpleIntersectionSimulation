package pl.treska.simulation.validator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.treska.simulation.exception.InvalidArgumentsException

class ArgumentsValidatorTest {

    @Test
    fun `Should throw on invalid args length`() {
        // given
        val args = arrayOf("somefile.json")

        // when

        // then
        assertThrows<InvalidArgumentsException> { ArgumentsValidator.validate(args) }
    }

    @Test
    fun `Should throw on invalid args extension`() {
        // given
        val args = arrayOf("somefile.java", "otherfile.c")

        // when

        // then
        assertThrows<InvalidArgumentsException> { ArgumentsValidator.validate(args) }
    }

    @Test
    fun `Should throw on incorrect paths`() {
        // given
        val args = arrayOf("My*File?.txt.json", "somefile.json")

        // when

        // then
        assertThrows<InvalidArgumentsException> { ArgumentsValidator.validate(args) }
    }


}