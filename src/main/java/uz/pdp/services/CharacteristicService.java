package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.dtos.CharacteristicDto;
import uz.pdp.entities.Characteristic;
import uz.pdp.projections.CharacteristicProjection;
import uz.pdp.repositories.CharactreristicRepo;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CharacteristicService {
    private final CharactreristicRepo characteristicRepo;
    public Characteristic save(CharacteristicDto characteristicDto) {
        Characteristic characteristic = new Characteristic();
        Integer id = characteristicDto.getId();
        if (id!=null) {
            characteristic.setId(id);
        }
        characteristic.setName(characteristicDto.getName());
        Characteristic save = characteristicRepo.save(characteristic);
        return save;
    }

    public Page<CharacteristicProjection> getAllCharactersitic(int size, int page){
        Page<CharacteristicProjection> characteristicPage = characteristicRepo.getAllCharacteristics(PageRequest.of(page-1, size));
        return characteristicPage;
    }

    public boolean delete(int id) {
        try {
            characteristicRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Characteristic getCharacteristicById(int id) {
        Optional<Characteristic> characteristicById = characteristicRepo.findById(id);
        if (characteristicById.isEmpty()) {
            return null;
        }
        return characteristicById.get();
    }

    public List<CharacteristicProjection> getAllCharactersiticForChoose() {
        return characteristicRepo.getAllCharacteristicsForChoose();
    }
}
