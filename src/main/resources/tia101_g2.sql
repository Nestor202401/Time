DROP DATABASE IF EXISTS tia101_g2;
CREATE DATABASE  tia101_g2;
USE tia101_g2;

-- 會員區塊.............................................................
CREATE TABLE member (
    member_id int AUTO_INCREMENT PRIMARY KEY,  -- 會員編號
    member_account varchar(20), -- 會員帳號
    member_password varchar(20), -- 會員密碼
    member_name varchar(20), -- 會員姓名
    member_phone varchar(20), -- 會員電話
    member_email varchar(100) UNIQUE, -- 會員信箱
    member_register_datetime Datetime DEFAULT CURRENT_TIMESTAMP, -- 註冊時間
    member_img LONGBLOB, -- 大頭照id
    is_admin BOOLEAN DEFAULT false, -- 是否是管理員
    is_verified  BOOLEAN DEFAULT false, -- 是否驗證過
    verification_token VARCHAR(45) -- 驗證亂碼
);

INSERT INTO member(member_account, member_password, member_name, member_phone, member_email,member_img,is_admin,is_verified)
VALUES 
('tony2892', 'tia222334',  '員工1', '0926523123', 'bbac@yahoo.com.tw',null,1,1),
('david1233', 'gag770894', '員工2', '0928194854', 'def@yahoo.com.tw', null,1,1),
('bolo5454', 'na51492932', '員工3', '0992740184', 'ghi@gmail.com.tw', null,1,1),
('antya123', 'gua2718', '員工4', '0918572095', 'gary@yahoo.com.tw',  null,1,1),
('wil9356', 'ac5059092',  '陳建隆', '0938291822', 'vvic@yahoo.com.tw', null,0,1),
('alex11892', 'bac250302', '陳以哲', '0909287430', 'logo@yahoo.com.tw',null,0,1);

-- SELECT * FROM member;

-- 電影區塊.............................................................


-- 電影類型
-- CREATE TABLE movie_types (
--     type_id INT AUTO_INCREMENT PRIMARY KEY, -- 電影類別ID
--     type_name VARCHAR(50) -- 電影類別名稱
-- );

-- INSERT INTO movie_types ( type_name) VALUES
-- ( '不分類'),
-- ( '劇情'),
-- ( '動作'),
-- ( '科幻'),
-- ( '喜劇'),
-- ( '愛情'),
-- ( '戰爭'),
-- ( '恐怖'),
-- ( '動畫'),
-- ( '紀錄'),
-- ( '其他');

-- 影廳表
CREATE TABLE cinema (
    cinema_id INT AUTO_INCREMENT PRIMARY KEY, -- 影廳ID，自動增量
    cinema_name VARCHAR(50), -- 影廳名稱
    seat_row INT, -- 座位行數
    seat_column VARCHAR(5), -- 座位列編號
    seat_status INT, -- 座位狀態碼（例如：0表示空閒，1表示已預訂）
    seat_number VARCHAR(5) -- 座位編號或標識符
);

INSERT INTO cinema (cinema_name, seat_row, seat_column, seat_status, seat_number) VALUES
('一般廳', 1, 'A', 0, 'A1'),
('一般廳', 2, 'A', 0, 'A2'),
('一般廳', 3, 'A', 0, 'A3'),
('一般廳', 4, 'A', 1, 'A4'),
('一般廳', 5, 'A', 2, 'A5'),
('一般廳', 1, 'B', 0, 'B1'),
('一般廳', 2, 'B', 0, 'B2'),
('一般廳', 3, 'B', 0, 'B3'),
('一般廳', 4, 'B', 1, 'B4'),
('一般廳', 5, 'B', 0, 'B5'),
('3D廳', 1, 'A', 0, 'A1'),
('3D廳', 2, 'A', 0, 'A2'),
('3D廳', 3, 'A', 0, 'A3'),
('3D廳', 4, 'A', 1, 'A4'),
('3D廳', 5, 'A', 2, 'A5'),
('3D廳', 1, 'B', 0, 'B1'),
('3D廳', 2, 'B', 0, 'B2'),
('3D廳', 3, 'B', 0, 'B3'),
('3D廳', 4, 'B', 1, 'B4'),
('3D廳', 5, 'B', 0, 'B5');


-- 電影表
CREATE TABLE movie (
    movie_id INT AUTO_INCREMENT PRIMARY KEY, -- 電影ID，自動增量
    movie_name VARCHAR(255), -- 電影名稱
    movie_rating INT, -- 電影類別ID，參考 movie_types 表
    director VARCHAR(100), -- 導演
    actor VARCHAR(100), -- 演員
    release_date DATE, -- 上映日期
    end_date DATE, -- 下檔日期
    runtime INT, -- 電影時長（分鐘）
    Introduction TEXT -- 電影介紹
    -- CONSTRAINT fk_movie_type FOREIGN KEY (movie_rating) REFERENCES movie_types(type_id) 
    -- 外鍵約束，關聯電影類別
);


