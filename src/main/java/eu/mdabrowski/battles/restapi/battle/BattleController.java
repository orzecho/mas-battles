package eu.mdabrowski.battles.restapi.battle;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.mdabrowski.battles.domain.Battle;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.persistance.BattleRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.restapi.battle.BattleDTO;
import eu.mdabrowski.battles.restapi.battle.BattleMapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/battles")
public class BattleController {

    private final BattleRepository battleRepository;
    private final BattleMapper battleMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseListWrapper<BattleDTO> getBattles(Principal principal) {
        List<Battle> battles = battleRepository.findAll();
        List<BattleDTO> battleDTOs = battles.stream()
                .map(battleMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(battleDTOs, Battle.LABEL_PLURAL);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseWrapper<BattleDTO> getBattle(@PathVariable Long id) {
        return new ResponseWrapper<>(battleRepository
                .findById(id)
                .map(battleMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), Battle.LABEL_SINGULAR);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<BattleDTO> createBattle(@Valid @RequestBody Map<String, BattleDTO> battleDTO) {
        Battle battle = battleMapper.fromDTO(battleDTO.get(Battle.LABEL_SINGULAR));
        Battle savedBattle = battleRepository.save(battle);
        BattleDTO savedBattleDTO = battleMapper.toDTO(savedBattle);
        return new ResponseWrapper<>(savedBattleDTO, Battle.LABEL_SINGULAR);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    @PreAuthorize("isAuthenticated()")
    public ResponseWrapper<BattleDTO> updateBattle(@PathVariable Long id, @Valid @RequestBody Map<String, BattleDTO>
            battleDTO, Principal principal) {
        Battle oldBattle = battleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Battle updatedBattle = battleRepository.save(battleMapper.update(battleDTO.get(Battle.LABEL_SINGULAR), oldBattle));
        return new ResponseWrapper<>(battleMapper.toDTO(updatedBattle), Battle.LABEL_SINGULAR);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseEntity deleteBattle(@PathVariable Long id) {
        battleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
