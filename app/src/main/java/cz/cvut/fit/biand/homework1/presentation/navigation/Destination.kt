package cz.cvut.fit.biand.homework1.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog

abstract class Destination {
    protected abstract val destinationId: String
    open val arguments: List<NamedNavArgument> = emptyList()

    val route
        get() = constructRoute()

    operator fun invoke(vararg arguments: Any?) = constructRouteForNavigation(*arguments)

    private fun constructRoute() = createUri(
        path = destinationId,
        argList = arguments,
        key = { it.name },
        value = { "{${it.name}}" },
    )

    private fun constructRouteForNavigation(
        vararg routeArguments: Any?,
    ): String {
        require(routeArguments.size == arguments.size) {
            "The routeArgument count must match this destination argument count.\n" +
                    "If needed, pass null for default value of argument."
        }
        val args = routeArguments.zip(arguments).map { (routeArg, arg) ->
            // Provided arg or default arg
            val value = (routeArg ?: arg.argument.defaultValue).toString()
            Pair(arg.name, value)
        }

        return createUri(
            path = destinationId,
            argList = args,
            key = { (name, _) -> name },
            value = { (_, value) -> value },
        )
    }

    private fun <T> createUri(
        path: String,
        argList: List<T>,
        key: (T) -> String,
        value: (T) -> String,
    ): String {
        return Uri.Builder()
            .path(path)
            .apply {
                argList.forEach { arg ->
                    appendQueryParameter(key(arg), value(arg))
                }
            }
            .toString()
    }
}

fun NavGraphBuilder.composableDestination(
    destination: Destination,
    content: @Composable (NavBackStackEntry) -> Unit,
) = composable(
    route = destination.route,
    arguments = destination.arguments,
    content = content,
)