INSERT INTO movie (movie_name, movie_rating, director, actor, release_date, end_date, runtime, Introduction)
VALUES 
('水行俠', 3, '溫子仁', '傑森摩莫亞', '2023-04-20', '2023-08-01', 114, '由溫子仁執導、傑森摩莫亞主演，敘述水行俠必須建立盟友，以保護亞特蘭提斯及世界受到不可逆的破壞。'),
('大話情聖', 4, '瑞卡多·米拉尼', '皮耶爾弗蘭切斯科·法維諾', '2023-05-11', '2023-09-23', 110, '花花公子詹尼接受朋友們的挑戰，假裝殘疾與因為車禍而癱瘓的小提琴家琪亞拉約會。 在相處過程中，他發現自己漸漸愛上了她，為了和她繼續相處，只能一直假裝殘疾…'),
('穿薛子的貓2', 4, '喬爾·克勞福德', '安東尼奧·班德拉斯', '2023-06-05', '2023-10-27', 120, '時隔11年，臭屁自大又愛賣萌的貓大俠回來了！如今的貓大俠（安東尼奧·班德拉斯 配音），依舊幽默瀟灑又不拘小節、數次「花式送命」後，九條命如今只剩一條，於是不得不請求自己的老搭檔兼「宿敵」——迷人的軟爪妞（薩爾瑪·海耶克 配音）來施以援手來恢復自己的九條生命。'),
('靈異神探', 2, '卡洛斯・賽隆', '冬妮·阿科斯塔', '2023-07-05', '2023-11-11', 105, '在領頭的皮爾恩神父失蹤後，三名調查超自然事件的中年婦女迎難而上。改編自真實存在的赫普特小組'),
('功夫熊貓4', 1, '麥克米契', '傑克布萊克', '2024-02-13', '2024-06-03', 94, '今年春天，喜劇天王傑克布萊克睽違近十年再度為【功夫熊貓4】獻聲，飾演世界上最出人意料的功夫大師阿波，為這部深受觀眾喜愛的夢工廠動畫系列開啟精彩刺激、幽默逗趣的全新篇章。神龍大俠阿波（金球獎入圍者傑克布萊克 飾）憑著無人能及的勇氣和武功，在三次驚險萬分的冒險中打敗了世界級的大壞蛋，現在命運安排他要……休息一下。更明確的說，他被選為和平谷的精神領袖。'),
('哥吉拉與金剛：新帝國', 3, '亞當溫高德', '蕾貝卡霍爾', '2024-03-17', '2024-07-26', 115, '傳奇影業的怪獸宇宙系列繼《哥吉拉大戰金剛》的爆炸性對決後，再度推出全新冒險故事！全能的金剛和駭人的哥吉拉即將聯手對抗隱藏在我們世界中未被發現的巨大威脅，那不僅挑戰了牠們的生存空間，更威脅了人類的存亡。《哥吉拉與金剛：新帝國》將深入探討這些泰坦巨獸的歷史、起源，以及骷髏島及其他秘境的奧秘，同時揭開塑造這些非凡生物，並將牠們與人類永遠連結在一起的神話戰鬥。'),
('魔鬼剋星：冰天凍地', 1, '吉爾基能', '保羅路德', '2024-04-01', '2024-08-26', 115, '史賓格勒一家在這集中，回到魔鬼剋星的發源地紐約消防局，與第一代的隊員重逢，並期望最高機密的研究室能夠研發出更先進的抓鬼技術。不過此時一件神秘的古老文物，釋放出一隻邪惡的惡靈，造成地球即將再次進入冰河時期，他們現在必須聯手保護家園並且拯救世界。'),
('沙丘：第二部', 1, '丹尼維勒納夫', '提摩西夏勒梅', '2024-05-21', '2024-09-02', 120, '在【沙丘】中，亞崔迪家族被哈肯能家族殘忍殺害，保羅亞崔迪與母親潔西嘉女士在險惡的厄拉科斯星球，遇見了先前在夢中出現的荃妮，加入弗瑞曼人的生活。'),
('加菲貓', 2, '馬克·汀戴爾', '克里斯·帕拉特', '2024-08-21', '2025-01-02', 140, '加菲貓的主人老薑（布萊克金·邁耶飾）接受了暗戀對象—獸醫甜心麗絲（珍妮佛·樂芙·休伊飾）送給他的一隻小狗歐弟，熱情憨厚的歐弟出現讓加菲貓的世界徹底顛覆，慵懶愛吃的加菲貓巴不得趕牠出去，只是歐弟卻真的不幸遭到壞人偷走，於是加菲貓鼓足勇氣決定要幫助主人和麗絲找回牠最討厭的歐弟...'),
('飢餓遊戲：鳴鳥與蛇之歌', 2, '弗朗西斯·勞倫斯', '湯姆·布萊斯', '2024-09-11', '2025-02-12', 150, '18歲的科里奧蘭納斯·斯諾（湯姆·布萊斯 飾）被選爲第十屆飢餓遊戲的導師，與來自第十二區的貧困女孩露西·格蕾·貝爾德（瑞秋·齊格勒 飾）成爲了「生死相依」的命運共同體。競技場上，這是一場關乎自由、食物，甚至是尊重與權利的生死角逐；競技場外，不忍與善意使斯諾決定不顧一切幫助露西在這場遊戲中贏得勝利......當活下去的欲望與所遵守的規則背道而馳，命運的齒輪緊抓入自己手中時，他又該如何抉擇？ 影片改編自蘇珊·柯林斯的同名小說。'),
('驚奇隊長2', 4, '尼婭·達科斯塔', '布麗·拉爾森', '2024-10-16', '2025-03-24', 130, '「驚奇隊長」卡羅爾·丹弗斯（布麗·拉爾森 Brie Larson 飾）從殘暴的克里人手中奪回了屬於自己的身份，也對至高智慧完成了復仇。然而，意想不到的後果出現了。面對動盪脆弱的宇宙，她毅然決然地挺身而出。執行任務時，她來到一個神祕的特殊蟲洞。在這裡，她與另外兩位女英雄產生了能力糾纏——一位是來自澤西市、驚奇隊長的超級粉絲「驚奇少女」卡瑪拉·克汗（伊曼·韋拉尼 Iman Vellani 飾），另一位是曾與卡羅爾親密無間的小侄女、如今長大在天劍局（S.A.B.E.R.）擔任太空人的莫妮卡·蘭博隊長（泰柔娜·派麗絲 Teyonah Parris 飾）。看似不搭界的三人必須齊心協力，以「驚奇聯盟」的身份拯救宇宙。'),
('失蹤者之夜', 3, '格拉漢姆·菲利普斯', '梅雷迪思·托馬斯', '2024-11-16', '2025-04-04', 120, '一個暴風雨的夜晚，小鎮警署里只有一個清潔工在打掃，一個神祕的女人突然進來聲稱要找女警長哈德森。');



