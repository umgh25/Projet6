INSERT IGNORE INTO `user` (id, email, user_name, password)
VALUES (1, 'john@gmail.com', 'john', '$2a$10$OEoMfE6/JEwOXqrkj10Veu4.vMRX25ePdeJ4IiJpuL7q0LobJ6Cry'),
       (2, 'alice@gmail.com', 'alice', '$2a$10$OEoMfE6/JEwOXqrkj10Veu4.vMRX25ePdeJ4IiJpuL7q0LobJ6Cry'),
       (3, 'bob@gmail.com', 'bob', '$2a$10$OEoMfE6/JEwOXqrkj10Veu4.vMRX25ePdeJ4IiJpuL7q0LobJ6Cry');

INSERT IGNORE INTO `topic` (id, title,  description)
VALUES (1, 'Java', 'Java language'),
       (2, 'Python', 'Python language'),
       (3, 'Javascript', 'Javascript language'),
       (4, 'C', 'C language');

INSERT IGNORE INTO `subscription` (user_id, topic_id)
VALUES (1,1),
       (1,2),
       (1,3),
       (1,4),
       (2,1);

INSERT IGNORE INTO `post` (id, title, content, created_at, user_id, topic_id)
VALUES (1, 'Java POO', 'Lorem ipsum dolor sit amet. Eum rerum officiis a quasi minima id eveniet praesentium id doloribus dolorem qui veniam magnam et corrupti quidem. Id sapiente nihil et ipsa voluptatem qui dolore minima. Et suscipit itaque non quis galisum ut blanditiis saepe ut voluptatem dolorum ut laboriosam aspernatur.', '2025-01-03', 1, 1),
       (2, 'Java other', 'Lorem ipsum dolor sit amet. Eum rerum officiis a quasi minima id eveniet praesentium id doloribus dolorem qui veniam magnam et corrupti quidem. Id sapiente nihil et ipsa voluptatem qui dolore minima. Et suscipit itaque non quis galisum ut blanditiis saepe ut voluptatem dolorum ut laboriosam aspernatur.', '2025-01-02',2,1),
       (3, 'Python', 'Lorem ipsum dolor sit amet. Eum rerum officiis a quasi minima id eveniet praesentium id doloribus dolorem qui veniam magnam et corrupti quidem. Id sapiente nihil et ipsa voluptatem qui dolore minima. Et suscipit itaque non quis galisum ut blanditiis saepe ut voluptatem dolorum ut laboriosam aspernatur.', '2025-01-01', 1, 2);

INSERT IGNORE INTO `comment` (id, content, created_at, user_id, post_id)
VALUES (1, 'first comment for Java POO article', '2025-01-01', 1, 1),
       (2, 'second comment for Java POO article', '2025-01-01', 3, 1);