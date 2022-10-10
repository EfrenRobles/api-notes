CREATE TABLE notes (
	note_id uuid NOT NULL,
	user_id uuid NOT NULL,
	note_title varchar(50) NOT NULL,
	note_message varchar(1000) NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	CONSTRAINT notes_pkey PRIMARY KEY (note_id)
);

CREATE INDEX notes_user_id_idx ON public.notes (user_id);