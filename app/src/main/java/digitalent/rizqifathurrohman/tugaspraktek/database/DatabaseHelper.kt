package digitalent.rizqifathurrohman.tugaspraktek.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Database name and version
        private const val DATABASE_NAME = "mahasiswa.db"
        private const val DATABASE_VERSION = 1

        // Table name and column names
        const val TABLE_USER = "user"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DOMISILI = "domisili"
        const val COLUMN_KELAMIN = "kelamin"
        const val COLUMN_TTL = "ttl"
        const val COLUMN_NPM = "npm"

        // SQL statement to create the table
        private const val TABLE_CREATE =
            "CREATE TABLE $TABLE_USER (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME TEXT NOT NULL, " +
                    "$COLUMN_DOMISILI TEXT NOT NULL, " +
                    "$COLUMN_KELAMIN TEXT NOT NULL, " +
                    "$COLUMN_TTL TEXT NOT NULL, " +
                    "$COLUMN_NPM TEXT NOT NULL);"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Execute the SQL statement to create the table
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop the old table and create a new one
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }
}
