--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 11.2

-- Started on 2019-07-02 08:57:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2862 (class 1262 OID 17249)
-- Name: offspring; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE offspring WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_Belgium.1252' LC_CTYPE = 'French_Belgium.1252';


ALTER DATABASE offspring OWNER TO postgres;

\connect offspring

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 17250)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    "firstName" character varying NOT NULL,
    "lastName" character varying NOT NULL,
    email character varying NOT NULL,
    "phoneNumber" character varying,
    password character varying(15) NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 17256)
-- Name: Person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Person_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Person_id_seq" OWNER TO postgres;

--
-- TOC entry 2863 (class 0 OID 0)
-- Dependencies: 197
-- Name: Person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Person_id_seq" OWNED BY public.person.id;


--
-- TOC entry 198 (class 1259 OID 17258)
-- Name: activity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.activity (
    id bigint NOT NULL,
    name character varying NOT NULL,
    "startDate" date NOT NULL,
    "endDate" date NOT NULL,
    creator_id bigint NOT NULL,
    event_id bigint NOT NULL,
    "endTime" time without time zone,
    "startTime" time without time zone
);


ALTER TABLE public.activity OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 17264)
-- Name: activity_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.activity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.activity_id_seq OWNER TO postgres;

--
-- TOC entry 2864 (class 0 OID 0)
-- Dependencies: 199
-- Name: activity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.activity_id_seq OWNED BY public.activity.id;


--
-- TOC entry 200 (class 1259 OID 17266)
-- Name: event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event (
    "startDate" date NOT NULL,
    "endDate" date NOT NULL,
    name character varying NOT NULL,
    id bigint NOT NULL,
    creator_id bigint,
    "endTime" time without time zone,
    "startTime" time without time zone
);


ALTER TABLE public.event OWNER TO postgres;

--
-- TOC entry 2865 (class 0 OID 0)
-- Dependencies: 200
-- Name: TABLE event; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.event IS 'Represents events ';


--
-- TOC entry 201 (class 1259 OID 17272)
-- Name: event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.event_id_seq OWNER TO postgres;

--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 201
-- Name: event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.event_id_seq OWNED BY public.event.id;


--
-- TOC entry 202 (class 1259 OID 17274)
-- Name: registration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registration (
    id bigint NOT NULL,
    id_person bigint NOT NULL,
    id_activity bigint NOT NULL
);


ALTER TABLE public.registration OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 17277)
-- Name: registration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.registration_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.registration_id_seq OWNER TO postgres;

--
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 203
-- Name: registration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.registration_id_seq OWNED BY public.registration.id;


--
-- TOC entry 2708 (class 2604 OID 17321)
-- Name: activity id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity ALTER COLUMN id SET DEFAULT nextval('public.activity_id_seq'::regclass);


--
-- TOC entry 2709 (class 2604 OID 17322)
-- Name: event id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event ALTER COLUMN id SET DEFAULT nextval('public.event_id_seq'::regclass);


--
-- TOC entry 2706 (class 2604 OID 17323)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public."Person_id_seq"'::regclass);


--
-- TOC entry 2710 (class 2604 OID 17324)
-- Name: registration id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registration ALTER COLUMN id SET DEFAULT nextval('public.registration_id_seq'::regclass);


--
-- TOC entry 2851 (class 0 OID 17258)
-- Dependencies: 198
-- Data for Name: activity; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.activity (id, name, "startDate", "endDate", creator_id, event_id, "endTime", "startTime") VALUES (11, 'Kayak', '2019-08-01', '2019-08-01', 5, 5, NULL, NULL);
INSERT INTO public.activity (id, name, "startDate", "endDate", creator_id, event_id, "endTime", "startTime") VALUES (12, 'Counter Strike', '2019-09-01', '2019-09-01', 6, 6, NULL, NULL);


--
-- TOC entry 2853 (class 0 OID 17266)
-- Dependencies: 200
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.event ("startDate", "endDate", name, id, creator_id, "endTime", "startTime") VALUES ('2019-08-01', '2019-08-10', 'Week-end sportif', 5, 5, NULL, NULL);
INSERT INTO public.event ("startDate", "endDate", name, id, creator_id, "endTime", "startTime") VALUES ('2019-09-01', '2019-09-10', 'Event E-sport', 6, 6, NULL, NULL);


--
-- TOC entry 2849 (class 0 OID 17250)
-- Dependencies: 196
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person (id, "firstName", "lastName", email, "phoneNumber", password) VALUES (5, 'Maxime', 'Quoilin', 'max.quoilin@gmail.com', '0498407095', 'azer');
INSERT INTO public.person (id, "firstName", "lastName", email, "phoneNumber", password) VALUES (6, 'Damien', 'Bouffioux', 'damien.bouffioux@gmail.com', '0479308008', 'azer');


--
-- TOC entry 2855 (class 0 OID 17274)
-- Dependencies: 202
-- Data for Name: registration; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.registration (id, id_person, id_activity) VALUES (9, 5, 11);
INSERT INTO public.registration (id, id_person, id_activity) VALUES (10, 6, 12);


--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 197
-- Name: Person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Person_id_seq"', 6, true);


--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 199
-- Name: activity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.activity_id_seq', 12, true);


--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 201
-- Name: event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.event_id_seq', 6, true);


--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 203
-- Name: registration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.registration_id_seq', 10, true);


--
-- TOC entry 2707 (class 2606 OID 17283)
-- Name: person ch_pwd_lenght; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.person
    ADD CONSTRAINT ch_pwd_lenght CHECK ((length((password)::text) > 3)) NOT VALID;


--
-- TOC entry 2720 (class 2606 OID 17285)
-- Name: registration co_unique_registration; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registration
    ADD CONSTRAINT co_unique_registration UNIQUE (id_person, id_activity);


--
-- TOC entry 2716 (class 2606 OID 17287)
-- Name: activity pk_activity; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT pk_activity PRIMARY KEY (id);


--
-- TOC entry 2718 (class 2606 OID 17289)
-- Name: event pk_event; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT pk_event PRIMARY KEY (id);


--
-- TOC entry 2712 (class 2606 OID 17291)
-- Name: person pk_person; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT pk_person PRIMARY KEY (id);


--
-- TOC entry 2722 (class 2606 OID 17293)
-- Name: registration pk_registration; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registration
    ADD CONSTRAINT pk_registration PRIMARY KEY (id);


--
-- TOC entry 2714 (class 2606 OID 17295)
-- Name: person un_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT un_email UNIQUE (email);


--
-- TOC entry 2726 (class 2606 OID 17296)
-- Name: registration fk_activity; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registration
    ADD CONSTRAINT fk_activity FOREIGN KEY (id_activity) REFERENCES public.activity(id);


--
-- TOC entry 2723 (class 2606 OID 17301)
-- Name: activity fk_event; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES public.event(id);


--
-- TOC entry 2727 (class 2606 OID 17306)
-- Name: registration fk_person; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registration
    ADD CONSTRAINT fk_person FOREIGN KEY (id_person) REFERENCES public.person(id);


--
-- TOC entry 2725 (class 2606 OID 17311)
-- Name: event fk_person; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT fk_person FOREIGN KEY (creator_id) REFERENCES public.person(id);


--
-- TOC entry 2724 (class 2606 OID 17316)
-- Name: activity pk_person; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT pk_person FOREIGN KEY (creator_id) REFERENCES public.person(id);


-- Completed on 2019-07-02 08:57:30

--
-- PostgreSQL database dump complete
--

