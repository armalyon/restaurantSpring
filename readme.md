Варіант №21
Система Ресторан. Клієнт робить замовлення з меню.
Адміністратор підтверджує замовлення і відправляє його на кухню для виконання.
Адміністратор виставляє рахунок. Клієнт робить його оплату

1. База даних  - MySql
2. Версія Java 8
3. Maven

Як встановити:

Clone project
Запустити schema.sql що знаходиться в папці resources/ 
Запустити populate.sql що знаходиться в папці resources/ 
Обновити логін і пароль в файлі resources/application.properties
Запустити Spring проект
Перейти за посиланням localhost:8080/

Бізнес логіка:

Користувач може залогінитися як клієнт або адміністратор. - LoginController (POST)
Клієнт може зареєструватися на сайті. - RegistrstionController (POST)
Клієнт може бачити сторінку з меню і замовленням в цей день. - ClientPageController (GET)
Адміністратор може бачити сторінку з меню і кількістю доступних елементів меню AdminPageController (GET)
Клієнт може зробити замовлення. - ClientPageController (POST)
Клієнт може перейти на сторінку своїх замовлень. - ClientOrdersController (GET)
Адміністратор може перейти на сторінку всіх замовлень - AdminConfirmationController (GET)
Адміністратор може підтвердити замовлення - AdminConfirmationController (POST)
Адміністратор може відмовити замовлення - AdminConfirmation (POST)
Адміністратор може перейти на сторінку підтвердженних замовлень - AdminConfirmedController (GET)
Адміністратор може Виставити рахунок по замовленню - AdminConfirmerController (POST)
Клієнт може перейти на сторінку своїх рахунків - ClientBillsController (GET)
Клієнт може сплатити рахунок (перевести гроші на рахунок адміністратора) - ClientBillsController (POST)
Адміністратор може перейти на сторінку зі списком всіх клієнтів - AdminStatsPageController (GET) 
Адміністратор може перейти на сторінку зі статистикою по клієнту - AdminClientStatsController(GET) 
Користувач може розлогінитися. LogoutCommand (POST)