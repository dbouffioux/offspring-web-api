CREATE TABLE public.activity
(
    id bigint NOT NULL DEFAULT nextval('activity_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    "startDate" date NOT NULL,
    "endDate" date NOT NULL,
    creator_id bigint NOT NULL,
    event_id bigint NOT NULL,
    "endTime" time without time zone,
    "startTime" time without time zone,
    CONSTRAINT pk_activity PRIMARY KEY (id),
    CONSTRAINT fk_event FOREIGN KEY (event_id)
        REFERENCES public.event (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pk_person FOREIGN KEY (creator_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.event
(
    "startDate" date NOT NULL,
    "endDate" date NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    id bigint NOT NULL DEFAULT nextval('event_id_seq'::regclass),
    creator_id bigint,
    "endTime" time without time zone,
    "startTime" time without time zone,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_person FOREIGN KEY (creator_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.event
    OWNER to postgres;
COMMENT ON TABLE public.event
    IS 'Represents events ';

CREATE TABLE public.person
(
    id bigint NOT NULL DEFAULT nextval('"Person_id_seq"'::regclass),
    "firstName" character varying COLLATE pg_catalog."default" NOT NULL,
    "lastName" character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    "phoneNumber" character varying COLLATE pg_catalog."default",
    password character varying(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_person PRIMARY KEY (id),
    CONSTRAINT un_email UNIQUE (email)
,
    CONSTRAINT ch_pwd_lenght CHECK (length(password::text) > 3) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.person
    OWNER to postgres;

CREATE TABLE public.registration
(
    id bigint NOT NULL DEFAULT nextval('registration_id_seq'::regclass),
    id_person bigint NOT NULL,
    id_activity bigint NOT NULL,
    CONSTRAINT pk_registration PRIMARY KEY (id),
    CONSTRAINT co_unique_registration UNIQUE (id_person, id_activity)
,
    CONSTRAINT fk_activity FOREIGN KEY (id_activity)
        REFERENCES public.activity (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_person FOREIGN KEY (id_person)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.registration
    OWNER to postgres;