package br.infnet.marianabs.mybeautynotes.ui


import android.content.ActivityNotFoundException
import android.content.Intent

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.infnet.marianabs.mybeautynotes.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val btnMoveBrowser: Button = findViewById(R.id.mygithub)
        btnMoveBrowser.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/marianabsctba"))
            startActivity(intent)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                intent.setPackage(null)
                Toast.makeText(this, "No browser in your device", Toast.LENGTH_SHORT).show()
            }
        }

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, IntroSliderActivity::class.java))
            finish()
        }, 6000)


    }
}



