INSERT INTO users (enabled, id, email, password, user_name) VALUES (true,1,'juan@correo.com','1234','juancho')
INSERT INTO users (enabled, id, email, password, user_name) VALUES (true,2,'carlos@corre.com','1234','caliche')
INSERT INTO users (enabled, id, email, password, user_name) VALUES (true,3,'emma@correo.com','1234','emy')
INSERT INTO users (enabled, id, email, password, user_name) VALUES (false,4,'francisco@correo.com','1234','pacho')
INSERT INTO users (enabled, id, email, password, user_name) VALUES (true,5,'leonardo@correo.com','1234','leo')

INSERT INTO roles (id, name) VALUES (1,'ROLE_USER')
INSERT INTO roles (id, name) VALUES (2,'ROLE_ADMIN')
INSERT INTO roles (id, name) VALUES (3,'ROLE_MODERATOR')


INSERT INTO users_roles (role_id, user_id) VALUES (1,1)
INSERT INTO users_roles (role_id, user_id) VALUES (1,2)
INSERT INTO users_roles (role_id, user_id) VALUES (2,2)
INSERT INTO users_roles (role_id, user_id) VALUES (1,3)
INSERT INTO users_roles (role_id, user_id) VALUES (1,4)
INSERT INTO users_roles (role_id, user_id) VALUES (1,5)