-- ============================================================
-- NFCTag — schéma initial
-- Calqué sur le modèle entité/relation fourni.
-- ============================================================

-- Société (entreprise externe à laquelle appartient un ouvrier)
CREATE TABLE business (
    business_id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    bce          VARCHAR(32),                      -- n° d'entreprise (BCE)
    created      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated      TIMESTAMPTZ,
    archived     TIMESTAMPTZ
);

-- Ouvrier / technicien externe (appartient à UNE société)
CREATE TABLE worker (
    worker_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    business_id  BIGINT NOT NULL REFERENCES business(business_id),
    lastname     VARCHAR(255),
    firstname    VARCHAR(255),
    mobile       VARCHAR(32) NOT NULL,             -- clé d'identité durable (repli)
    created      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated      TIMESTAMPTZ,
    archived     TIMESTAMPTZ,
    CONSTRAINT uq_worker_mobile UNIQUE (mobile)
);

-- Adresse
CREATE TABLE address (
    address_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    street       VARCHAR(255),
    number       VARCHAR(32),
    box          VARCHAR(32),
    postal_code  VARCHAR(32),
    city         VARCHAR(255),
    created      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated      TIMESTAMPTZ
);

-- Bâtiment (domicilié à une adresse)
CREATE TABLE building (
    building_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    address_id    BIGINT REFERENCES address(address_id),
    name          VARCHAR(255) NOT NULL,
    project_code  VARCHAR(64),
    building_type VARCHAR(64),
    created       TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated       TIMESTAMPTZ,
    archived      TIMESTAMPTZ
);

-- Aile (composante d'un bâtiment) — c'est l'emplacement d'un tag
CREATE TABLE wing (
    wing_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    building_id BIGINT NOT NULL REFERENCES building(building_id),
    name        VARCHAR(255) NOT NULL,
    street      VARCHAR(255),
    number      VARCHAR(32),
    box         VARCHAR(32),
    created     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated     TIMESTAMPTZ,
    archived    TIMESTAMPTZ
);

-- Tag NFC (1 tag par aile). scan_token = identifiant inscrit dans l'URL du tag.
CREATE TABLE nfc (
    nfc_id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    wing_id     BIGINT REFERENCES wing(wing_id),
    name        VARCHAR(255) NOT NULL,             -- nom du bâtiment/aile inscrit sur le tag
    scan_token  UUID NOT NULL DEFAULT gen_random_uuid(),
    created     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated     TIMESTAMPTZ,
    archived    TIMESTAMPTZ,
    CONSTRAINT uq_nfc_scan_token UNIQUE (scan_token),
    CONSTRAINT uq_nfc_wing UNIQUE (wing_id)         -- 1 tag par aile
);

-- Présence = une intervention (arrivée/départ) d'un ouvrier sur un tag
CREATE TABLE presence (
    presence_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_id   BIGINT NOT NULL REFERENCES worker(worker_id),
    nfc_id      BIGINT NOT NULL REFERENCES nfc(nfc_id),
    arrived_at  TIMESTAMPTZ NOT NULL,
    departed_at TIMESTAMPTZ,
    estimated   BOOLEAN NOT NULL DEFAULT FALSE,     -- départ estimé (pas de scan de sortie)
    created     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated     TIMESTAMPTZ
);

-- Appareil reconnu (jeton déposé dans le navigateur du technicien)
CREATE TABLE worker_device (
    worker_device_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_id        BIGINT NOT NULL REFERENCES worker(worker_id),
    token            UUID NOT NULL DEFAULT gen_random_uuid(),
    created          TIMESTAMPTZ NOT NULL DEFAULT now(),
    last_seen        TIMESTAMPTZ,
    CONSTRAINT uq_worker_device_token UNIQUE (token)
);

-- Index utiles
CREATE INDEX idx_worker_business        ON worker(business_id);
CREATE INDEX idx_wing_building          ON wing(building_id);
CREATE INDEX idx_building_address       ON building(address_id);
CREATE INDEX idx_presence_worker        ON presence(worker_id);
CREATE INDEX idx_presence_nfc           ON presence(nfc_id);
CREATE INDEX idx_presence_open          ON presence(worker_id, nfc_id) WHERE departed_at IS NULL;
CREATE INDEX idx_worker_device_worker   ON worker_device(worker_id);
