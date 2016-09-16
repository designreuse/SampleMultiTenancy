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

CREATE SEQUENCE public.seq_empresa
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_empresa
  OWNER TO postgres;