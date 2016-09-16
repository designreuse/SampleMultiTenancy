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