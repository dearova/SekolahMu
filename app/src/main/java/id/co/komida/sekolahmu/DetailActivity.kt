package id.co.komida.sekolahmu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("id",0)

        val studentDataSource = StudentDataSource(this)
        val student = studentDataSource.getStudent(id)

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvNoHp = findViewById<TextView>(R.id.tvNoHp)
        val tvGender = findViewById<TextView>(R.id.tvGender)
        val tvGrade = findViewById<TextView>(R.id.tvGrade)
        val tvHobi = findViewById<TextView>(R.id.tvHobi)
        val tvAlamat = findViewById<TextView>(R.id.tvAlamat)

        tvName.text = "${student?.namaDepan} ${student?.namaBelakang}"
        tvNoHp.text = student?.noHp
        tvGender.text = student?.gender
        tvGrade.text = student?.jenjang
        tvHobi.text = student?.hobi
        tvAlamat.text = student?.alamat
    }
}