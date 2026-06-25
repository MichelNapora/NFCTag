-- ============================================================
-- Données de démonstration
-- ============================================================

INSERT INTO business (name, bce) VALUES
    ('Électricité Dupont SPRL', 'BE0123456789'),
    ('Plomberie Martin SA',     'BE0987654321'),
    ('Chauffage Lambert',       'BE0555444333');

INSERT INTO address (street, number, postal_code, city) VALUES
    ('Rue de la Loi',     '16', '1000', 'Bruxelles'),
    ('Avenue Louise',     '120','1050', 'Ixelles');

INSERT INTO building (address_id, name, project_code, building_type) VALUES
    (1, 'Bâtiment Loi 16',  'PRJ-001', 'Bureaux'),
    (2, 'Résidence Louise', 'PRJ-002', 'Logements');

INSERT INTO wing (building_id, name) VALUES
    (1, 'Aile Nord'),
    (1, 'Aile Sud'),
    (2, 'Bloc A');

-- Tags avec scan_token fixe pour pouvoir tester les URLs facilement
INSERT INTO nfc (wing_id, name, scan_token) VALUES
    (1, 'Loi 16 — Aile Nord',  '11111111-1111-1111-1111-111111111111'),
    (2, 'Loi 16 — Aile Sud',   '22222222-2222-2222-2222-222222222222'),
    (3, 'Louise — Bloc A',     '33333333-3333-3333-3333-333333333333');
