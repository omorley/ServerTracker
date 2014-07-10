/**
 * 
 */
package ca.bcit.comp2613.servertracker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.bcit.comp2613.servertracker.model.Cabinet;

/**
 * @author Owen
 *
 */
public interface CabinetRepository extends CrudRepository<Cabinet, String> {


}
