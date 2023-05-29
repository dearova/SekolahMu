package id.co.komida.sekolahmu

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class StudentDataSource(context: Context) {
    private var database: SQLiteDatabase? = null
    private val helper: DatabaseHelper = DatabaseHelper(context)

    private fun open(){
        database = helper.writableDatabase
    }
    private fun close(){
        helper.close()
    }
    fun insertStudent(student: Student): Boolean {
        open()

        val cv = createContentValuesFrom(student)
        val id = database?.insert("student", null, cv)

        return id != null && id > 0
    }

    private fun createContentValuesFrom(student: Student): ContentValues? {
        val cv = ContentValues()
        cv.put("nama_depan", student.namaDepan)
        cv.put("nama_belakang", student.namaBelakang)
        cv.put("no_hp", student.noHp)
        cv.put("gender", student.gender)
        cv.put("jenjang", student.jenjang)
        cv.put("hobi", student.hobi)
        cv.put("alamat", student.alamat)
        return cv
    }

    private fun updateNama(student: Student): ContentValues? {
        val cv = ContentValues()
        cv.put("nama_depan", student.namaDepan)
        cv.put("nama_belakang", student.namaBelakang)

        return cv
    }
    fun fetchRow(cursor: Cursor): Student{
        val student = Student()
        student.id = cursor.getInt(0)
        student.namaDepan = cursor.getString(1)
        student.namaBelakang = cursor.getString(2)
        student.noHp = cursor.getString(3)
        student.gender = cursor.getString(4)
        student.jenjang = cursor.getString(5)
        student.hobi = cursor.getString(6)
        student.alamat = cursor.getString(7)
        return student
    }

    fun getAll(): ArrayList<Student>{
        val studentList = ArrayList<Student>()

        //ambil data dari database
        open()

        val cek: Cursor? = database?.rawQuery("SELECT * FROM student", null)
        cek?.let {
            cek.moveToFirst()
            while (!cek.isAfterLast){
                val student = fetchRow(it)
                studentList.add(student)
                cek.moveToNext()
            }
            cek.close()
        }

        close()

        return studentList
    }
    fun getStudent(id: Int): Student? {
        open()
        val selectQuery = "SELECT * FROM student WHERE id=$id"
        val cursor = database?.rawQuery(selectQuery, null)

        var student: Student? = null

        cursor?.let {
            it.moveToFirst()
            student = fetchRow(it)
            it.close()
        }
        close()

        return student
    }
    fun search(keyword: String): ArrayList<Student>{
        open()
        val studentList = ArrayList<Student>()

        //get list of student from database
        val searchQuery = "SELECT * FROM student WHERE nama_depan LIKE ? OR nama_belakang LIKE ?"
        val cursor = database?.rawQuery(searchQuery, arrayOf("%$keyword%", "%$keyword%"))

        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast){
                val student = fetchRow(it)
                studentList.add(student)
                it.moveToNext()
            }
            it.close()
        }

        close()
        return studentList
    }
    fun removeStudent(id: Int): Boolean {
        open()

        //delete studen by id
        val affectedRow = database?.delete("student", "id=?", arrayOf("$id"))

        close()
        return affectedRow != null && affectedRow > 0
    }
    fun updateStudent(student: Student, id: Int): Boolean {
        open()

        val cv = createContentValuesFrom(student)
        val cv2 = updateNama(student)
        val affectedRow = database?.update("student", cv2, "id=?", arrayOf("$id"))

        close()

        return affectedRow != null && affectedRow > 0
    }
}