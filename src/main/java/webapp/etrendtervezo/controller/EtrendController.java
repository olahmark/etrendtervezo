package webapp.etrendtervezo.controller;

import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp.etrendtervezo.businesslogic.EtkezesSolution;
import webapp.etrendtervezo.businesslogic.EtkezesTervezet;
import webapp.etrendtervezo.businesslogic.KaloriaSzukseglet;
import webapp.etrendtervezo.dto.AlapanyagDTO;
import webapp.etrendtervezo.dto.FelhasznaloiAdatokDTO;
import webapp.etrendtervezo.dto.ReceptDTO;
import webapp.etrendtervezo.service.AlapanyagService;
import webapp.etrendtervezo.service.ReceptService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class EtrendController {

    private final ReceptService receptService;
    private final AlapanyagService alapanyagService;
    private SolverManager<EtkezesSolution, UUID> solverManager;


    public EtrendController(ReceptService receptService, AlapanyagService alapanyagService, SolverManager<EtkezesSolution, UUID> solverManager) {
        this.receptService = receptService;
        this.alapanyagService = alapanyagService;
        this.solverManager = solverManager;
    }

    @RequestMapping(path = "/receptek", method = RequestMethod.GET)
    public List<ReceptDTO> findAll(){

    return receptService.findAll();
    }

    @RequestMapping(path = "/receptek", method = RequestMethod.POST)
    public ResponseEntity<ReceptDTO> create(ReceptDTO receptDTO){
        ReceptDTO savedRecept = receptService.create(receptDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRecept);
    }

    @RequestMapping(path = "/alapanyagok", method = RequestMethod.POST)
    public ResponseEntity<AlapanyagDTO> create(AlapanyagDTO alapanyagDTO){

        AlapanyagDTO savedAlapanyag = alapanyagService.create(alapanyagDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedAlapanyag);
    }

    @RequestMapping(path = "/alapanyagok", method = RequestMethod.GET)
    public AlapanyagDTO findByAlapanyagnev(String alapanyagnev){

        return alapanyagService.findByAlapanyagnev(alapanyagnev);
    }


    @RequestMapping(path = "/etrend", method = RequestMethod.GET)
    public double kaloriakalkulalas (FelhasznaloiAdatokDTO felhasznaloiAdatokDTO, String receptnev) {
       KaloriaSzukseglet kaloriaSzukseglet = new KaloriaSzukseglet(felhasznaloiAdatokDTO);

       EtkezesTervezet etkezesTervezet = new EtkezesTervezet(alapanyagService, receptService.findByReceptnev(receptnev), kaloriaSzukseglet);
       etkezesTervezet.nyomtat();
       return kaloriaSzukseglet.kalkulalas();

    }

    @PostMapping("/solve")
    public EtkezesSolution solve(FelhasznaloiAdatokDTO felhasznaloiAdatokDTO, String receptnev) {
        KaloriaSzukseglet kaloriaSzukseglet = new KaloriaSzukseglet(felhasznaloiAdatokDTO);
        EtkezesTervezet etkezesTervezet = new EtkezesTervezet(alapanyagService, receptService.findByReceptnev(receptnev), kaloriaSzukseglet);
        EtkezesSolution problem = new EtkezesSolution(etkezesTervezet);
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<EtkezesSolution, UUID> solverJob = solverManager.solve(problemId, problem);
        EtkezesSolution solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }

  /*  @RequestMapping(path = "/etkezesbetolt", method = RequestMethod.GET)

    public String etkezesbetoltes (String receptnev){

        int kaloriacel = 1700;
        Etkezes etkezes = new Etkezes (alapanyagService, receptService.findByReceptnev(receptnev), kaloriacel);
        etkezes.nyomtat();
        return etkezes.getReceptnev();
    } */
}

