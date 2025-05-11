package sv.edu.udb.fichaseducativas.models

class Tematica {
    var Id : Int = 0
    var Nombre : String = ""

    constructor(_id : Int, _nombre : String){
        Id = _id
        Nombre = _nombre
    }

    constructor(_nombre : String){
        Nombre = _nombre
    }

    companion object{
        val TABLE_NAME = "tematica"

        val COL_ID = "id"
        val COL_NOMBRE = "nombre"

        val COLUMNS = arrayOf(
            COL_ID,
            COL_NOMBRE
        )

        val CREATE_TABLE = "CREATE TABLE ${TABLE_NAME} (" +
                "${COL_ID} integer primary key autoincrement," +
                "${COL_NOMBRE} varchar(100) not null" +
                ")"

        val DROP_TABLE = "DROP TABLE ${TABLE_NAME}"
    }
}