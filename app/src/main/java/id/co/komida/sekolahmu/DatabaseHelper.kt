package id.co.komida.sekolahmu

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, "sekolahku.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableScript = "CREATE TABLE student (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nama_depan TEXT," +
                "nama_belakang TEXT," +
                "no_hp TEXT," +
                "gender TEXT," +
                "jenjang TEXT," +
                "hobi TEXT," +
                "alamat TEXT,"+
                "email TEXT,"+
                "tanggal_lahir TEXT)"
        db?.execSQL(createTableScript)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}