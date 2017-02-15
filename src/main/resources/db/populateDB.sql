DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM menu;
DELETE FROM users;
DELETE FROM restaurants;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User 1', 'user1@ya.ru', 'user1'),
  ('Admin', 'admin@ya.ru', 'admin'),
  ('User 2', 'user2@ya.ru', 'user2'),
  ('User 3', 'user3@ya.ru', 'user3');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003);

INSERT INTO restaurants (name, description) VALUES
  ('Restaurant 1', 'Description of restaurant 1'),
  ('Restaurant 2', 'Description of restaurant 2'),
  ('Restaurant 3', 'Description of restaurant 3'),
  ('Restaurant 4', 'Description of restaurant 4'),
  ('Restaurant 5', 'Description of restaurant 5'),
  ('Restaurant 6', 'Description of restaurant 6'),
  ('Restaurant 7', 'Description of restaurant 7'),
  ('Restaurant 8', 'Description of restaurant 8'),
  ('Restaurant 9', 'Description of restaurant 9'),
  ('Restaurant 10', 'Description of restaurant 10');

INSERT INTO menu (rest_id, date_time, dish, price) VALUES
  (100004, '2017-01-30 10:00:00', 'Dish 1', 12.5),
  (100005, '2017-01-30 10:10:00', 'Dish 2', 13.5),
  (100006, '2017-01-30 10:20:00', 'Dish 3', 14.5),
  (100007, '2017-01-30 10:30:00', 'Dish 4', 15.5),
  (100008, '2017-01-30 10:40:00', 'Dish 5', 16.5),
  (100009, '2017-01-30 10:50:00', 'Dish 6', 17.5),
  (100010, '2017-01-30 09:00:00', 'Dish 7', 18.5),
  (100011, '2017-01-30 08:00:00', 'Dish 8', 19.5),
  (100012, '2017-01-30 07:00:00', 'Dish 9', 10.5),
  (100013, '2017-01-30 09:59:00', 'Dish 10', 9.5);

INSERT INTO votes (user_id, rest_id, date_time) VALUES
  (100000, 100004, '2017-01-30 10:00:00'),
  (100001, 100005, '2017-01-30 10:00:00'),
  (100002, 100006, '2017-01-30 10:00:00'),
  (100003, 100006, '2017-01-30 10:00:00'),
  (100000, 100008, '2017-02-20 10:00:00'),
  (100001, 100009, '2017-02-20 10:00:00'),
  (100002, 100010, '2017-02-20 10:00:00'),
  (100003, 100009, '2017-02-20 10:00:00');