package org.cours.springdatarest.modele;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProprietaireRepo extends CrudRepository<Proprietaire, Long> {
}
