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