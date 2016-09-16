CREATE TABLE public.empresa
(
  id_empresa bigint NOT NULL,
  razao_social character varying(255) NOT NULL,
  CONSTRAINT empresa_pkey PRIMARY KEY (id_empresa)
) WITH (OIDS=FALSE);

ALTER TABLE public.empresa OWNER TO postgres;

CREATE INDEX idx_id_empresa ON public.empresa USING btree(id_empresa);