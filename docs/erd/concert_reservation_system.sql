CREATE TABLE `genres` (
  `genre_id` bigint PRIMARY KEY,
  `genre_name` varcahr,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `concerts` (
  `concert_id` bigint PRIMARY KEY,
  `concert_name` varchar(255),
  `genre_id` bigint,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `concert_detail_registers` (
  `concert_detail_register_id` bigint PRIMARY KEY,
  `concert_detail_register_name` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `concert_details` (
  `concert_detail_id` bigint PRIMARY KEY,
  `concert_id` bigint UNIQUE,
  `start_date` timestamp UNIQUE,
  `end_datee` timestamp UNIQUE,
  `concert_detail_register_id` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `artists` (
  `artist_id` bigint PRIMARY KEY,
  `artist_name` varchar(255),
  `artist_age` integer,
  `artist_gender` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `casts` (
  `cast_id` bigint PRIMARY KEY,
  `concert_detail_id` bigint,
  `arist_id` bigint,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `seats` (
  `seat_id` bigint PRIMARY KEY,
  `concert_detail_id` bigint UNIQUE,
  `seat_number` bigint UNIQUE,
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
  `seat_id` bigint,
  `status` varchar(255) DEFAULT 'wait',
  `expired_at` timestamp,
  `created_at` timestamp,
  `updated_at` timestamp,
  `deleted_at` timestamp
);

CREATE TABLE `reservations` (
  `reservation_id` bigint PRIMARY KEY,
  `seat_id` bigint,
  `member_id` bigint,
  `status` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

ALTER TABLE `concerts` ADD FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`);

ALTER TABLE `concert_details` ADD FOREIGN KEY (`concert_id`) REFERENCES `concerts` (`concert_id`);

ALTER TABLE `concert_details` ADD FOREIGN KEY (`concert_detail_register_id`) REFERENCES `concert_detail_registers` (`concert_detail_register_id`);

ALTER TABLE `casts` ADD FOREIGN KEY (`concert_detail_id`) REFERENCES `concert_details` (`concert_detail_id`);

ALTER TABLE `casts` ADD FOREIGN KEY (`arist_id`) REFERENCES `artists` (`artist_id`);

ALTER TABLE `seats` ADD FOREIGN KEY (`concert_detail_id`) REFERENCES `concert_details` (`concert_detail_id`);

ALTER TABLE `waitings` ADD FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`);

ALTER TABLE `reservations` ADD FOREIGN KEY (`seat_id`) REFERENCES `seats` (`seat_id`);

ALTER TABLE `reservations` ADD FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`);
