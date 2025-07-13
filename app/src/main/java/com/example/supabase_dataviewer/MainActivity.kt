package com.example.supabase_dataviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var supabaseUrlEditText: EditText
    private lateinit var supabaseKeyEditText: EditText
    private lateinit var connectButton: Button

    private var supabaseClient: SupabaseClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supabaseUrlEditText = findViewById(R.id.supabase_url)
        supabaseKeyEditText = findViewById(R.id.supabase_key)
        connectButton = findViewById(R.id.login_button)

        connectButton.setOnClickListener {
            val supabaseUrl = supabaseUrlEditText.text.toString()
            val supabaseKey = supabaseKeyEditText.text.toString()

            if (supabaseUrl.isNotBlank() && supabaseKey.isNotBlank()) {
                lifecycleScope.launch {
                    try {
                        supabaseClient = createSupabaseClient(
                            supabaseUrl = supabaseUrl,
                            supabaseKey = supabaseKey
                        ) {
                            install(GoTrue)
                            install(Postgrest)
                        }
                        Toast.makeText(this@MainActivity, "Connection successful!", Toast.LENGTH_SHORT).show()
                        // TODO: Fetch and display data
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Connection failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this@MainActivity, "Please enter Supabase URL and Key", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
