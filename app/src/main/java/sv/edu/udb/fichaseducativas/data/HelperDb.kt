package sv.edu.udb.fichaseducativas.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import sv.edu.udb.fichaseducativas.models.Ficha
import sv.edu.udb.fichaseducativas.models.Tematica

class HelperDb(
    context: Context
) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {
    companion object{
        val DB_NAME = "fichas_educativas"
        val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tematica.CREATE_TABLE)
        db.execSQL(Ficha.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(Ficha.DROP_TABLE)
        db.execSQL(Tematica.DROP_TABLE)

        db.execSQL(Tematica.CREATE_TABLE)
        db.execSQL(Ficha.CREATE_TABLE)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(Ficha.DROP_TABLE)
        db.execSQL(Tematica.DROP_TABLE)

        db.execSQL(Tematica.CREATE_TABLE)
        db.execSQL(Ficha.CREATE_TABLE)
    }
}