package digitalent.rizqifathurrohman.tugaspraktek

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import digitalent.rizqifathurrohman.tugaspraktek.model.User
import digitalent.rizqifathurrohman.tugaspraktek.repository.UserRepository

class DataMahasiswa : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var itemListNameAdapter: ItemListAct? = null
    private var userRepository: UserRepository? = null
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_data_mahasiswa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initalizeViews()
    }

    private fun initalizeViews() {
        btnBack = findViewById(R.id.btnBack)
        recyclerView = findViewById(R.id.recyclerViewItemName)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        initializeOnClickListeners()
    }

    private fun initializeOnClickListeners() {
        btnBack.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
    }

    private fun loadUserData() {
        userRepository = UserRepository(this)
        userRepository?.open()

        val userList = userRepository?.allUsers ?: emptyList()
        if (itemListNameAdapter == null) {
            itemListNameAdapter = ItemListAct(userList, object : ItemListAct.OnItemLongClickListener {
                override fun onItemLongClick(user: User?) {
                    user?.let { showOptionsDialog(it) }
                }
            })
            recyclerView?.adapter = itemListNameAdapter
        } else {
            itemListNameAdapter?.updateUserList(userList)
            itemListNameAdapter?.notifyDataSetChanged()
        }

        userRepository?.close()
    }

    private fun showOptionsDialog(user: User) {
        Log.d("MainActivity", "showOptionsDialog called for user: ${user.name}")
        AlertDialog.Builder(this)
            .setItems(arrayOf("Update Data", "Detail Data", "Delete")) { dialog: DialogInterface?, which: Int ->
                when (which) {
                    0 -> {
                        val intent = Intent(this@DataMahasiswa, EditDataAct::class.java)
                        intent.putExtra("USER_ID", user.id)
                        startActivity(intent)
                    }
                    1 ->{
                        val intent = Intent(this@DataMahasiswa, DetailDataAct::class.java)
                        intent.putExtra("USER_ID", user.id)
                        startActivity(intent)
                    }
                    2 -> deleteUser(user)
                }
            }
            .show()
    }

    private fun deleteUser(user: User) {
        userRepository?.open()
        userRepository?.deleteUser(user.id)
        userRepository?.close()
        loadUserData()
        Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show()
    }
}