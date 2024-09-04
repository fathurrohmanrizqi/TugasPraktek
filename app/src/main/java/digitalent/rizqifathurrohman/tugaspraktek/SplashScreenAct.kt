package digitalent.rizqifathurrohman.tugaspraktek

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenAct : AppCompatActivity() {
    private val splashScreenDuration = 3000L // 3 seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Start a coroutine for the delay
        CoroutineScope(Dispatchers.Main).launch {
            delay(splashScreenDuration)
            startActivity(Intent(this@SplashScreenAct, LoginAct::class.java))
            finish()
        }
    }
}