-- 電影圖片
CREATE TABLE movie_img (
    movie_img_id INT AUTO_INCREMENT PRIMARY KEY,  -- 圖片ID，自增
    movie_id INT,                                 -- 電影ID，關聯到電影表的ID
    movie_img_name VARCHAR(255),                  -- 圖片名稱
    movie_img_file VARCHAR(255),                  -- 圖片文件路徑
    CONSTRAINT fk_movie_id FOREIGN KEY (movie_id) REFERENCES movie(movie_id) 
    -- 外鍵約束，關聯到電影表的ID
);


INSERT INTO movie_img (movie_id, movie_img_name, movie_img_file)
VALUES 
(1, '圖片01',  '/path/to/image01.jpg'),
(1, '圖片02',  '/path/to/image02.jpg'),
(1, '圖片03',  '/path/to/image03.jpg'),
(2, '圖片04',  '/path/to/image04.jpg'),
(2, '圖片05',  '/path/to/image05.jpg'),
(3, '圖片06',  '/path/to/image06.jpg'),
(3, '圖片07',  '/path/to/image07.jpg'),
(4, '圖片08',  '/path/to/image08.jpg'),
(4, '圖片09',  '/path/to/image09.jpg'),
(5, '圖片10',  '/path/to/image10.jpg'),
(5, '圖片11',  '/path/to/image11.jpg'),
(6, '圖片12',  '/path/to/image12.jpg'),
(6, '圖片13',  '/path/to/image13.jpg'),
(7, '圖片14',  '/path/to/image14.jpg'),
(7, '圖片15',  '/path/to/image15.jpg'),
(8, '圖片16',  '/path/to/image16.jpg'),
(8, '圖片17',  '/path/to/image17.jpg'),
(9, '圖片18',  '/path/to/image18.jpg'),
(9, '圖片19',  '/path/to/image19.jpg'),
(10, '圖片20',  '/path/to/image20.jpg'),
(10, '圖片21',  '/path/to/image21.jpg'),
(11, '圖片22',  '/path/to/image22.jpg'),
(11, '圖片23',  '/path/to/image23.jpg');


