
CREATE SEQUENCE public.seq_system_database INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
ALTER TABLE public.seq_system_database OWNER TO postgres;

CREATE SEQUENCE public.seq_system_module INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
ALTER TABLE public.seq_system_module OWNER TO postgres;

CREATE SEQUENCE public.seq_system_user INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
ALTER TABLE public.seq_system_user OWNER TO postgres;

CREATE SEQUENCE public.seq_empresa INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
ALTER TABLE public.seq_empresa OWNER TO postgres;

CREATE TABLE public.empresa(id_empresa bigint NOT NULL, razao_social character varying(255) NOT NULL, CONSTRAINT empresa_pkey PRIMARY KEY (id_empresa)) WITH (OIDS=FALSE);
ALTER TABLE public.empresa OWNER TO postgres;
CREATE INDEX idx_id_empresa ON public.empresa USING btree(id_empresa);

CREATE TABLE public.system_module (id_system_module bigint NOT NULL, system_module_category character varying(255) NOT NULL, system_module_name character varying(255) NOT NULL, CONSTRAINT system_module_pkey PRIMARY KEY (id_system_module))WITH (OIDS=FALSE);
ALTER TABLE public.system_module OWNER TO postgres;
CREATE INDEX idx_id_system_module ON public.system_module USING btree(id_system_module);

CREATE TABLE public.system_database(id_system_database bigint NOT NULL, name character varying(255) NOT NULL, password character varying(255), server character varying(255), username character varying(255), version character varying(255), CONSTRAINT system_database_pkey PRIMARY KEY (id_system_database)) WITH (OIDS=FALSE);
ALTER TABLE public.system_database OWNER TO postgres;
CREATE INDEX idx_id_system_database ON public.system_database USING btree(id_system_database);
CREATE INDEX idx_name ON public.system_database USING btree(name COLLATE pg_catalog."default");

CREATE TABLE public.system_user(id_system_user bigint NOT NULL, account_can_expire boolean NOT NULL, account_expiration date, credential_can_expire boolean NOT NULL, credential_expiration date, email character varying(100) NOT NULL, enabled boolean NOT NULL, first_name character varying(100) NOT NULL, last_name character varying(100) NOT NULL, locked boolean NOT NULL, middle_name character varying(100), password character varying(256) NOT NULL, systemdatabase_id_system_database bigint, CONSTRAINT system_user_pkey PRIMARY KEY (id_system_user), CONSTRAINT fk_dnodi6ae95crgrweb3shlg2st FOREIGN KEY (systemdatabase_id_system_database) REFERENCES public.system_database (id_system_database) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION, CONSTRAINT uk_3ypdb9457wfdya51dfk3ul642 UNIQUE (email)) WITH (OIDS=FALSE);
ALTER TABLE public.system_user OWNER TO postgres;
CREATE INDEX idx_email ON public.system_user USING btree (email COLLATE pg_catalog."default");
CREATE INDEX idx_id_system_user ON public.system_user USING btree (id_system_user);

CREATE TABLE public.system_user_permission(add boolean NOT NULL, delete boolean NOT NULL, edit boolean NOT NULL, read boolean NOT NULL, id_system_module bigint NOT NULL, id_system_user bigint NOT NULL, CONSTRAINT system_user_permission_pkey PRIMARY KEY (id_system_module, id_system_user), CONSTRAINT fk_ha9kiacrc4m1ct9lbw1gchkf1 FOREIGN KEY (id_system_module) REFERENCES public.system_module (id_system_module) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION, CONSTRAINT fk_kok5aeoth3eq7cj7hbtrupjc4 FOREIGN KEY (id_system_user) REFERENCES public.system_user (id_system_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION )WITH (OIDS=FALSE);
ALTER TABLE public.system_user_permission OWNER TO postgres;

INSERT INTO public.empresa(id_empresa, razao_social) VALUES (nextval('seq_empresa'), 'Master Sistemas');

INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'MODULE', 'SYSTEM');
INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'USER', 'SYSTEM');
INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'PERMISSION', 'SYSTEM');
INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'DATABASE', 'SYSTEM');

INSERT INTO public.system_database(id_system_database, name, password, server, username, version) VALUES (nextval('seq_system_database'), 'master', 'postgres', 'nop', 'postgres', 'nop');

INSERT INTO public.system_user(id_system_user, account_can_expire, account_expiration, credential_can_expire, credential_expiration, email, enabled, first_name, last_name, locked, middle_name, password, systemdatabase_id_system_database) VALUES (nextval('seq_system_user'), false, null, false, null, 'admin@smt.com', true, 'Admin', 'Admin', false, 'Admin', '482a16b62579c55c95eb6153af1ffec050b74b645b40e750638e07db4076e2b8e34a7e2a89f5a701', 1);

INSERT INTO public.system_user_permission(add, delete, edit, read, id_system_module, id_system_user) VALUES (true, true, true, true, 1, 1);
INSERT INTO public.system_user_permission(add, delete, edit, read, id_system_module, id_system_user) VALUES (true, true, true, true, 2, 1);
INSERT INTO public.system_user_permission(add, delete, edit, read, id_system_module, id_system_user) VALUES (true, true, true, true, 3, 1);
INSERT INTO public.system_user_permission(add, delete, edit, read, id_system_module, id_system_user) VALUES (true, true, true, true, 4, 1);
