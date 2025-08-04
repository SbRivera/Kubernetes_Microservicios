package authservice.controller;



import authservice.model.Role;
import authservice.repository.RoleRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepo;

    public RoleController(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public List<Role> list() {
        return roleRepo.findAll();
    }

    @PostMapping
    public Role create(@Valid @RequestBody Role role) {
        return roleRepo.save(role);
    }
}