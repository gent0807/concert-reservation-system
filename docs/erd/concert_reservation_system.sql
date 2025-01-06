CREATE TABLE `concert_basic` (
  `concert_basic_id` bigint PRIMARY KEY,
  `concert_name` varchar(255),
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `deleted_at` timestamp
);

CREATE TABLE `concert_detail` (
  `concert_detail_id` bigint PRIMARY KEY,
  `concert_basic_id` bigint UNIQUE,
  `concert_detail_status` varchar(255) DEFAULT 'reservable',
  `start_date` timestamp UNIQUE,
  `end_date` timestamp UNIQUE,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `deleted_at` timestamp
);

CREATE TABLE `seat` (
  `seat_id` bigint PRIMARY KEY,
  `concert_detail_id` bigint UNIQUE,
  `seat_number` bigint UNIQUE,
  `price` integer NOT NULL,
  `seat_status` varchar(255) DEFAULT 'reservable',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `deleted_at` timestamp
);

CREATE TABLE `user` (
  `user_id` varchar(255) PRIMARY KEY,
  `user_name` varchar(255),
  `age` integer,
  `gender` varchar(255),
  `point` integer,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `deleted_at` timestamp
);

CREATE TABLE `token` (
  `waiting_id` bigint PRIMARY KEY,
  `user_id` varchar(255),
  `token_status` varchar(255),
  `expired_at` timestamp,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `deleted_at` timestamp
);

CREATE TABLE `reservation` (
  `reservation_id` bigint PRIMARY KEY,
  `seat_id` bigint,
  `user_id` varchar(255),
  `payment_id` bigint,
  `reservation_status` varchar(255) DEFAULT 'temp',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `deleted_at` timestamp
);

CREATE TABLE `payment` (
  `payment_id` bigint PRIMARY KEY,
  `payment_status` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp,
  `delted_at` timestamp
);

CREATE TABLE `point_history` (
  `point_history_id` bigint PRIMARY KEY,
  `payment_id` bigint,
  `created_at` timestamp,
  `updated_at` timestamp,
  `delted_at` timestamp
);

ALTER TABLE `concert_detail` ADD FOREIGN KEY (`concert_basic_id`) REFERENCES `concert_basic` (`concert_basic_id`);

ALTER TABLE `seat` ADD FOREIGN KEY (`concert_detail_id`) REFERENCES `concert_detail` (`concert_detail_id`);

ALTER TABLE `token` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

ALTER TABLE `reservation` ADD FOREIGN KEY (`seat_id`) REFERENCES `seat` (`seat_id`);

ALTER TABLE `reservation` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

ALTER TABLE `reservation` ADD FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`);

ALTER TABLE `point_history` ADD FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`);
