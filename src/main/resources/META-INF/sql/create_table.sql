/* 
 * Copyright (C) 2016 João Paulo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * Author:  João Paulo
 * Created: 16/09/2016
 */

CREATE SEQUENCE public.seq_system_database
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_system_database
  OWNER TO postgres;

CREATE SEQUENCE public.seq_system_module
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_system_module
  OWNER TO postgres;

CREATE SEQUENCE public.seq_system_user
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_system_user
  OWNER TO postgres;

/*Tabelas*/

/*system_database*/

CREATE TABLE public.system_database
(
  id_system_database bigint NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255),
  server character varying(255),
  username character varying(255),
  version character varying(255),
  CONSTRAINT system_database_pkey PRIMARY KEY (id_system_database)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.system_database
  OWNER TO postgres;

CREATE INDEX idx_id_system_database
  ON public.system_database
  USING btree
  (id_system_database);

CREATE INDEX idx_name
  ON public.system_database
  USING btree
  (name COLLATE pg_catalog."default");

/*system_module*/

CREATE TABLE public.system_module
(
  id_system_module bigint NOT NULL,
  system_module_category character varying(255) NOT NULL,
  system_module_name character varying(255) NOT NULL,
  CONSTRAINT system_module_pkey PRIMARY KEY (id_system_module)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.system_module
  OWNER TO postgres;

CREATE INDEX idx_id_system_module
  ON public.system_module
  USING btree
  (id_system_module);

/*system_user*/

CREATE TABLE public.system_user
(
  id_system_user bigint NOT NULL,
  account_can_expire boolean NOT NULL,
  account_expiration date,
  credential_can_expire boolean NOT NULL,
  credential_expiration date,
  email character varying(100) NOT NULL,
  enabled boolean NOT NULL,
  first_name character varying(100) NOT NULL,
  last_name character varying(100) NOT NULL,
  locked boolean NOT NULL,
  middle_name character varying(100),
  password character varying(256) NOT NULL,
  systemdatabase_id_system_database bigint,
  CONSTRAINT system_user_pkey PRIMARY KEY (id_system_user),
  CONSTRAINT fk_dnodi6ae95crgrweb3shlg2st FOREIGN KEY (systemdatabase_id_system_database)
      REFERENCES public.system_database (id_system_database) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_3ypdb9457wfdya51dfk3ul642 UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.system_user
  OWNER TO postgres;

CREATE INDEX idx_email
  ON public.system_user
  USING btree
  (email COLLATE pg_catalog."default");

CREATE INDEX idx_id_system_user
  ON public.system_user
  USING btree
  (id_system_user);

/*system_user_permission*/

CREATE TABLE public.system_user_permission
(
  add boolean NOT NULL,
  delete boolean NOT NULL,
  edit boolean NOT NULL,
  read boolean NOT NULL,
  id_system_module bigint NOT NULL,
  id_system_user bigint NOT NULL,
  CONSTRAINT system_user_permission_pkey PRIMARY KEY (id_system_module, id_system_user),
  CONSTRAINT fk_ha9kiacrc4m1ct9lbw1gchkf1 FOREIGN KEY (id_system_module)
      REFERENCES public.system_module (id_system_module) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_kok5aeoth3eq7cj7hbtrupjc4 FOREIGN KEY (id_system_user)
      REFERENCES public.system_user (id_system_user) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.system_user_permission
  OWNER TO postgres;