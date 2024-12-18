package pl.treska.simulation.command

data class StepBacklog(val listOfVehicleIds: List<String> = emptyList())

data class Backlog(val stepStatuses: MutableList<StepBacklog> = mutableListOf())