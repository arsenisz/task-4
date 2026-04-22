-- База даних для проекту "Визначні місця України"
-- Створення бази даних та таблиці

-- Створити базу даних
CREATE DATABASE IF NOT EXISTS ukraine_sites;
USE ukraine_sites;

-- Створити таблицю визначних місць
CREATE TABLE IF NOT EXISTS sites (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NULL,
    latitude DECIMAL(10, 6) NULL,
    longitude DECIMAL(10, 6) NULL,
    region VARCHAR(255) NULL,
    image VARCHAR(255) NULL,
    PRIMARY KEY (id)
);

-- Додати тестові дані про визначні місця України
INSERT INTO sites (name, latitude, longitude, region, image) VALUES
('Софійський собор', 50.452693, 30.514477, 'Київ', 'sophia.jpg'),
('Києво-Печерська лавра', 50.434472, 30.558077, 'Київ', 'lavra.jpg'),
('Хотинська фортеця', 48.515278, 26.493611, 'Чернівецька область', 'khotyn.jpg'),
('Кам\'янець-Подільська фортеця', 48.679167, 26.583056, 'Хмельницька область', 'kamianets.jpg'),
('Замок Паланок', 48.437222, 22.713889, 'Закарпатська область', 'palanok.jpg'),
('Олеський замок', 49.971389, 24.894722, 'Львівська область', 'olesko.jpg'),
('Хортиця', 47.831111, 35.078611, 'Запорізька область', 'khortytsia.jpg'),
('Бахчисарайський палац', 44.748889, 33.881111, 'Крим', 'bakhchysarai.jpg');

-- Перевірити дані
SELECT * FROM sites;
