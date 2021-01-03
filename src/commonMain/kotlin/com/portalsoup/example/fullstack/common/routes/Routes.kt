package com.portalsoup.example.fullstack.common.routes


// Re-usable path variables
enum class PathVariables(val value: PathSegment.VariableSegment) {
    NAME(variable("name"));
}

// Routes to pages used by react router
enum class ViewRoutes(vararg val segments: PathSegment) {
    HOME(static("/")),
    COUNTER(static("/counter"), variable("name"))
}

// Routes to apis served by ktor
enum class ApiRoutes(vararg val segments: PathSegment) {
    GetCount(static("api"), *ViewRoutes.COUNTER.segments),
    IncrementCount(static("api"), *ViewRoutes.COUNTER.segments, static("increment")),
    DecrementCount(static("api"), *ViewRoutes.COUNTER.segments, static("decrement")),
}

// Defines the different types of path segments, the client and server may treat these different when
// assembling paths
sealed class PathSegment(val name: String) {
    class StaticSegment(name: String): PathSegment(name)
    class VariableSegment(name: String): PathSegment(name)
}

// Convenient functions to make defining paths a little less verbose
private fun static(name: String) = PathSegment.StaticSegment(name)
private fun variable(name: String) = PathSegment.VariableSegment(name)
