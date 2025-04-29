package sv.edu.udb.fichaseducativas.models

class Ficha {
    var Id : Int = 0
    var IdTematica : Int = 0
    var Titulo : String = ""
    var DescripcionCara : String = ""
    var DescripcionAtras : String = ""
    var Imagen : String? = ""

    constructor(
        _id : Int,
        _idTematica : Int,
        _titulo : String,
        _descripcionCara : String,
        _descripcionAtras : String,
        _imagen : String?
    ){
        Id = _id
        IdTematica = _idTematica
        Titulo = _titulo
        DescripcionCara = _descripcionCara
        DescripcionAtras = _descripcionAtras
        Imagen = _imagen
    }

    companion object{
        val TABLE_NAME = "ficha"

        val COL_ID = "id"
        val COL_ID_TEMATICA = "id_tematica"
        val COL_TITULO = "titulo"
        val COL_DESCRIPCION_CARA = "descripcion_cara"
        val COL_DESCRIPCION_ATRAS = "descripcion_atras"
        val COL_IMAGEN = "imagen"

        val COLUMNS = arrayOf(
            COL_ID,
            COL_ID_TEMATICA,
            COL_TITULO,
            COL_DESCRIPCION_CARA,
            COL_DESCRIPCION_ATRAS,
            COL_IMAGEN
        )

        val CREATE_TABLE = "CREATE TABLE ${TABLE_NAME} (" +
                "${COL_ID} integer primary key autoincrement," +
                "${COL_ID_TEMATICA} int not null," +
                "${COL_TITULO} varchar(100) not null," +
                "${COL_DESCRIPCION_CARA} varchar(100) not null," +
                "${COL_DESCRIPCION_ATRAS} varchar(100) not null," +
                "${COL_IMAGEN} varchar(100) null," +
                "FOREIGN KEY (${COL_ID_TEMATICA}) REFERENCES ${Tematica.TABLE_NAME}(${Tematica.COL_ID})" +
                ")"

        val DROP_TABLE = "DROP TABLE ${TABLE_NAME}"
    }
}