-- 電影類型表
-- CREATE TABLE moves_type_table (
--     movie_id INT,
--     moves_type_id INT,
--     CONSTRAINT fk_movie_id_type FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
--     CONSTRAINT fk_type_id FOREIGN KEY (moves_type_id) REFERENCES movie_types(type_id)
-- );


-- INSERT INTO moves_type_table (movie_id, moves_type_id) VALUES 
-- (1, 2), 
-- (1, 3), 
-- (1, 8), 
-- (2, 4), 
-- (2, 7), 
-- (3, 2), 
-- (3, 5), 
-- (3, 7), 
-- (4, 3), 
-- (4, 6), 
-- (5, 4), 
-- (6, 2), 
-- (6, 6), 
-- (6, 9), 
-- (7, 8), 
-- (8, 2), 
-- (8, 3), 
-- (9, 4), 
-- (9, 7), 
-- (10, 5), 
-- (11, 8), 
-- (12, 11);


-- 電影場次
CREATE TABLE movie_time (
    show_times_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT,
    cinema_id INT,
    movie_playback_type INT,-- 0 數位  1 3D
    price INT,
    show_time INT,
    show_time_date DATE,
    CONSTRAINT fk_movie_time_movie_id FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    CONSTRAINT fk_movie_time_cinema_id FOREIGN KEY (cinema_id) REFERENCES cinema(cinema_id)
);

INSERT INTO movie_time (movie_id, cinema_id, movie_playback_type, price, show_time, show_time_date) VALUES
(5, 1, 0, 0, 0, '2024-02-13'),
(5, 2, 1, 20, 1, '2024-02-13'),
(5, 1, 0, 0, 2, '2024-02-13'),
(6, 1, 0, 0, 0, '2024-03-17'),
(6, 2, 1, 20, 1, '2024-03-17'),
(6, 1, 0, 0, 2, '2024-03-17'),
(7, 1, 0, 0, 0, '2024-04-01'),
(7, 2, 1, 20, 1, '2024-04-01'),
(7, 1, 0, 0, 2, '2024-04-01'),
(8, 1, 0, 0, 0, '2024-05-21'),
(8, 1, 0, 0, 1, '2024-05-21'),
(8, 2, 1, 20, 2, '2024-05-21');

-- 建立票種表格
CREATE TABLE ticket_types (
    ticket_types_id INT AUTO_INCREMENT PRIMARY KEY comment '票種ID', -- PK
    ticket_type_name VARCHAR(50) comment '票種名稱', 
    ticket_price INT comment '價格'
) AUTO_INCREMENT = 1520001;

-- 建立餐飲種類表格
-- CREATE TABLE food_types (
--     food_types_id INT AUTO_INCREMENT PRIMARY KEY comment '餐飲種類ID', -- PK
--     food_name VARCHAR(50) comment '餐飲名稱',
--     food_price INT comment '餐飲價格',
-- 	   food_img_file VARCHAR(255)                 -- 圖片文件路徑
-- ) AUTO_INCREMENT = 1510001;

-- 建立優惠表格
-- CREATE TABLE discounts (
--     discount_id INT AUTO_INCREMENT PRIMARY KEY comment '優惠ID', -- PK
--     food_types_id INT comment '餐飲種類ID', -- FK
--     discount_price INT comment '優惠金額',
--     FOREIGN KEY (food_types_id) REFERENCES food_types(food_types_id)
-- ) AUTO_INCREMENT = 1500001;

-- 建立電影訂單表格
CREATE TABLE movie_ticket_order (
    movie_order_id INT AUTO_INCREMENT PRIMARY KEY comment '電影訂單ID', -- PK
    member_id INT comment '會員ID', -- FK
    movie_order_status BOOLEAN comment '電影訂單狀態', -- FALSE.未完成  TRUE.已完成
    buy_tickets_date DATETIME comment '購票時間',
    movie_order_total INT comment '總價',
    FOREIGN KEY (member_id) REFERENCES member(member_id)
) AUTO_INCREMENT = 1530001;

-- 建立餐飲明細表格
-- CREATE TABLE food_details (
--     food_details_id INT AUTO_INCREMENT PRIMARY KEY comment '餐飲明細ID', -- PK
--     movie_order_id INT comment '電影訂單ID', -- FK
--     food_types_id INT comment '餐飲種類ID', -- FK
--     food_unit_price INT comment '餐飲單價',
--     food_count INT comment '餐飲數量',
--     food_subtotal INT comment '餐飲小計',
--     FOREIGN KEY (movie_order_id) REFERENCES movie_ticket_order(movie_order_id),
--     FOREIGN KEY (food_types_id) REFERENCES food_types(food_types_id)
-- ) AUTO_INCREMENT = 1540001;

