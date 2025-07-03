USE book_manager;

INSERT INTO book
VALUES (100, 'Kotlin入門', 'コトリン太郎', '1950-10-01'),
       (200, 'Java入門', 'ジャヴァ太郎', '2005-08-29');

INSERT INTO user
VALUES (1, 'admin@test.com', '', '管理者', 'ADMIN'),
       (2, 'user@test.com', '', 'ユーザー', 'USER');
#パスワードは基本直接DBに書かず、ハッシュ値を生成する