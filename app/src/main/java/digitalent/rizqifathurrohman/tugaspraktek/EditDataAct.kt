package digitalent.rizqifathurrohman.tugaspraktek

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import digitalent.rizqifathurrohman.tugaspraktek.model.User
import digitalent.rizqifathurrohman.tugaspraktek.repository.UserRepository

class EditDataAct : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editAddress: EditText
    private lateinit var editKelamin: EditText
    private lateinit var editNpm: EditText
    private lateinit var editTtl: EditText
    private lateinit var btnBack: ImageButton
    private lateinit var btnSubmit: Button
    private lateinit var userRepository: UserRepository
    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_edit_data)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userRepository = UserRepository(this)
        userId = intent.getLongExtra("USER_ID", -1)
        if (userId == -1L) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initializeViews()
        initializeOnClickListeners()
        loadUserData()
    }

    private fun initializeViews() {
        editName = findViewById(R.id.editName)
        editAddress = findViewById(R.id.editAddress)
        editKelamin = findViewById(R.id.editKelamin)
        editNpm = findViewById(R.id.editNpm)
        editTtl = findViewById(R.id.editTtl)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun initializeOnClickListeners() {
        btnSubmit.setOnClickListener {
            val name = editName.text.toString().trim()
            val domisili = editAddress.text.toString().trim()
            val kelamin = editKelamin.text.toString().trim()
            val npm = editNpm.text.toString().trim()
            val ttl = editTtl.text.toString().trim()

            if (name.isEmpty() || domisili.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                updateDataToSQLite(userId, name, domisili, kelamin, npm, ttl)
                Toast.makeText(this, "User data updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadUserData() {
        userRepository.open()
        val user = userRepository.getUserById(userId)
        userRepository.close()

        if (user != null) {
            editName.setText(user.name)
            editAddress.setText(user.domisili)
            editKelamin.setText(user.kelamin)
            editNpm.setText(user.npm)
            editTtl.setText(user.ttl)
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateDataToSQLite(id: Long, name: String, domisili: String, kelamin : String, npm : String, ttl : String) {
        userRepository.open()
        userRepository.updateUser(id, name, domisili, kelamin, npm, ttl)
        userRepository.close()
    }
}
