DROP TABLE IF EXISTS public.company_owner CASCADE;
DROP TABLE IF EXISTS public.owner CASCADE;
DROP TABLE IF EXISTS public.company CASCADE;

-- table company
CREATE TABLE public.company
(
   id bigserial NOT NULL,
   name character varying NOT NULL,
   address character varying NOT NULL,
   city character varying NOT NULL,
   country character varying NOT NULL,
   email character varying,
   phone_number character varying,
   CONSTRAINT pk_company_id PRIMARY KEY (id)
)
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.company OWNER TO company;

-- table owner
CREATE TABLE public.owner
(
   id bigserial NOT NULL,
   name character varying NOT NULL,
   CONSTRAINT pk_owner_id PRIMARY KEY (id),
   CONSTRAINT uq_owner_name UNIQUE (name)
)
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.company OWNER TO company;

-- join table company_owner
CREATE TABLE public.company_owner
(
   company_id bigint NOT NULL,
   owner_id bigint NOT NULL,
   CONSTRAINT fk_co_company_id FOREIGN KEY (company_id) REFERENCES company (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT fk_co_owner_id FOREIGN KEY (owner_id) REFERENCES owner (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT uq_owner_per_company UNIQUE (company_id, owner_id)
)
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.company_owner
  OWNER TO company;

