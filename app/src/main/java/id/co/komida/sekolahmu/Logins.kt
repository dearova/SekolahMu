package id.co.komida.sekolahmu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

class Logins : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logins)
        val login = findViewById<View>(R.id.login)
        //form.visibility = View.INVISIBLE


        login.setOnClickListener {

            jalankan()
        }
    }

    private fun jalankan() {
        val etUser = findViewById<EditText>(R.id.input)
        val etPassw = findViewById<EditText>(R.id.inputpassowrd)
        val username = etUser.text.toString()
        val passw = etPassw.text.toString()
        if(username == "admin" && passw == "admin"){
                val inte = Intent(this, ListActivity::class.java)
                startActivity(inte)
                finish()
            }else{
            Toast.makeText(this, "Salah donk", Toast.LENGTH_LONG).show()
        }
    }
}