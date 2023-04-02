package cz.cvut.fit.biand.homework1.infrastructure.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

fun createSqlDriver(context: Context): SqlDriver =
    AndroidSqliteDriver(Database.Schema, context, "hw1.db")

fun createDatabase(
    driver: SqlDriver
) = Database(
    driver = driver,
)