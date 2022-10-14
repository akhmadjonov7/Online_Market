package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Characteristic;
import uz.pdp.projections.CharacteristicProjection;

import java.util.List;

public interface CharactreristicRepo extends JpaRepository<Characteristic,Integer> {
    @Query(value = "select id, name from characteristics",
    countQuery = "select count (*) from characteristics",
    nativeQuery = true)
    Page<CharacteristicProjection> getAllCharacteristics(PageRequest of);

    @Query(value = "select id,name from characteristics",nativeQuery = true)
    List<CharacteristicProjection> getAllCharacteristicsForChoose();

}
