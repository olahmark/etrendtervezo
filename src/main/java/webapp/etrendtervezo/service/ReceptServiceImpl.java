package webapp.etrendtervezo.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webapp.etrendtervezo.dto.AlapanyagDTO;
import webapp.etrendtervezo.dto.ReceptDTO;
import webapp.etrendtervezo.entity.Alapanyag;
import webapp.etrendtervezo.entity.Recept;
import webapp.etrendtervezo.repository.ReceptRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceptServiceImpl implements ReceptService{

    private final ReceptRepository receptRepository;
    private final ModelMapper modelMapper;

    public ReceptServiceImpl(ReceptRepository receptRepository, ModelMapper modelMapper) {
        this.receptRepository = receptRepository;
        this.modelMapper = modelMapper;

        Recept recept1 = new Recept();
        recept1.setReceptnev("teszt");
        recept1.setAlapanyag("protein");
        recept1.setArany(55);
        recept1.setKategoria("reggeli");

        Recept recept2 = new Recept();
        recept2.setReceptnev("teszt");
        recept2.setAlapanyag("ch");
        recept2.setArany(35);
        recept2.setKategoria("reggeli");

        Recept recept3 = new Recept();
        recept3.setReceptnev("teszt");
        recept3.setAlapanyag("fat");
        recept3.setArany(20);
        recept3.setKategoria("reggeli");

        receptRepository.save(recept1);
        receptRepository.save(recept2);
        receptRepository.save(recept3);
    }

    @Override
    public List<ReceptDTO> findAll() {

        List<Recept> receptList = receptRepository.findAll();

        return receptList.stream()
                .map(recept -> modelMapper.map(recept, ReceptDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReceptDTO create(ReceptDTO receptDTO) {

        Recept receptToSave = modelMapper.map(receptDTO, Recept.class);
        receptToSave.setId(null);
        Recept savedRecept = receptRepository.save(receptToSave);
        return modelMapper.map(savedRecept, ReceptDTO.class);
    }

    @Override
    public List<ReceptDTO> findByReceptnev(String receptnev) {

        List<Recept> recept = receptRepository.findByReceptnev(receptnev);

        return recept.stream()
                .map(receptlist -> modelMapper.map(receptlist, ReceptDTO.class))
                .collect(Collectors.toList());
    }
}
