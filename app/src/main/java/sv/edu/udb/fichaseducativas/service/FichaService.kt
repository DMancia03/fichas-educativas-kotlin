package sv.edu.udb.fichaseducativas.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import sv.edu.udb.fichaseducativas.data.HelperDb

class FichaService(
    context: Context
) {
    private var helper : HelperDb
    private var db : SQLiteDatabase

    init {
        helper = HelperDb(context)
        db = helper.writableDatabase
    }
        fun contarFichasPorTematica(idTematica: Int): Int {
            val query = "SELECT COUNT(*) FROM ${sv.edu.udb.fichaseducativas.models.Ficha.TABLE_NAME} WHERE ${sv.edu.udb.fichaseducativas.models.Ficha.COL_ID_TEMATICA} = ?"
            val cursor = db.rawQuery(query, arrayOf(idTematica.toString()))
            var total = 0
            if (cursor.moveToFirst()) {
                total = cursor.getInt(0)
            }
            cursor.close()
            return total
        }


}