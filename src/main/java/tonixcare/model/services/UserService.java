package tonixcare.model.services;

import tonixcare.entities.InsuranceEntity;
import tonixcare.model.dto.UserDTO;
import tonixcare.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    // Metoda pro vytvoření nového uživatele
    void create(UserDTO user, boolean isAdmin);

    // Metoda pro získání seznamu všech uživatelů
    List<UserEntity> getAllUsers();

    // Metoda pro smazání uživatele podle jeho ID
    void delete(long userId);

    // Metoda pro získání uživatele podle jeho ID
    UserEntity getUserById(long userId);

    // Metoda pro aktualizaci údajů uživatele
    void updateUser(long userId, UserDTO userDTO);

    UserEntity findByEmail(String email);

    List<InsuranceEntity> getInsurancesByEmail(String email);
}
