create view NomTrabajador_View
as     select t1.rifEmpresa, t2.tipoNomina, t1.nroTrabajador, cedulaID , t1.nombre1, t1.apellido1 
from   NomTrabajador00_Dat t1 LEFT JOIN NomTrabajador01_Dat t2
on     ( t1.rifEmpresa = t2.rifEmpresa and t1.nroTrabajador = t2.nroTrabajador )
order  by t1.rifEmpresa, t2.tipoNomina, t1.nroTrabajador 
