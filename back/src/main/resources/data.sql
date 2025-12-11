INSERT IGNORE INTO `user` (id, email, user_name, password)
VALUES (1, 'john@gmail.com', 'john', '$2a$10$tUj/tK3yB2hH1oIqG5pE.ezb429Jm9Mv6qY.F7dKx/c8rZzW0q1yG'),
       (2, 'alice@gmail.com', 'alice', '$2a$10$tUj/tK3yB2hH1oIqG5pE.ezb429Jm9Mv6qY.F7dKx/c8rZzW0q1yG'),
       (3, 'bob@gmail.com', 'bob', '$2a$10$tUj/tK3yB2hH1oIqG5pE.ezb429Jm9Mv6qY.F7dKx/c8rZzW0q1yG');

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

INSERT IGNORE INTO `post` (id, title, description, created_at, user_id, topic_id)
VALUES (1, 'Java POO', 'Java POO written by john', '2025-01-01', 1, 1),
       (2, 'Java other', 'Java POO other arti written by alice', '2025-01-01',2,1),
       (3, 'Python', 'Python written by john', '2025-01-01', 1, 2);

INSERT IGNORE INTO `comment` (id, content, created_at, user_id, post_id)
VALUES (1, 'first comment for Java POO article', '2025-01-01', 1, 1),
       (2, 'second comment for Java POO article', '2025-01-01', 3, 1);