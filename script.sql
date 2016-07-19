create table categoria(
	id INTEGER not null PRIMARY KEY AUTOINCREMENT,
	descripcion TEXT not null
);

create table presupuesto(
	month INTEGER not null,
	year INTEGER not null,
	idCategoria INTEGER not null,
	monto INTEGER not null,
	foreign key(idCategoria) references categoria(id) on delete cascade on update cascade,
	PRIMARY KEY(month,year,idCategoria)
	
);

create table gasto(
	id INTEGER not null PRIMARY KEY AUTOINCREMENT,
	idCategoria INTEGER not null,
	monto INTEGER not null,
	fecha TEXT not null,
	foreign key(idCategoria) references categoria(id) on delete cascade on update cascade
);

insert into categoria(descripcion) values ("Basicos"),("Combustible"),("Supermercado"),("Extras");