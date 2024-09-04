package digitalent.rizqifathurrohman.tugaspraktek

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import digitalent.rizqifathurrohman.tugaspraktek.repository.UserRepository

class DetailDataAct : AppCompatActivity() {
    private lateinit var textName: EditText
    private lateinit var textAddress: EditText
    private lateinit var textKelamin: EditText
    private lateinit var textNpm: EditText
    private lateinit var textTtl: EditText
    private lateinit var btnBack: ImageButton
    private lateinit var userRepository: UserRepository
    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_data)
        userRepository = UserRepository(this)
        userId = intent.getLongExtra("USER_ID", -1)
        if (userId == -1L) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initializeViews()
        loadUserData()
        initializeOnClickListeners()
    }

    private fun initializeViews() {
        textName = findViewById(R.id.fieldName)
        textAddress = findViewById(R.id.fieldAddress)
        textKelamin = findViewById(R.id.fieldKelamin)
        textNpm = findViewById(R.id.fieldNpm)
        textTtl = findViewById(R.id.fieldTtl)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun loadUserData() {
        userRepository.open()
        val user = userRepository.getUserById(userId)
        userRepository.close()

        if (user != null) {
            textName.setText(user.name)
            textAddress.setText(user.domisili)
            textKelamin.setText(user.kelamin)
            textNpm.setText(user.npm)
            textTtl.setText(user.ttl)
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initializeOnClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}
