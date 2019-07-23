package com.codeoftheweb.salvo;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo, Set> {

}
