package digitalent.rizqifathurrohman.tugaspraktek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnLihatData : Button
    private lateinit var btnInputData : Button
    private lateinit var btnInfo : Button
    private lateinit var btnLogout : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeBtn()
        setOnClickListener()
    }
    private fun initializeBtn(){
        btnLihatData = findViewById(R.id.btnLihatData)
        btnInputData = findViewById(R.id.btnInputData)
        btnInfo = findViewById(R.id.btnInfo)
        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun setOnClickListener(){
        btnLihatData.setOnClickListener {
            intent = Intent(this, DataMahasiswa::class.java)
            startActivity(intent)
        }
        btnInputData.setOnClickListener {
            intent = Intent(this, AddDataAct::class.java)
            startActivity(intent)
        }
        btnInfo.setOnClickListener {
            intent = Intent(this, InfoAct::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Selamat tinggal!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}