package sv.edu.udb.fichaseducativas.service

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import sv.edu.udb.fichaseducativas.data.HelperDb
import sv.edu.udb.fichaseducativas.models.Ficha

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

    fun getByTematicaId(idTematica: Int): List<Ficha> {
        val cursor: Cursor? = db.query(
            Ficha.TABLE_NAME,
            Ficha.COLUMNS,
            "${Ficha.COL_ID_TEMATICA} = ?",
            arrayOf(idTematica.toString()),
            null,
            null,
            null
        )

        return generarList(cursor)
    }


    fun generarContentValue(
        ficha : Ficha
    ) : ContentValues {
        val contentValues = ContentValues()
        if(ficha.Id > 0){
            contentValues.put(Ficha.COL_ID, ficha.Id)
        }
        contentValues.put(Ficha.COL_ID_TEMATICA, ficha.IdTematica)
        contentValues.put(Ficha.COL_TITULO, ficha.Titulo)
        contentValues.put(Ficha.COL_DESCRIPCION_CARA, ficha.DescripcionCara)
        contentValues.put(Ficha.COL_DESCRIPCION_ATRAS, ficha.DescripcionAtras)
        contentValues.put(Ficha.COL_IMAGEN, ficha.Imagen)
        return contentValues
    }

    fun generarList(
        cursor: Cursor?
    ) : List<Ficha> {
        if(cursor == null || cursor.count == 0){
            return listOf()
        }

        var fichas : MutableList<Ficha> = mutableListOf()
        cursor.moveToFirst()

        do{
            var f : Ficha = Ficha(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
            )

            fichas.add(f)
        }while (cursor.moveToNext())

        return fichas
    }

    fun getAll() : List<Ficha>{
        val cursor : Cursor? = db.query(
            Ficha.TABLE_NAME,
            Ficha.COLUMNS,
            null,
            null,
            null,
            null,
            null
        )

        return generarList(cursor)
    }

    fun getById(
        id : Int
    ) : Ficha {
        val cursor : Cursor? = db.query(
            Ficha.TABLE_NAME,
            Ficha.COLUMNS,
            "${Ficha.COL_ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if(cursor == null || cursor.count == 0){
            return Ficha(0, 0, "", "", "", "")
        }

        cursor.moveToFirst()

        return Ficha(
            cursor.getInt(0),
            cursor.getInt(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
        )
    }

    fun add(
        ficha: Ficha
    ){
        db.insert(
            Ficha.TABLE_NAME,
            null,
            generarContentValue(ficha)
        )
    }

    fun update(
        ficha : Ficha
    ){
        db.update(
            Ficha.TABLE_NAME,
            generarContentValue(ficha),
            "${Ficha.COL_ID}=?",
            arrayOf(ficha.Id.toString()),
        )
    }

    fun delete(
        id  : Int
    ) {
        db.delete(
            Ficha.TABLE_NAME,
            "${Ficha.COL_ID}=?",
            arrayOf(id.toString()),
        )
    }
}