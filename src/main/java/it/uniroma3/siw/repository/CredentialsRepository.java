package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	@Query(nativeQuery = true, value = "select * from Credentials s where s.username = :username")
	public Optional<Credentials> findByUsername(String username);

//	@Query(nativeQuery = true, 
//			value="SELECT CASE WHEN EXISTS("
//			    + "SELECT 1"
//				+ "FROM credentials"
//			    + "WHERE username=:username)"
//				+ "THEN 'true'"
//			    + "ELSE 'false"
//				+ "END")
	public boolean existsByUsername(String username);
}
