create table categoria(
	titulo TEXT not null PRIMARY KEY,
	descripcion TEXT not null
);

create table presupuesto(
	month INTEGER not null,
	year INTEGER not null,
	idCategoria TEXT not null,
	monto INTEGER not null,
	foreign key(idCategoria) references categoria(titulo) on delete cascade on update cascade,
	PRIMARY KEY(month,year,idCategoria)
	
);

create table gasto(
	id INTEGER not null PRIMARY KEY AUTOINCREMENT,
	idCategoria TEXT not null,
	monto INTEGER not null,
	fecha TEXT not null,
	foreign key(idCategoria) references categoria(titulo) on delete cascade on update cascade
);

insert into categoria(titulo,descripcion) values ("Basicos","Basicos"),("Combustible","Combustible"),("Supermercado","Supermercado"),("Extras","Extras");