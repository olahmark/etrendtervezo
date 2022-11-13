package webapp.etrendtervezo.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webapp.etrendtervezo.dto.AlapanyagDTO;
import webapp.etrendtervezo.entity.Alapanyag;
import webapp.etrendtervezo.entity.Recept;
import webapp.etrendtervezo.repository.AlapanyagRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlapanyagServiceImpl implements AlapanyagService{

    private final AlapanyagRepository alapanyagRepository;
    private final ModelMapper modelMapper;

    public AlapanyagServiceImpl(AlapanyagRepository alapanyagRepository, ModelMapper modelMapper) {
        this.alapanyagRepository = alapanyagRepository;
        this.modelMapper = modelMapper;

        Alapanyag alapanyag1 = new Alapanyag();
        alapanyag1.setAlapanyagnev("protein");
        alapanyag1.setBazismennyiseg(100);
        alapanyag1.setSzenhidrat(0);
        alapanyag1.setFeherje(100);
        alapanyag1.setZsir(0);

        Alapanyag alapanyag2 = new Alapanyag();
        alapanyag2.setAlapanyagnev("ch");
        alapanyag2.setBazismennyiseg(100);
        alapanyag2.setSzenhidrat(100);
        alapanyag2.setFeherje(0);
        alapanyag2.setZsir(0);

        Alapanyag alapanyag3 = new Alapanyag();
        alapanyag3.setAlapanyagnev("fat");
        alapanyag3.setBazismennyiseg(100);
        alapanyag3.setSzenhidrat(0);
        alapanyag3.setFeherje(0);
        alapanyag3.setZsir(100);

        alapanyagRepository.save(alapanyag1);
        alapanyagRepository.save(alapanyag2);
        alapanyagRepository.save(alapanyag3);
    }


    @Override
    public List<AlapanyagDTO> findAll() {

        List<Alapanyag> alapanyagList = alapanyagRepository.findAll();

        return alapanyagList.stream()
                .map(alapanyag -> modelMapper.map(alapanyag, AlapanyagDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AlapanyagDTO findByAlapanyagnev(String alapanyagnev) {

        Alapanyag alapanyag = alapanyagRepository.findByAlapanyagnev(alapanyagnev);
        return modelMapper.map(alapanyag, AlapanyagDTO.class);
    }

    @Override
    public AlapanyagDTO create(AlapanyagDTO alapanyagDTO) {
        Alapanyag alapanyagToSave = modelMapper.map(alapanyagDTO, Alapanyag.class);
        Alapanyag savedAlapanyag = alapanyagRepository.save(alapanyagToSave);
        return modelMapper.map(savedAlapanyag, AlapanyagDTO.class);
    }



    }

