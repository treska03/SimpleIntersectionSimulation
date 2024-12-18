# Intersection simulation

--------
A simple intersection simulation for recrutation at AVSystem


## How to run:
1. Clone the project
2. Prepare a file containing information about intersection (see example.json)
3. Run `IntersectionSimulationApplication.kt <path/to/file/with/commands.json> <path/to/output/file.json>`

---

## Afterthoughts
- Intersection is structured in such way, that we may add custom strategies that direct traffic (for example green arrow).
- It is pretty much impossible in current form to add another road lanes.
- Unit tests cover all non-trivial public functionality.
- By using kotlin I enabled myself to write more functional-like code while maintaining OOP benefits.
- It is not the smartest implementation of `PassableVehicleFinder`, although I think it's good enough given time spent.
- It uses Round-Robin as measure to make sure that each road gets green light at least once every `RoadCount` cycles