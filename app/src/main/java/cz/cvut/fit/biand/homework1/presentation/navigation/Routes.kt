package cz.cvut.fit.biand.homework1.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Routes : Destination() {

    object Graph : Routes() {
        override val destinationId = "movies"
    }

    object Overview : Routes() {
        override val destinationId = "movies/overview"
    }

    object Search : Routes() {
        override val destinationId = "movies/search"
    }

    object Detail : Routes() {
        override val destinationId = "movies/detail"
        const val IdArg = "id"
        override val arguments = listOf(
            navArgument(IdArg) { type = NavType.LongType }
        )

        internal class Args(
            val id: Long
        ) {
            constructor(arguments: Bundle?) : this(
                id = checkNotNull(arguments?.getLong(IdArg))
            )
        }

    }

    object Favourites : Routes() {
        override val destinationId = "movies/favourites"
    }
}