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
}