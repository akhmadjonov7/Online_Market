package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dtos.ChValueDto;
import uz.pdp.entities.Characteristic;
import uz.pdp.entities.CharacteristicValue;
import uz.pdp.entities.CharacteristicsChValues;
import uz.pdp.repositories.ChValueRepo;
import uz.pdp.repositories.CharactreristicChValueRepo;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChValueService {
    private final ChValueRepo chValueRepo;
    private final CharactreristicChValueRepo charactreristicChValueRepo;
    public void save(ChValueDto chValueDto) {
        CharacteristicValue characteristicValue = new CharacteristicValue();
        characteristicValue.setValue(chValueDto.getValue());
        CharacteristicValue save = chValueRepo.save(characteristicValue);
        System.out.println(chValueDto.getCharacteristicId());
        System.out.println(save.getId());
        System.out.println(save);

        Characteristic characteristic = new Characteristic();
        characteristic.setId(chValueDto.getCharacteristicId());
        System.out.println(chValueDto.getCharacteristicId());
        CharacteristicsChValues save1 = charactreristicChValueRepo.save(new CharacteristicsChValues(characteristic, save));
        System.out.println(save1);
    }
    public boolean update(ChValueDto chValueDto){
        CharacteristicValue characteristicValue = new CharacteristicValue();
        characteristicValue.setValue(chValueDto.getValue());
        characteristicValue.setId(chValueDto.getId());
        try {
            chValueRepo.save(characteristicValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public Page<CharacteristicValue> getAllChValues(int size, int page){
        Page<CharacteristicValue> characteristicValuePage = chValueRepo.findAll(PageRequest.of(page-1,size));
        return characteristicValuePage;
    }
    

    public boolean delete(int id) {
        try {
            charactreristicChValueRepo.removeByChValueId(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CharacteristicValue getCharacteristicValueById(int id) {
        Optional<CharacteristicValue> characteristicValue = chValueRepo.findById(id);
        if (characteristicValue.isEmpty()) {
            return null;
        }
        return characteristicValue.get();
    }

    public List<String> getChValuesByCharacteristicId(int characteristicId){
        List<String> allByCharacteristic_id = charactreristicChValueRepo.getallChValuesByCharacteristicId(characteristicId);
        return allByCharacteristic_id;
    }
}