-- 建立電影票表格
CREATE TABLE ticket_list (
    movie_ticket_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '電影票ID', -- PK
    movie_order_id INT COMMENT '電影訂單ID', -- FK
    movie_id INT COMMENT '電影ID', -- FK
    ticket_types_id INT COMMENT '票種ID', -- FK
    cinema_id INT COMMENT '影廳ID', -- FK
    show_times_id INT COMMENT '場次ID', -- FK
    seat_number VARCHAR(10) COMMENT '座位編號',
    qrcode VARCHAR(255) COMMENT 'QRcode',
    ticket_status BOOLEAN COMMENT '電影票使用狀態',
    FOREIGN KEY (movie_order_id) REFERENCES movie_ticket_order(movie_order_id),
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY (ticket_types_id) REFERENCES ticket_types(ticket_types_id),
    FOREIGN KEY (cinema_id) REFERENCES cinema(cinema_id),
    FOREIGN KEY (show_times_id) REFERENCES movie_time(show_times_id)
) AUTO_INCREMENT = 1550001;


-- 插入資料到票種表格
INSERT INTO ticket_types (ticket_type_name, ticket_price) 
VALUES
('一般票', 320),
('優待票', 280),
('敬老票', 180);

-- 插入資料到餐飲種類表格
-- INSERT INTO food_types (food_name, food_price) 
-- VALUES
-- ('爆米花', 120),
-- ('熱狗堡', 70),
-- ('熱狗', 55),
-- ('吉拿棒', 55),
-- ('雪碧', 45),
-- ('可樂', 45);

-- 插入資料到優惠表格
-- INSERT INTO discounts (food_types_id, discount_price) 
-- VALUES
-- (1510001, 30),
-- (1510002, 15),
-- (1510005, 5),
-- (1510006, 5);

-- 插入資料到電影訂單表格
INSERT INTO movie_ticket_order (member_id, movie_order_status, buy_tickets_date, movie_order_total) 
VALUES
(1, TRUE, '2020-12-22 21:30:26', 1010),
(1, FALSE, '2021-01-02 14:10:04', 230),
(2, TRUE, '2021-02-05 11:25:12', 655);

-- 插入資料到餐飲明細表格
-- INSERT INTO food_details (movie_order_id, food_types_id, food_unit_price, food_count, food_subtotal) 
-- VALUES
-- (1530001, 1510001, 120, 2, 240),
-- (1530001, 1510002, 70, 2, 140),
-- (1530001, 1510005, 45, 1, 45),
-- (1530001, 1510006, 45, 1, 45),
-- (1530002, 1510004, 55, 1, 55),
-- (1530003, 1510003, 55, 1, 55);

-- 插入資料到電影票表格
INSERT INTO ticket_list (movie_order_id, movie_id, ticket_types_id, cinema_id, show_times_id, seat_number, qrcode, ticket_status) 
VALUES
(1530001, 1, 1520001, 1, 3, 'A5', 'https://reurl.cc/NQ1vk9', TRUE),
(1530001, 1, 1520003, 1, 3, 'B2', 'https://reurl.cc/NQ1vk9', TRUE),
(1530002, 4, 1520002, 2, 7, 'B5', 'https://reurl.cc/NQ1vk9', FALSE),
(1530003, 2, 1520002, 1, 2, 'A3', 'https://reurl.cc/NQ1vk9', TRUE),
(1530003, 2, 1520001, 1, 2, 'A4', 'https://reurl.cc/NQ1vk9', TRUE);



-- 周邊商品區塊.............................................................

CREATE TABLE product_order (
    prod_ord_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '商品訂單ID',
    member_id INT COMMENT '會員的ID', # NOT NULL 
    est_time DATETIME COMMENT '訂單成立時間',
    ord_status INT COMMENT '訂單狀態 - 0:未完成, 1:已完成',
    total INT COMMENT '總額',
    recipient VARCHAR(255) COMMENT '收件人姓名',
    rec_addr VARCHAR(255) COMMENT '收件人地址',
    CONSTRAINT fk_product_order_member_id FOREIGN KEY (member_id) REFERENCES member(member_id)
);
ALTER TABLE product_order AUTO_INCREMENT=11000001;

