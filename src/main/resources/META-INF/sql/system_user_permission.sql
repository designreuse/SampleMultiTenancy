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