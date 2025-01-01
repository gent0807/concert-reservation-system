CREATE TABLE `concerts` (
  `concert_id` bigint PRIMARY KEY,
  `concert_name` varchar(255),
  `genre_name` varchar(255),
  `start_date` datetime,
  `end_date` timestamp,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `reservations` (
  `reservation_id` bigint PRIMARY KEY,
  `member_id` bigint,
  `stand_id` bigint,
  `status` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `stands` (
  `stand_id` bigint PRIMARY KEY,
  `concert_id` bigint UNIQUE,
  `stand_column` varchar(255) UNIQUE,
  `stand_row` integer UNIQUE,
  `status` varchar(255),
  `price` integer,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `members` (
  `member_id` varchar(255) PRIMARY KEY,
  `balance` int,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `waitings` (
  `waiting_id` bigint PRIMARY KEY,
  `member_id` varchar(255),
  `stand_id` bigint,
  `status` varchar(255) DEFAULT 'wait',
  `expired_at` timestamp,
  `created_at` timestamp,
  `updated_at` timestamp,
  `deleted_at` timestamp
);

ALTER TABLE `reservations` ADD FOREIGN KEY (`stand_id`) REFERENCES `stands` (`stand_id`);

ALTER TABLE `reservations` ADD FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`);

ALTER TABLE `stands` ADD FOREIGN KEY (`concert_id`) REFERENCES `concerts` (`concert_id`);

ALTER TABLE `waitings` ADD FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`);

ALTER TABLE `waitings` ADD FOREIGN KEY (`stand_id`) REFERENCES `stands` (`stand_id`);
