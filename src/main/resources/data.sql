insert into role(id, name) values(1, 'ROLE_ADMIN');
insert into role(id, name) values(2, 'ROLE_USER');
insert into user(id, username, password, name) values (1, 'admin@admin.com', '$2a$10$x5q0Z1Lr6zU9y78GpfmmNePgzex1n7nQf5rYyGMjsWsKHPC5h6wc.', 'Administrator');
insert into user_role(user_id, role_id) values(1, 1);
insert into tipo_vinho(id, descricao) values (1, 'Seco');
insert into tipo_vinho(id, descricao) values (2, 'Suave');