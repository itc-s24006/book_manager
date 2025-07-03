CREATE DATABASE book_manager;
CREATE USER spring IDENTIFIED BY 'boot'; #spring→ユーザ名　boot→パスワード
GRANT ALL PRIVILEGES ON book_manager.* TO spring; #GRANT    権限を与える
