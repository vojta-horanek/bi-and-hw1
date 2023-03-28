package cz.cvut.fit.biand.homework1.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog

/**
 * Base class for any destination the app uses.
 * This should be used as a parent for a sealed class which corresponds to some nav graph
 */
abstract class Destination {
    /**
     * The id of the destination, without nav arguments.
     */
    protected abstract val destinationId: String
    open val arguments: List<NamedNavArgument> = emptyList()

    /**
     * Route used to determine this destination from others. Do not use for navigating.
     */
    val route
        get() = constructRoute()

    /**
     * Creates a navigate-able route with the [arguments] provided.
     * @param arguments Arguments that should be used for this destination. The number and order of arguments
     * must exactly match the number and order of arguments this destination requires. For a default value, use null.
     */
    operator fun invoke(vararg arguments: Any?) = constructRouteForNavigation(*arguments)

    /**
     * Constructs a route with argument names in the following format:
     * - e.g. Destination called `detail` with arguments named `id` and `name`:
     *      "detail?id={id}&name={name}"
     * - e.g. Destination called `home` with no arguments:
     *      "home"
     */
    private fun constructRoute() = createUri(
        path = destinationId,
        argList = arguments,
        key = { it.name },
        value = { "{${it.name}}" },
    )

    /**
     * Constructs a route with argument names in the following format:
     * - e.g. Destination called `detail` with arguments named `id` = 5 and `name` = "test":
     *      "detail?id=5&name=test"
     * - e.g. Destination called `home` with no arguments:
     *      "home"
     */
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

fun NavGraphBuilder.dialogDestination(
    destination: Destination,
    content: @Composable (NavBackStackEntry) -> Unit,
) = dialog(
    route = destination.route,
    arguments = destination.arguments,
    content = content,
)
