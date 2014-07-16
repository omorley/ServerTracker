/**
 * 
 */
package ca.bcit.comp2613.servertracker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.bcit.comp2613.servertracker.model.Server;

/**
 * @author Owen
 *
 */
public interface ServerRepository extends CrudRepository<Server, String> {


}
