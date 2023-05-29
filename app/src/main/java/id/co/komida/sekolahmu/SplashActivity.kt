package id.co.komida.sekolahmu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    lateinit var imgv : ImageView
    lateinit var txtv : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        txtv = findViewById(R.id.versi)
        txtv.text = "Version ${BuildConfig.VERSION_CODE}"

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            nextpage()
        }, 3000)
    }

    private fun nextpage(){
        val inte = Intent(this, Logins::class.java)
        startActivity(inte)
        finish()
    }
}
