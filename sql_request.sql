INSERT INTO public.person(
	"firstName", "lastName", email, "phoneNumber", password)
	VALUES ( 'Maxime', 'Quoilin', 'max.quoilin@gmail.com', '0498407095','azer');
	
	INSERT INTO public.person(
	"firstName", "lastName", email, "phoneNumber", password)
	VALUES ( 'Damien', 'Bouffioux', 'damien.bouffioux@gmail.com', '0479308008','azer');
	
	INSERT INTO public.event(
	"startDate", "endDate", name, creator_id)
	VALUES ('2019-08-01 08:30:00', '2019-08-10 17:00:00', 'Week-end sportif', 5);
	
	INSERT INTO public.event(
	"startDate", "endDate", name, creator_id)
	VALUES ('2019-09-01 08:30:00', '2019-09-10 17:00:00', 'Event E-sport', 6);
	
	INSERT INTO public.activity(
	name, "startDate", "endDate", creator_id)
	VALUES ('Kayak', '2019-08-01 08:30:00', '2019-08-01 12:30:00', 5);
	INSERT INTO public.activity(
	name, "startDate", "endDate", creator_id)
	VALUES ('Counter Strike', '2019-09-01 08:30:00', '2019-09-01 12:30:00', 6);
	
	
	INSERT INTO public.registration(
	id_person, id_activity)
	VALUES ( 5, 1);
	
	INSERT INTO public.registration(
	id_person, id_activity)
	VALUES ( 6, 3);