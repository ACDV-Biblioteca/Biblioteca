CREATE TABLE DERBYUSER.AUTORES_TITULO (
  nombre_autor varchar(45) NOT NULL,
  apellidos_autor varchar(45) NOT NULL,
  id_titulo int NOT NULL,
  fecha date NOT NULL,
  PRIMARY KEY (nombre_autor,apellidos_autor,id_titulo),
  CONSTRAINT Autor_Titulo_ISBN_FK FOREIGN KEY (id_titulo) REFERENCES DERBYUSER.titulo (isbn),
  CONSTRAINT Autor_Titulo_Nombre_Apellidos_FK FOREIGN KEY (nombre_autor,apellidos_autor) REFERENCES DERBYUSER.autor (NOMBRE,APELLIDOS)

);