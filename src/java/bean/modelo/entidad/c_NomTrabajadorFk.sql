alter table NomTrabajador01_Dat add CONSTRAINT NomTrabajador00_Fk 
FOREIGN key ( rifEmpresa ) REFERENCES NomEmpresa_Dat( rif )

alter table NomTrabajador01_Dat add CONSTRAINT NomTrabajador01_Fk 
FOREIGN key ( nroTrabajador ) REFERENCES NomTrabajador00_Dat( nroTrabajador )

