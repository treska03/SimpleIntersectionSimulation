package pl.treska.simulation.command

class CommandExecutor {
    companion object {
        fun execute(command: Command) {
            command.execute()
        }
    }
}

