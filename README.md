QuartzTaskManualAdder

QuartzTaskManualAdder — это Spring Boot приложение на Kotlin, предназначенное для управления задачами с использованием Quartz Scheduler.

🚀 Возможности

Добавление, редактирование и удаление задач в Quartz Scheduler.

Управление расписанием через REST API.

Поддержка хранения задач в базе данных.

Логирование и обработка событий выполнения задач.

🛠️ Установка и запуск

Требования

Java 21

Maven 3+

PostgreSQL (или другая поддерживаемая БД)

🔧 Настройка

Склонируйте репозиторий:

git clone https://github.com/your-repo/QuartzTaskManualAdder.git
cd QuartzTaskManualAdder

Настройте application.yml:

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db
    username: your_user
    password: your_password

Соберите и запустите приложение:

./mvnw spring-boot:run

📌 API примеры

Приложение предоставляет REST API для управления задачами.

✨ Автор

Разработано Нодиром.