# product
CREATE TABLE product ( # CREATE my_detail TABLE
	prod_id int AUTO_INCREMENT PRIMARY KEY comment '商品ID',
	prod_name varchar(255) comment '商品名稱',
	prod_intro varchar(255) comment '商品介紹', # text 不能用 String 接，改用 varchar
	prod_price int comment '商品價格', 
	release_date date comment '上架日期',
	remove_date date comment '下架日期',
	sales_status int comment '銷售狀態, 0:銷售中, 1:停售',
	time_limit_prod bool comment '限時商品'
);
# Increment with default value XXX
ALTER TABLE product AUTO_INCREMENT=13000001;

# product_detail
CREATE TABLE product_detail (
	prod_detail_id int AUTO_INCREMENT PRIMARY KEY comment '明細ID',
	prod_ord_id int comment '商品訂單ID', # NOT NULL 
	prod_id int comment '商品ID', # NOT NULL 
	unit_price int comment '單價',
	prod_count int comment '數量', 
	prod_sum int comment '小計',
	FOREIGN KEY (prod_ord_id) REFERENCES product_order(prod_ord_id),
	FOREIGN KEY (prod_id) REFERENCES product(prod_id)
);
# Increment with default value XXX
ALTER TABLE product_detail AUTO_INCREMENT=12000001;

# product_img
CREATE TABLE product_img ( # CREATE my_detail TABLE
	prod_img_id int AUTO_INCREMENT PRIMARY KEY comment '圖片ID',
	prod_id int comment '商品ID', #  NOT NULL
	img_src varchar(255) comment '圖片路徑', # 如何解決檔名重複?
	img_name varchar(255) comment '圖片名稱', 
	FOREIGN KEY (prod_id) REFERENCES product(prod_id) 
);
# Increment with default value XXX
ALTER TABLE product_img AUTO_INCREMENT=14000001;

# --------- 加入資料: product > product_img > product_order > product_detail ---------
# product
INSERT INTO product (prod_name, prod_intro, prod_price, release_date, remove_date, sales_status, time_limit_prod)
VALUES 
	('功夫熊猫資料夾', '功夫熊猫的資料夾', 100, '2024-03-27', null, 0, false),
	('沙丘保溫瓶', '沙丘保的溫瓶', 1000, '2024-03-27', null, 0, false),
	('千尋卡片', '千尋的卡片', 250, '2002-01-01', null, 1, false),
	('金鋼玩偶', '金鋼的玩偶', 1000, '2024-03-27', '2024-06-27', 0, true),
	('怪盜基德明信片', '怪盜基德的明信片', 50, '2024-03-29', null, 0, false),
	-- 5
	('科南T-shirt', '科南的T-shirt', 500, '2024-03-29', null, 0, false),
	('《NEXT LEVEL》加油棒', '《NEXT LEVEL》的加油棒', 1000, '2024-04-09', '2024-04-30', 1, true),
	('蜘蛛人背包', '蜘蛛的人背包', 1500, '2023-12-25', null, 0, false),
	('鏡之孤城BD', '鏡之孤城的BD', 1800, '2023-12-20', null, 0, false),
	('哥吉拉玩偶', '哥吉拉的玩偶', 1000, '2024-03-27', '2024-06-27', 0, true)
	-- 10
	;

# product_img
INSERT INTO product_img (prod_id, img_src, img_name)
VALUES 
	(13000001, './prod_img/功夫熊猫資料夾_1.jpg', '功夫熊猫資料夾_1.jpg'),
	(13000001, './prod_img/功夫熊猫資料夾_2.jpg', '功夫熊猫資料夾_2.jpg'),
	(13000001, './prod_img/功夫熊猫資料夾_3.jpg', '功夫熊猫資料夾_3.jpg'),
	(13000002, './prod_img/沙丘保溫瓶_1.jpg', '沙丘保溫瓶_1.jpg'),
	(13000002, './prod_img/沙丘保溫瓶_2.jpg', '沙丘保溫瓶_2.jpg'),
	-- 5
	(13000003, './prod_img/千尋卡片_1.jpg', '千尋卡片_1.jpg'),
	(13000004, './prod_img/金鋼玩偶_1.jpg', '金鋼玩偶_1.jpg'),
	(13000004, './prod_img/金鋼玩偶_2.jpg', '金鋼玩偶_2.jpg'),
	(13000004, './prod_img/金鋼玩偶_3.jpg', '金鋼玩偶_3.jpg'),
	(13000005, './prod_img/怪盜基德明信片_1.jpg', '怪盜基德明信片_1.jpg'),
	-- 10
	(13000006, './prod_img/科南T-shirt_1.jpg', '科南T-shirt_1.jpg'),
	(13000007, './prod_img/《NEXT LEVEL》加油棒_1.jpg', '《NEXT LEVEL》加油棒_1.jpg'),
	(13000007, './prod_img/《NEXT LEVEL》加油棒_2.jpg', '《NEXT LEVEL》加油棒_2.jpg'),
	(13000008, './prod_img/蜘蛛人背包_1.jpg', '蜘蛛人背包_1.jpg'),
	(13000008, './prod_img/蜘蛛人背包_2.jpg', '蜘蛛人背包_2.jpg'),
	-- 15
	(13000009, './prod_img/鏡之孤城BD_1.jpg', '鏡之孤城BD_1.jpg'),
	(13000010, './prod_img/哥吉拉玩偶_1.jpg', '哥吉拉玩偶_1.jpg'),
	(13000010, './prod_img/哥吉拉玩偶_2.jpg', '哥吉拉玩偶_2.jpg'),
	(13000010, './prod_img/哥吉拉玩偶_3.jpg', '哥吉拉玩偶_3.jpg')
	;

