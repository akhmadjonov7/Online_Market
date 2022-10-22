package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;;
import uz.pdp.entities.CharacteristicValue;

public interface ChValueRepo extends JpaRepository<CharacteristicValue,Integer> {
}
