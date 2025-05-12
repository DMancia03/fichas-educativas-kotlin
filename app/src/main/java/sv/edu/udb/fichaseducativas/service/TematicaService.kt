package sv.edu.udb.fichaseducativas.service

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import sv.edu.udb.fichaseducativas.data.HelperDb
import sv.edu.udb.fichaseducativas.models.Ficha
import sv.edu.udb.fichaseducativas.models.Tematica


class TematicaService(
    context: Context
) {
    private var helper : HelperDb
    private var db : SQLiteDatabase

    init {
        helper = HelperDb(context)
        db = helper.writableDatabase
    }
    fun insertTematica(tematica: Tematica): Long {
        val values = ContentValues().apply {
            put(Tematica.COL_NOMBRE, tematica.Nombre)
        }
        return db.insert(Tematica.TABLE_NAME, null, values)
    }

    fun getAllTematicas(): List<Tematica> {
        val lista = mutableListOf<Tematica>()
        val cursor = db.query(
            Tematica.TABLE_NAME,
            Tematica.COLUMNS,
            null,
            null,
            null,
            null,
            "${Tematica.COL_NOMBRE} ASC"
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Tematica.COL_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(Tematica.COL_NOMBRE))
                lista.add(Tematica(id, nombre))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

    fun updateTematica(tematica: Tematica): Int {
        val values = ContentValues().apply {
            put(Tematica.COL_NOMBRE, tematica.Nombre)
        }

        val whereClause = "${Tematica.COL_ID} = ?"
        val whereArgs = arrayOf(tematica.Id.toString())

        return db.update(Tematica.TABLE_NAME, values, whereClause, whereArgs)
    }

    fun deleteTematica(id: Int): Int {
        val whereClause = "${Tematica.COL_ID} = ?"
        val whereArgs = arrayOf(id.toString())
        return db.delete(Tematica.TABLE_NAME, whereClause, whereArgs)
    }

    fun getById(
        id : Int
    ) : Tematica {
        val cursor : Cursor? = db.query(
            Tematica.TABLE_NAME,
            Tematica.COLUMNS,
            "${Tematica.COL_ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if(cursor == null || cursor.count <= 0){
            return Tematica(0, "")
        }

        cursor.moveToFirst()

        return Tematica(
            cursor.getInt(0),
            cursor.getString(1)
        )
    }
}