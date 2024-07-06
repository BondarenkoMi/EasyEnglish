package ru.itis.easyenglish.theory

class WordRepository {
    private val words = listOf(
        Word("hello", "привет", 1),
        Word("goodbye", "до свидания", 1),
        Word("please", "пожалуйста", 1),
        Word("thank you", "спасибо", 1),
        Word("yes", "да", 1),
        Word("no", "нет", 1),
        Word("dog", "собака", 1),
        Word("cat", "кошка", 1),
        Word("book", "книга", 1),
        Word("water", "вода", 1),

        Word("teacher", "учитель", 2),
        Word("student", "студент", 2),
        Word("house", "дом", 2),
        Word("car", "машина", 2),
        Word("family", "семья", 2),
        Word("morning", "утро", 2),
        Word("afternoon", "день", 2),
        Word("evening", "вечер", 2),
        Word("important", "важный", 2),
        Word("difficult", "трудный", 2),

        Word("adventure", "приключение", 3),
        Word("knowledge", "знание", 3),
        Word("opportunity", "возможность", 3),
        Word("experience", "опыт", 3),
        Word("freedom", "свобода", 3),
        Word("situation", "ситуация", 3),
        Word("communication", "общение", 3),
        Word("environment", "окружение", 3),
        Word("health", "здоровье", 3),
        Word("education", "образование", 3),

        Word("complicated", "сложный", 4),
        Word("significant", "значительный", 4),
        Word("strategy", "стратегия", 4),
        Word("development", "развитие", 4),
        Word("perspective", "перспектива", 4),
        Word("challenge", "вызов", 4),
        Word("consequence", "последствие", 4),
        Word("relationship", "взаимоотношения", 4),
        Word("achievement", "достижение", 4),
        Word("opinion", "мнение", 4),
    )

    fun getWords(): List<Word> {
        return words
    }
}