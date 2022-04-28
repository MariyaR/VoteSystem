Vote System 
===============================
application for voting for the restaurant to have today lunch. Each user can vote and revote until 11.00 o'clock.
2 roles are available: user and admin. 

login: admin@gmail.com
password: admin

the application is run via cargo-plugin, cargo:run

some possible requests: 

localhost:8080/VoteSystem/admin/restos - get all restaurants
localhost:8080/VoteSystem/admin/restos/100004 - get restaurant by id
localhost:8080/VoteSystem/admin/today-menus?restoId=100004 - get today menu by restaurant id
localhost:8080/VoteSystem/admin/users - get all users
localhost:8080/VoteSystem/admin/users/by?email=user1@yandex.ru - get user by email
localhost:8080/VoteSystem/admin/users/100000 - get user by id
localhost:8080/VoteSystem/profile - get logged user
localhost:8080/VoteSystem/profile/history - get voting hystory of logged user

swagger documentation:
http://localhost:8080/VoteSystem/v2/api-docs