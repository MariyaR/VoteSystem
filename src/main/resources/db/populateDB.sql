DELETE FROM user_roles;
DELETE FROM restos;
DELETE FROM users;
DELETE FROM restos_history;
DELETE FROM voting_history;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User1', 'user1@yandex.ru', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin'),
  ('User2', 'user2@yandex.ru', '{noop}password'),
  ('User3', 'user3@yandex.ru', '{noop}password');


INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003);

INSERT INTO restos (name, address) VALUES
    ('resto1', 'address1'),
    ('resto2', 'address2'),
    ('resto3', 'address3');

INSERT INTO restos_history (date, resto_id, resto_menu, counter) VALUES
    (CURRENT_DATE - INTERVAL '1' MONTH, 100004, '{"burger":"10000","cake":"500"}', 0),
    (CURRENT_DATE - INTERVAL '2' MONTH, 100004, '{"burger":"10000","cake":"500"}', 0),
    (CURRENT_DATE - INTERVAL '3' MONTH, 100004, '{"burger":"10000","cake":"500"}', 0),
    (CURRENT_DATE - INTERVAL '1' MONTH, 100005, '{"pasta":"12000","ice-cream":"500"}', 0),
    (CURRENT_DATE - INTERVAL '2' MONTH, 100005, '{"pasta":"12000","ice-cream":"500"}', 0),
    (CURRENT_DATE - INTERVAL '3' MONTH, 100005, '{"pasta":"12000","ice-cream":"500"}', 0),
    (CURRENT_DATE - INTERVAL '1' MONTH, 100006, '{"sushi":"13000","cheese-cake":"500"}', 0),
    (CURRENT_DATE - INTERVAL '2' MONTH, 100006, '{"sushi":"13000","cheese-cake":"500"}', 0),
    (CURRENT_DATE - INTERVAL '3' MONTH, 100006, '{"sushi":"13000","cheese-cake":"500"}', 0);

INSERT INTO voting_history (date, user_id, resto_id) VALUES
    ('2000-01-01', 100000, 100004),
    ('2000-01-01', 100001, 100005),
    ('2000-01-01', 100002, 100004),
    ('2000-01-01', 100003, 100006),
    ('2000-01-02', 100000, 100005),
    ('2000-01-02', 100001, 100005),
    ('2000-01-02', 100002, 100004),
    ('2000-01-02', 100003, 100006);
