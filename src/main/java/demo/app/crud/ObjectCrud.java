package demo.app.crud;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.app.entities.ObjectEntity;

public interface ObjectCrud extends JpaRepository<ObjectEntity, String> {

	public List<ObjectEntity> findAllByActive(@Param("active") boolean active, Pageable pageable);

	public List<ObjectEntity> findAllByType(@Param("type") String type, Pageable pageable);

	public List<ObjectEntity> findAllByTypeAndActive(@Param("type") String type, @Param("active") boolean active,
			Pageable pageable);

	public List<ObjectEntity> findAllByAlias(@Param("alias") String alias, Pageable pageable);

	public List<ObjectEntity> findAllByAliasAndActive(@Param("alias") String alias, @Param("active") boolean active,
			Pageable pageable);

	public List<ObjectEntity> findAllByAliasLikeIgnoreCase(@Param("pattern") String pattern, Pageable pageable);

	public List<ObjectEntity> findAllByAliasLikeIgnoreCaseAndActive(@Param("pattern") String pattern,
			@Param("active") boolean active, Pageable pageable);

	 @Query(value = "SELECT * FROM objects o WHERE " +
             "(:units * acos(cos(radians(:lat)) * cos(radians(o.latitude)) * cos(radians(o.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(o.latitude)))) <= :distance "
             ,nativeQuery = true)
	public List<ObjectEntity> findAllByLocationWithin(
			@Param("lat") double lat, 
			@Param("lng") double lng, 
			@Param("distance") double distance, 
			@Param("units") double units,  
			Pageable pageable);

	 @Query(value = "SELECT * FROM objects o WHERE " +
			 "(:units * acos(cos(radians(:lat)) * cos(radians(o.latitude)) * cos(radians(o.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(o.latitude)))) <= :distance "+
			 "AND o.active = :active",nativeQuery = true)
	public List<ObjectEntity> findAllByLocationWithinAndActive(
			@Param("lat") double lat, 
			@Param("lng") double lng, 
			@Param("distance") double distance, 
			@Param("units") double units, 
			@Param("active") boolean active, 
			Pageable pageable);
	 
//	 finds all entities with a latitude and longitude within a specified rectangular area
//	 public List<ObjectEntity> findAllByLatitudeBetweenAndLongitudeBetween(
//	            double startLat, double endLat, 
//	            double startLng, double endLng, 
//	            Pageable pageable);

}
