-- Consulta SQL para obtener autores por ISBN
SELECT
    a.Nombre,
    a.Apellidos
FROM
    Autor a
JOIN
    TITULO_AUTOR ta ON a.ID = ta.Autor_ID
JOIN
    Titulo t ON ta.Titulo_ISBN = t.ISBN
WHERE
    t.ISBN = 1234567890123;
