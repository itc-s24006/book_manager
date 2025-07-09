USE book_manager;

INSERT INTO book
VALUES (100, 'Kotlin入門', 'コトリン太郎', '1950-10-01'),
       (200, 'Java入門', 'ジャヴァ太郎', '2005-08-29');

INSERT INTO user
VALUES (1, 'admin@test.com', '$argon2id$v=19$m=19456,t=2,p=1$dEE5M0x5amtoMzhFZXlndw$Zje69hYj15V7XysQeNnvCNDo49Crz4eRbSNHNcuMqDM
        ', '管理者', 'ADMIN'),
       (2, 'user@test.com',
        '$argon2id$v=19$m=19456,t=2,p=1$U2toOXQxd2NDeXlGU0NrYg$c0Sd/jqfuFtyw/Z3RgYAJpyeenhdlCrmsqKwH7IPqCk', 'ユーザー',
        'USER');
#パスワードは基本直接DBに書かず、ハッシュ値を生成する