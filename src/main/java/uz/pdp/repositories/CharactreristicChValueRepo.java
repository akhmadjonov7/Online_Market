package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.CharacteristicsChValues;
import uz.pdp.projections.CharacteristicsValuesProjection;

import java.util.List;

public interface CharactreristicChValueRepo extends JpaRepository<CharacteristicsChValues, Integer> {
    @Query(nativeQuery = true, value = "select value from characteristics_ch_values join ch_values cv on cv.id = characteristics_ch_values.characteristic_value_id where characteristic_id = :characteristicId")
    List<String> getallChValuesByCharacteristicId(int characteristicId);

    @Query(nativeQuery = true, value = "select * from characteristics_ch_values where characteristic_id = :id and  characteristic_value_id = :id1")
    CharacteristicsChValues getCharacteristicId(int id, int id1);

    @Query(nativeQuery = true, value = "select c.name, cv.value \n" +
            "            from characteristics_ch_values \n" +
            "                     join characteristics c on c.id = characteristics_ch_values.characteristic_id \n" +
            "                     join ch_values cv on characteristics_ch_values.characteristic_value_id = cv.id \n" +
            "                     join products_characteristics_ch_values pccvl \n" +
            "                          on characteristics_ch_values.id = pccvl.characteristics_ch_values_id\n" +
            "            where pccvl.products_id = :productId")
    List<CharacteristicsValuesProjection> getAllCharacteristicsValuesByProductId(Integer productId);

    @Query(nativeQuery = true,
            value = "delete from characteristics_ch_values where characteristic_value_id = :id")
    void removeByChValueId(int id);
}
