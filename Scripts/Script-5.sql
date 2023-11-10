-- Crear 10 títulos ficticios con ISBN como bigint
INSERT INTO Titulo (isbn, titulo, num_Reserva)
VALUES
    (9780451524935, 'Moby-Dick', 0),
    (9781984962178, 'Pride and Prejudice', 0),
    (9780486280615, 'The Great Gatsby', 0),
    (9780143128542, '1984', 0),
    (9780679722762, 'To Kill a Mockingbird', 0),
    (9780679732769, 'Brave New World', 0),
    (9780743273565, 'The Catcher in the Rye', 0),
    (9780743273565, 'The Catcher in the Rye', 0),
    (9780743273565, 'The Catcher in the Rye', 0),
    (9781400083817, 'The Book Thief', 0);

-- Crear las relaciones entre autores y títulos utilizando ISBN
-- Asegúrate de ajustar los ISBN según tus títulos reales
INSERT INTO Titulo_Autor (autor_id, titulo_id)
VALUES
    (7, 9780451524935),
    (8, 9781984962178),
    (9, 9780486280615),
    (10, 9780143128542),
    (11, 9780679722762),
    (12, 9780679732769),
    (13, 9780743273565),
    (14, 9780743273565),
    (15, 9780743273565),
    (16, 9781400083817);
