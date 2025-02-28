package tonixcare.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tonixcare.entities.InsuranceEntity;
import tonixcare.entities.InsuredEntity;
import tonixcare.model.dto.UserDTO;
import tonixcare.entities.UserEntity;
import tonixcare.repository.InsuredRepository;
import tonixcare.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InsuredRepository insuredRepository; // Přidáme InsuredRepository

    @Autowired
    private PasswordEncoder passwordEncoder; // Injektujeme PasswordEncoder

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email); // Tato metoda bude volat metodu z UserRepository
    }


    public List<InsuranceEntity> getInsurancesByEmail(String email) {
        // Použijeme správně injektované insuredRepository
        InsuredEntity insured = insuredRepository.findByEmail(email).orElse(null);  // Najdeme pojištěnce podle emailu
        if (insured != null) {
            return insured.getInsurances();  // Vrátíme seznam pojištění pro tohoto pojištěnce
        }
        return null;  // Pokud pojištěnec neexistuje, vrátíme null
    }

    @Override
    public void create(UserDTO userDTO, boolean isAdmin) {
        // Vytvoření nové entity uživatele
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());  // Nastavení firstName
        userEntity.setLastName(userDTO.getLastName());    // Nastavení lastName
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());  // Nastavení telefonního čísla
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));  // Šifrování hesla
        userEntity.setAdmin(isAdmin);  // Určení, zda je uživatel administrátor

        userRepository.save(userEntity);  // Uložení uživatele do databáze
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();  // Vrátí všechny uživatele z databáze
    }

    @Override
    public void delete(long userId) {
        // Smazání uživatele podle ID
        userRepository.deleteById(userId);
    }

    @Override
    public UserEntity getUserById(long userId) {
        return userRepository.findById(userId).orElse(null);  // Vrátí uživatele nebo null, pokud nenalezen
    }

    @Override
    public void updateUser(long userId, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            throw new IllegalArgumentException("Uživatel nebyl nalezen");
        }

        // Aktualizace údajů
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setEmail(userDTO.getEmail());

        // Změna hesla pouze pokud je nové heslo zadáno
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));  // Šifrování nového hesla
        }

        userRepository.save(userEntity);  // Uložení změněného uživatele
    }



    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        // Načtení uživatele podle emailu (username)
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Uživatel s tímto emailem neexistuje");
        }
        return user;
    }
}
