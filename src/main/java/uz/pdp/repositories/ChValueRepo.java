package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.dtos.ChValueDto;
import uz.pdp.entities.Characteristic;
import uz.pdp.entities.CharacteristicValue;

import java.util.List;

public interface ChValueRepo extends JpaRepository<CharacteristicValue,Integer> {
    List<CharacteristicValue> findAllById(int id);
}