# product_order
INSERT INTO product_order (member_id, est_time, ord_status, total, recipient, rec_addr)
VALUES 
	(1, '2024-03-27 10:59:15', 1, 1300, '王小明', '104台北市中山區南京東路三段219號5樓'),
	(1, '2024-03-27 12:33:00', 0,  200, '王小明', '104台北市中山區南京東路三段219號5樓'),
	(2, '2024-03-27 15:07:00', 0, 1100, '李四端', '104台北市中山區南京東路三段9號'),
	(2, '2024-03-31 12:00:21', 1, 3500, '蔡元熙', '高雄市中央路12號'),
	(3, '2024-04-05 15:26:59', 1,  650, '陳琳瑤', '臺中市三榮五路5號'),
	-- 5
	(3, '2024-04-11 12:00:01', 0, 1800, '郭駱騏', '雲林縣南安街96號')
	;

# product_detail
INSERT INTO product_detail (prod_ord_id, prod_id, unit_price, prod_count, prod_sum)
VALUES 
	(11000001, 13000001,  100, 3,  300),
	(11000001, 13000004, 1000, 1, 1000),
	(11000003, 13000001,  100, 1,  100),
	(11000003, 13000002, 1000, 1, 1000),
	(11000002, 13000001,  100, 2,  200),
	-- 5
	(11000004, 13000004, 1000, 1, 1000),
	(11000004, 13000010, 1000, 1, 1000),
	(11000004, 13000008, 1500, 1, 1500),
	(11000005, 13000005,   50, 3,  150),
	(11000005, 13000006,  500, 1,  500),
	-- 10
	(11000006, 13000009, 1800, 1, 1800)
	;

-- 討論區區塊.............................................................

