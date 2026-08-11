-- La société d'une intervention est une donnée historique : elle ne doit plus suivre
-- le technicien quand il change d'employeur.

alter table presence add column business_id uuid;

-- Reprise de l'existant : l'employeur actuel du technicien.
update presence
set business_id = (select t.business_id from technician t where t.id = presence.technician_id);

alter table presence alter column business_id set not null;

alter table presence add constraint fk_presence_business
    foreign key (business_id) references business (id);

create index ix_presence_business on presence (business_id);