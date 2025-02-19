# QuartzTaskManualAdder

QuartzTaskManualAdder — это Spring Boot приложение на Kotlin, предназначенное для управления задачами с использованием Quartz Scheduler.

## ߚ Возможности
- Добавление, редактирование и удаление задач в Quartz Scheduler.
- Управление расписанием через REST API.
- Поддержка хранения задач в базе данных.
- Логирование и обработка событий выполнения задач.

## ߛ️ Установка и запуск
### Требования
- Java 21
- Maven 3+
- PostgreSQL (или другая поддерживаемая БД)

### ߔ Настройка
1. Склонируйте репозиторий:
   ```sh
   git clone https://github.com/your-repo/QuartzTaskManualAdder.git
   cd QuartzTaskManualAdder
   ```
2. Настройте `application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/your_db
       username: your_user
       password: your_password
   ```
3. Соберите и запустите приложение:
   ```sh
   ./mvnw spring-boot:run
   ```

## ߓ API примеры
Приложение предоставляет REST API для управления задачами.

## ߓ Лицензия
## ✨ Автор
Разработано [Нодиром](https://t.me/sirojiddinoovv).