-- 文章表格
CREATE TABLE article (
    article_id INT PRIMARY KEY AUTO_INCREMENT,
    type_id INT,
    member_id INT,
    theme VARCHAR(255),
    article_content TEXT,
    browse_peoples INT,
    article_status INT,
    release_time DATETIME,
    pin_to_top BOOLEAN,
    -- FOREIGN KEY (type_id) REFERENCES movie_types(type_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);


INSERT INTO article ( type_id, member_id,theme, article_content, browse_peoples, article_status, release_time)
VALUES (2,1,'哥吉拉與金剛：新帝國', '怪獸宇宙」的第五部作品，同時也是《哥吉拉與金剛》系列電影第二部的《哥吉拉與金剛：新帝國》（Godzilla x Kong: The New Empire）整體來說評價算是可圈可點，上映第一天就衝上全台票房冠軍寶座，IMDb開出 6.8 分，雖然怪獸之間的戰鬥爽度不及系列上一部《哥吉拉大戰金剛》（Godzilla vs. Kong），劇情中也有一些雷點，但是「怪獸女王」摩斯拉（又稱莫斯拉，Mothra）、「迷你金剛」的出現，以及出現了許多金剛很萌、很人性化的巧思，的確為電影加分不少。', 6, 0, '2024-01-30');
-- 插入「功夫熊貓4」的資訊
INSERT INTO article (type_id, member_id,theme, article_content, browse_peoples, article_status, release_time)
VALUES (3,1,'功夫熊貓4', '今個春季「重出江湖」的功夫熊貓阿波，8年後再與觀眾見面。夢工廠出品《功夫熊貓4》（Kung Fu Panda 4）的武打場面更加盛大，更具幽默感，阿波集結動物界武林高手的力量，攜手並肩打敗黑暗勢力變色龍。影片中的大反派變色龍，以及變色龍統治的沿海大都市Juniper City，究竟有怎樣的隱喻？', 8, 0, '2024-02-02');

-- 插入「青春18×2 通往有你的旅程」的資訊
INSERT INTO article (type_id, member_id,theme, article_content, browse_peoples, article_status, release_time)
VALUES (5,3,'青春18×2 通往有你的旅程', 'XXXXXXX', 9, 1, '2024-03-03');

-- 插入「沙丘：第二部」的資訊
INSERT INTO article (type_id, member_id,theme, article_content, browse_peoples, article_status, release_time)
VALUES (7,4,'沙丘：第二部', '對於《沙丘：第二部》有興趣的新觀眾來說，我的立場也和丹尼一樣，有興趣直接去看就對了，強烈建議你不要先找第一部來看，因為如果你能接受甚至喜歡第二部，那再回頭看第一部也不遲，反倒是如果你現在先看第一部（而且還是在家看），那麼你不喜歡的機率會非常高，自然也就不會想看第二部了。', 12, 0, '2024-03-04');

-- 插入「天魔：惡之初」的資訊
INSERT INTO article (type_id, member_id,theme, article_content, browse_peoples, article_status, release_time)
VALUES (8,5,'天魔：惡之初', 'XXXXXXX', 7, 1, '2024-05-08');



-- 留言表格
CREATE TABLE comments (
    comment_id INT PRIMARY KEY AUTO_INCREMENT,
    article_id INT NOT NULL,
    member_id INT NOT NULL,
    comment_content TEXT,
    comment_status INT,
    release_time DATETIME,
    FOREIGN KEY (article_id) REFERENCES article(article_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

INSERT INTO comments (article_id, comment_content, comment_status, release_time, member_id) VALUES
(1, '不只有可愛的金剛寶寶，還有紅毛金剛也現身', 0, '2024-02-01', 1),
(1, '哥吉拉控制著地表世界，而金剛則在地心世界,但新的威脅出現時，牠們仍不得不聯手制霸。', 0, '2024-02-03', 2),
(3, 'XXXXXXX', 1, '2024-03-04', 3),
(4, 'IMAX有史以來在台開片日票房最高的電影', 0, '2024-03-05', 4),
(5, 'XXXXXXX', 1, '2024-05-09', 5);


-- 按讚表格
CREATE TABLE likes (
    likes_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    article_id INT,
    comment_id INT,
    FOREIGN KEY (member_id) REFERENCES member(member_id),
    FOREIGN KEY (article_id) REFERENCES article(article_id),
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id)
);

INSERT INTO likes (article_id, comment_id, member_id) VALUES
(1, NULL, 1),
(1, NULL, 2),
(NULL, 1, 4),
(NULL, 2, 5);



-- 檢舉表格
CREATE TABLE reports (
    report_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    article_id INT,
    comment_id INT,
    report_type INT,
    report_reason TEXT,
    report_status INT,
    report_datetime DATETIME,
    FOREIGN KEY (member_id) REFERENCES member(member_id),
    FOREIGN KEY (article_id) REFERENCES article(article_id),
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id)
);

INSERT INTO reports (article_id, comment_id, report_type, report_reason, report_status, report_datetime, member_id)
VALUES
(5, NULL, '0', '仇恨言論', '0', '2024-05-09 00:00:00', 1),
(NULL, 5, '1', '仇恨言論', '1.', '2024-05-09 00:00:00', 1),
(5, NULL, '1', '仇恨言論', '1.', '2024-05-09 00:00:00', 2),
(3, NULL, '0', '仇恨言論', '1.', '2024-05-09 00:00:00', 2),
(NULL, 3, '2', '仇恨言論', '1.', '2024-05-09 00:00:00', 5);


-- 討論區圖片
-- CREATE TABLE forum_images (
--     forum_img_id INT PRIMARY KEY AUTO_INCREMENT,
--     article_id INT,
--     comment_id INT,
--     forum_img_file VARCHAR(255),
--     FOREIGN KEY (article_id) REFERENCES article(article_id),
--     FOREIGN KEY (comment_id) REFERENCES comments(comment_id)
-- );

-- INSERT INTO forum_images (article_id, comment_id, forum_img_file) VALUES
-- (1, NULL, 'https://example.com/image1.jpg'),
-- (2, NULL, 'https://example.com/image2.jpg'),
-- (NULL, 1, 'https://example.com/image3.jpg'),
-- (NULL, 2, 'https://example.com/image4.jpg'),
-- (3, NULL, 'https://example.com/image5.jpg'),
-- (NULL, 3, 'https://example.com/image6.jpg'),
-- (4, NULL, 'https://example.com/image7.jpg'),
-- (NULL, 4, 'https://example.com/image8.jpg'),
-- (5, NULL, 'https://example.com/image9.jpg'),
-- (NULL, 5, 'https://example.com/image10.jpg');

USE tia101_g2;