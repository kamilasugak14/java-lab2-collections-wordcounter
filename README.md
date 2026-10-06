# Word Counter (Gradle + Java Collections)

Лабораторна робота №2 з дисципліни «Технології розробки на платформі Java»
(КПІ ім. Ігоря Сікорського).

**Тема:** збірка проєкту за допомогою Gradle та робота з Java Collections Framework.

## Опис проєкту

Проєкт реалізує частотний аналізатор тексту `WordCounter` у пакеті
`ua.kpi.comsys.collections`. Використовуються лише стандартні колекції `java.util.*`,
сторонні бібліотеки для колекцій відсутні.

| Метод | Опис |
|-------|------|
| `Map<String, Integer> countWords(List<String> textLines)` | кількість входжень кожного слова |
| `Set<String> getUniqueWords(List<String> textLines)` | множина унікальних слів |

### Правила обробки тексту

- Словом вважається послідовність буквено-цифрових символів латиниці (`a-z`, `A-Z`, `0-9`).
  Усі інші символи (пробіли, розділові знаки, апостроф, дефіс, підкреслення, кирилиця
  та інші нелатинські літери) є роздільниками.
- Усі слова приводяться до нижнього регістру.
- Порожні слова після розбиття ігноруються.
- Якщо `textLines == null`, обидва методи кидають
  `IllegalArgumentException("textLines cannot be null")`.
- Елементи списку, що дорівнюють `null`, пропускаються.

Приклад: `["Hello, world!", "hello again"]` дає
`{hello=2, world=1, again=1}`.

## Варіант

Виконано **варіант 2** (частотний аналізатор тексту, клас `WordCounter`).
Варіант обрано за погодженням з викладачем.

## Gradle та Version Catalog

- Збірка налаштована у `build.gradle.kts`, ім'я проєкту - у `settings.gradle.kts`.
- Версії залежностей винесені в каталог `gradle/libs.versions.toml`
  (JUnit 5 підключається через BOM `org.junit:junit-bom`).
- Використовується Gradle Wrapper (Gradle 8.10.2), окремо встановлювати Gradle не потрібно.

## Вимоги

- JDK 17 або новіший (розроблено на JDK 21)
- Інтернет під час першого запуску (Wrapper завантажує Gradle, Gradle - залежності)

## Збірка та запуск тестів

Windows (PowerShell або cmd):

```
.\gradlew.bat build
.\gradlew.bat test
```

Linux / macOS:

```
./gradlew build
./gradlew test
```

Звіт про результати тестів: `build/reports/tests/test/index.html`.
Звіт про покриття (JaCoCo): `build/reports/jacoco/test/html/index.html`.

## Структура

```
build.gradle.kts
settings.gradle.kts
gradle/libs.versions.toml
gradle/wrapper/
src/main/java/ua/kpi/comsys/collections/WordCounter.java
src/test/java/ua/kpi/comsys/collections/WordCounterTest.java
```

## Автор

Сугак Каміла Романівна, група ІП-42
