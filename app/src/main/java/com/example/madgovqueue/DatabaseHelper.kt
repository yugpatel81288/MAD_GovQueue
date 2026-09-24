package com.example.madgovqueue

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "GovQueue.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE reports (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                office_name TEXT NOT NULL,
                crowd_status TEXT NOT NULL,
                people_waiting TEXT,
                report_time TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        if (oldVersion < 2) {

            db.execSQL(
                """
                CREATE TABLE reports (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    office_name TEXT NOT NULL,
                    crowd_status TEXT NOT NULL,
                    people_waiting TEXT,
                    report_time TEXT NOT NULL
                )
                """.trimIndent()
            )
        }
    }

    // ================================
    // REGISTER USER
    // ================================

    fun registerUser(
        name: String,
        email: String,
        password: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
            put("password", password)
        }

        return try {

            val result = db.insert(
                "users",
                null,
                values
            )

            result != -1L

        } catch (e: Exception) {

            false
        }
    }

    // ================================
    // LOGIN USER
    // ================================

    fun loginUser(
        email: String,
        password: String
    ): Boolean {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE email = ? AND password = ?",
            arrayOf(email, password)
        )

        val success = cursor.count > 0

        cursor.close()

        return success
    }

    // ================================
    // RESET PASSWORD
    // ================================

    fun resetPassword(
        email: String,
        newPassword: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues().apply {
            put("password", newPassword)
        }

        val rowsUpdated = db.update(
            "users",
            values,
            "email = ?",
            arrayOf(email)
        )

        return rowsUpdated > 0
    }

    // ================================
    // GET USER DETAILS
    // ================================

    fun getUserDetails(
        email: String
    ): Pair<String, String>? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT name, email FROM users WHERE email = ?",
            arrayOf(email)
        )

        var userDetails: Pair<String, String>? = null

        if (cursor.moveToFirst()) {

            val name = cursor.getString(
                cursor.getColumnIndexOrThrow("name")
            )

            val userEmail = cursor.getString(
                cursor.getColumnIndexOrThrow("email")
            )

            userDetails = Pair(
                name,
                userEmail
            )
        }

        cursor.close()

        return userDetails
    }

    // ================================
    // ADD CROWD REPORT
    // ================================

    fun addCrowdReport(
        officeName: String,
        crowdStatus: String,
        peopleWaiting: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues().apply {

            put("office_name", officeName)
            put("crowd_status", crowdStatus)
            put("people_waiting", peopleWaiting)

            put(
                "report_time",
                System.currentTimeMillis().toString()
            )
        }

        val result = db.insert(
            "reports",
            null,
            values
        )

        return result != -1L
    }

    // ================================
    // GET ALL REPORTS
    // ================================

    fun getAllReports(): ArrayList<Report> {

        val reports = ArrayList<Report>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM reports ORDER BY id DESC",
            null
        )

        if (cursor.moveToFirst()) {

            do {

                val id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
                )

                val officeName = cursor.getString(
                    cursor.getColumnIndexOrThrow("office_name")
                )

                val crowdStatus = cursor.getString(
                    cursor.getColumnIndexOrThrow("crowd_status")
                )

                val peopleWaiting = cursor.getString(
                    cursor.getColumnIndexOrThrow("people_waiting")
                )

                val reportTime = cursor.getString(
                    cursor.getColumnIndexOrThrow("report_time")
                )

                reports.add(
                    Report(
                        id,
                        officeName,
                        crowdStatus,
                        peopleWaiting,
                        reportTime
                    )
                )

            } while (cursor.moveToNext())
        }

        cursor.close()

        return reports
    }

    // ================================
    // GET LATEST REPORT FOR OFFICE
    // ================================

    fun getLatestReportForOffice(
        officeName: String
    ): Report? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            """
            SELECT *
            FROM reports
            WHERE office_name = ?
            ORDER BY id DESC
            LIMIT 1
            """.trimIndent(),
            arrayOf(officeName)
        )

        var report: Report? = null

        if (cursor.moveToFirst()) {

            val id = cursor.getInt(
                cursor.getColumnIndexOrThrow("id")
            )

            val name = cursor.getString(
                cursor.getColumnIndexOrThrow("office_name")
            )

            val crowd = cursor.getString(
                cursor.getColumnIndexOrThrow("crowd_status")
            )

            val people = cursor.getString(
                cursor.getColumnIndexOrThrow("people_waiting")
            )

            val time = cursor.getString(
                cursor.getColumnIndexOrThrow("report_time")
            )

            report = Report(
                id,
                name,
                crowd,
                people,
                time
            )
        }

        cursor.close()

        return report
    }
}