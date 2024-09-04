package digitalent.rizqifathurrohman.tugaspraktek

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import digitalent.rizqifathurrohman.tugaspraktek.database.DatabaseHelper

class AddDataAct : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editAddress: EditText
    private lateinit var editNpm: EditText
    private lateinit var editTtl: EditText
    private lateinit var editKelamin: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(left = systemBars.left, top = systemBars.top, right = systemBars.right, bottom = systemBars.bottom)
            insets
        }

        initializeViews()
        initializeOnClickListeners()
    }

    private fun initializeViews() {
        editName = findViewById(R.id.editName)
        editAddress = findViewById(R.id.editAddress)
        editNpm = findViewById(R.id.editNpm)
        editTtl = findViewById(R.id.editTtl)
        editKelamin = findViewById(R.id.editKelamin)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun initializeOnClickListeners() {
        btnSubmit.setOnClickListener {
            val name = editName.text.toString().trim()
            val domisili = editAddress.text.toString().trim()
            val npm = editNpm.text.toString().trim()
            val ttl = editTtl.text.toString().trim()
            val kelamin = editKelamin.text.toString().trim()

            if (name.isEmpty() || domisili.isEmpty() || npm.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                insertDataToSQLite(name, npm, ttl, domisili, kelamin)
                Toast.makeText(this, "User data submitted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun insertDataToSQLite(name: String, npm: String, ttl: String, domisili: String, kelamin: String) {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, name)
            put(DatabaseHelper.COLUMN_NPM, npm)
            put(DatabaseHelper.COLUMN_TTL, ttl)
            put(DatabaseHelper.COLUMN_DOMISILI, domisili)
            put(DatabaseHelper.COLUMN_KELAMIN, kelamin)
        }

        db.insert(DatabaseHelper.TABLE_USER, null, values)
        db.close()
    }
}