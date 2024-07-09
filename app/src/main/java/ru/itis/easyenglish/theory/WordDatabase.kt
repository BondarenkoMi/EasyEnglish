package ru.itis.easyenglish.theory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создаем новую таблицу с новым первичным ключом и правильным порядком столбцов
        database.execSQL("CREATE TABLE words_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, englishWord TEXT NOT NULL, russianWord TEXT NOT NULL, level INTEGER, completedStatus INTEGER, savedStatus INTEGER)")

        // Копируем данные из старой таблицы в новую
        database.execSQL("INSERT INTO words_new (englishWord, russianWord, level, completedStatus, savedStatus) SELECT englishWord, russianWord, level, completedStatus, savedStatus FROM words")

        // Удаляем старую таблицу
        database.execSQL("DROP TABLE words")

        // Переименовываем новую таблицу в старую
        database.execSQL("ALTER TABLE words_new RENAME TO words")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Добавляем уникальный индекс на поле englishWord
        database.execSQL("CREATE UNIQUE INDEX index_words_englishWord ON words(englishWord)")
    }
}

@Database(entities = [WordEntity::class], version = 3)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}