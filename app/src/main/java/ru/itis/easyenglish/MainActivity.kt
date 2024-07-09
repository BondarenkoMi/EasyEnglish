package ru.itis.easyenglish

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.easyenglish.theory.WordEntity
import ru.itis.easyenglish.databinding.ActivityMainBinding
import ru.itis.easyenglish.theory.WordDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                call()
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.setupWithNavController(navController)
    }
    private suspend fun call() {
        val words = listOf(
            WordEntity(0, "hello", "привет", 1, null, false),
            WordEntity(0, "goodbye", "до свидания", 1, null, false),
            WordEntity(0, "please", "пожалуйста", 1, null, false),
            WordEntity(0, "thank you", "спасибо", 1, null, false),
            WordEntity(0, "yes", "да", 1, null, false),
            WordEntity(0, "no", "нет", 1, null, false),
            WordEntity(0, "dog", "собака", 1, null, false),
            WordEntity(0, "cat", "кошка", 1, null, false),
            WordEntity(0, "book", "книга", 1, null, false),
            WordEntity(0, "water", "вода", 1, null, false),

            WordEntity(0, "teacher", "учитель", 2, null, false),
            WordEntity(0, "student", "студент", 2, null, false),
            WordEntity(0, "house", "дом", 2, null, false),
            WordEntity(0, "car", "машина", 2, null, false),
            WordEntity(0, "family", "семья", 2, null, false),
            WordEntity(0, "morning", "утро", 2, null, false),
            WordEntity(0, "afternoon", "день", 2, null, false),
            WordEntity(0, "evening", "вечер", 2, null, false),
            WordEntity(0, "important", "важный", 2, null, false),
            WordEntity(0, "difficult", "трудный", 2, null, false),

            WordEntity(0, "adventure", "приключение", 3, null, false),
            WordEntity(0, "knowledge", "знание", 3, null, false),
            WordEntity(0, "opportunity", "возможность", 3, null, false),
            WordEntity(0, "experience", "опыт", 3, null, false),
            WordEntity(0, "freedom", "свобода", 3, null, false),
            WordEntity(0, "situation", "ситуация", 3, null, false),
            WordEntity(0, "communication", "общение", 3, null, false),
            WordEntity(0, "environment", "окружение", 3, null, false),
            WordEntity(0, "health", "здоровье", 3, null, false),
            WordEntity(0, "education", "образование", 3, null, false),

            WordEntity(0, "complicated", "сложный", 4, null, false),
            WordEntity(0, "significant", "значительный", 4, null, false),
            WordEntity(0, "strategy", "стратегия", 4, null, false),
            WordEntity(0, "development", "развитие", 4, null, false),
            WordEntity(0, "perspective", "перспектива", 4, null, false),
            WordEntity(0, "challenge", "вызов", 4, null, false),
            WordEntity(0, "consequence", "последствие", 4, null, false),
            WordEntity(0, "relationship", "взаимоотношения", 4, null, false),
            WordEntity(0, "achievement", "достижение", 4, null, false),
            WordEntity(0, "opinion", "мнение", 4, null, false)
        )

        WordDatabase.getDatabase(this).wordDao().insertAll(words)
        val list: List<WordEntity> = WordDatabase.getDatabase(this).wordDao().getAllWords()
        Log.e("TAG123", list.toString())
    }
}
