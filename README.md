Vote System 
===============================
Модель:

User - информация о юзере

Role - информация о ролях

Resto - информация о ресторане, адрес и название

Record - запись о голосовании, хранится проголосовавший юзер, дата голосования и ресторан, за который он проголосовал

DayMenu - дневное меню, здесь хранится дата, ресторан, которому принадлежит меню, само меню в виде мапы с названием и ценой блюд, а также счетчик голосов в этот день за это меню.

Организация базы данных:

Таблица users - данные о юзере, соответствует классу  User 

Таблица user_roles - роли юзера

Таблица restos - данные ресторана, соответствует классу Resto

Таблица restos_history - история ресторанов, соответствует классу DayMenu

Таблица voting_history - история голосований, соответсвует классу Record

При создании нового меню для какого-то ресторана просто создается новая запись в таблице restos_history.
При голосовании изменяется запись в таблице restos_history (меняется счетчик), а также заносится запись в таблицу с историей голосований.

Контроллеры:

Все контроллеры наследуются от базового AbstractController. 
UserController и AdminController еще имеют родителем промежуточный AbstractUserController

UserController:
доступ - авторизованный юзер

get() - получить авторизованного юзера

delete() - удалить авторизованного юхера

register(UserTo userTo) - зарегистрироваться

update(UserTo userTo) - изменить данные авторизованного юзера

AdminController:

доступ - админ

get(int id) - получить юзера по id

createWithLocation(UserTo userTo) - создать новго юзера

delete(int id) - удалить юзера по id

update(UserTo userTo, int id) - измениь данные юзера по id

getByMail(String email) - получить юзера по email

RestoAdminController:
доступ - админ

getAll() - получить список ресторанов

get(int id) - получить ресторан по id

createWithLocation(RestoTo restoTo) - создать новый ресторан

delete(int id) - удалить ресторан по id

update( RestoTo restoTo,  int id) - редактировать данные о ресторане

Resto getWithHistory(int id) - получить ресторан с историей его меню

RecordController:
доступ - авторизованный юзер

get(int id) - получить запись по id, если она принадлежит авторизованному юзеру

getAll() - получить все записи авторизованного юзера

DayMenuUserController:
доступ - авторизованный юзер

getTodayMenus() - получить список сегоднящних меню с данными о ресторанах

voteForMenu(int menuId) - проголосовать за меню, переголосовать можоно до 11.00

DayMenuAdminController:
доступ - админ

getAll(int restoId) - получить историю ресторана по его id

get(int id) - получить дневное меню по его id 

createWithLocation(DayMenuTo dayMenuTo, int restoId) - создать новое дневное меню для ресторана с restoId

delete(int id) - удалить меню по id

update(DayMenuTo dayMenuTo, int restoId) - редактировать меню