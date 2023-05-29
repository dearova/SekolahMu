package id.co.komida.sekolahmu

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.android.material.textfield.TextInputLayout
import id.co.komida.sekolahmu.databinding.ActivityFormBinding
import java.time.Month
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

class FormActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {
   /* lateinit var btnSimpan : Button
    lateinit var etNamaDepan : EditText
    lateinit var etNamaBelakang : EditText
    lateinit var rbPria : RadioButton
    lateinit var rbWanita : RadioButton
    lateinit var arJenjang : Spinner
    lateinit var cbMembaca : CheckBox
    lateinit var cbMenulis : CheckBox
    lateinit var cbMenggambar : CheckBox
    lateinit var etAlamat : EditText
    lateinit var etNoHp : EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var tilNamaDepan: TextInputLayout
    private lateinit var tilNamaBelakang:TextInputLayout
    lateinit var etEmail: EditText
    lateinit var  etTglLhr: EditText*/

    lateinit var binding : ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_form)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        /*etNamaDepan = findViewById(R.id.etNamaDepan)
        etNamaBelakang = findViewById(R.id.etNamaBelakang)
        rbPria = findViewById(R.id.rbPria)
        rbWanita = findViewById(R.id.rbWanita)
        arJenjang = findViewById(R.id.arJenjang)
        cbMembaca = findViewById(R.id.cbMembaca)
        cbMenulis = findViewById(R.id.cbMenulis)
        cbMenggambar = findViewById(R.id.cbMenggambar)
        etAlamat = findViewById(R.id.etAlamat)
        etNoHp = findViewById(R.id.etNoHp)
        btnSimpan = findViewById(R.id.btSimpan)
        rgGender = findViewById(R.id.rbGroupGender)
        tilNamaDepan = findViewById(R.id.tilNamaDepan)
        tilNamaBelakang = findViewById(R.id.tilNamaBelakang)
        etEmail = findViewById(R.id.etEmail)
        etTglLhr = findViewById(R.id.etTglLahir)*/


        binding.etTglLahir.setOnClickListener{
            showDialogDate()
        }
        binding.btSimpan.setOnClickListener {

            jalankan()
        }
        title = buildString {
            append("Tambah Data")
        }
        val id = intent.getIntExtra("id",0)
        if(id > 0){

            val studentDataSource = StudentDataSource(this)
            val student = studentDataSource.getStudent(id)

            binding.etNamaDepan.setText(student!!.namaDepan!!)
            binding.etNamaBelakang.setText(student.namaBelakang!!)
            binding.etAlamat.setText(student.alamat!!)
           // etEmail.setText(student!!.email!!)
           // etTglLhr.setText(student!!.tglLahir!!)
            title = buildString {
                append("Edit Data")
            }
            binding.rbPria.isChecked = student.gender == binding.rbPria.text.toString()
            binding.rbWanita.isChecked = student.gender == binding.rbWanita.text.toString()

            val selectedPosition = (binding.arJenjang.adapter as ArrayAdapter<String>).getPosition(student.jenjang)
            binding.arJenjang.setSelection(selectedPosition)

            binding.cbMembaca.isChecked = student.hobi?.contains(binding.cbMembaca.text) ?: false
            binding.cbMenulis.isChecked = student.hobi?.contains(binding.cbMenulis.text) ?: false
            binding.cbMenggambar.isChecked = student.hobi?.contains(binding.cbMenggambar.text) ?: false


        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.form_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if (item.itemId == R.id.menuTambah) {
            Toast.makeText(this, "Tambah", Toast.LENGTH_LONG).show()
            return true
        }else
        if(item.itemId == android.R.id.home){


            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    private fun showDialogDate(){
        var calender = Calendar.getInstance()
        var datepick = DatePickerDialog(
            this,
            this,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DATE))
        datepick.show()


    }
    fun jalankan() {
        val strNamaDepan = binding.etNamaDepan.text.toString();
        val strNamaBelakang = binding.etNamaBelakang.text.toString();
        val strNoHP = binding.etNoHp.text.toString();
        val strAlamat = binding.etAlamat.text.toString();

        val hobbyList = ArrayList<String>()
        if (binding.cbMembaca.isChecked){
            hobbyList.add(binding.cbMembaca.text.toString())
        }

        if (binding.cbMenulis.isChecked){
            hobbyList.add(binding.cbMenulis.text.toString())
        }

        if (binding.cbMenggambar.isChecked){
            hobbyList.add(binding.cbMenggambar.text.toString())
        }
        val strGender = when(binding.rbGroupGender.checkedRadioButtonId){
            R.id.rbPria -> binding.rbPria.text.toString()
            R.id.rbWanita -> binding.rbWanita.text.toString()
            else -> ""
        }

        val strJenjang = binding.arJenjang.selectedItem.toString()

        val strHobby = TextUtils.join(", ", hobbyList)

        val message = "Hello $strNamaDepan $strNamaBelakang\n" +
                "No Hp : "+ strNoHP+ "\n" +
                "Gender : $strGender\n" +
                "Jenjang : $strJenjang\n" +
                "Hobi : $strHobby\n" +
                "Alamat : $strAlamat"
        binding.tilNamaDepan.error = null
        binding.tilNamaBelakang.error = null
        if(strNamaDepan.isEmpty()){
            //etNamaDepan.error = "Nama Depan Wajib"
            binding.tilNamaDepan.error = "Nama Depan Wajib"
            binding.etNamaDepan.requestFocus()
            return
        }
        if(strNamaBelakang.isEmpty()){
            //etNamaBelakang.error = "Nama Belakang Wajib"
            binding.tilNamaBelakang.error = "Nama Belakang Wajib"
            binding.etNamaBelakang.requestFocus()
            return
        }

        //simpan data
        val student = Student()
        student.namaDepan = strNamaDepan
        student.namaBelakang = strNamaBelakang
        student.noHp = strNoHP
        student.gender = strGender
        student.jenjang = strJenjang
        student.hobi = strHobby
        student.alamat = strAlamat
        val studentDataSource = StudentDataSource(this)

        val id = intent.getIntExtra("id", 0)
        if(id>0){
            //edit
            val success = studentDataSource.updateStudent(student, id)
            if (success){
                Toast.makeText(this, "Ubah data berhasil", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Ubah data gagal", Toast.LENGTH_LONG).show()
            }

        } else {
            val success = studentDataSource.insertStudent(student)
            if (success){
                Toast.makeText(this, "Simpan data berhasil", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Simpan data gagal", Toast.LENGTH_LONG).show()
            }
        }




    }

    override fun onDateSet(view:DatePicker?,year: Int, month: Int, dayOfMonth: Int) {
        val tgl = "${dayOfMonth} - ${month+1} - $year"
        binding.etTglLahir.setText(tgl)
    }

}