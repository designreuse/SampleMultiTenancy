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