package cristiancicale.capstone.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristiancicale.capstone.config.EmailSender;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.enums.RoleUser;
import cristiancicale.capstone.exceptions.BadRequestException;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.payloads.UserDTO;
import cristiancicale.capstone.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    public final EmailSender emailSender;
    private final UserRepository userRepository;
    private final Cloudinary cloudinaryUploader;
    private final PasswordEncoder bcrypt;

    public UserService(UserRepository userRepository, Cloudinary cloudinaryUploader, PasswordEncoder bcrypt, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.cloudinaryUploader = cloudinaryUploader;
        this.bcrypt = bcrypt;
        this.emailSender = emailSender;
    }

    public User save(UserDTO body) {

        if (userRepository.existsByEmail(body.email())) {
            throw new BadRequestException("Email già utilizzata");
        }

        if (userRepository.existsByUsername(body.username())) {
            throw new BadRequestException("Username già utilizzato");
        }

        User user = new User(body.username().toLowerCase().trim(), body.email().toLowerCase().trim(), bcrypt.encode(body.password()), body.name(), body.surname(), body.dateOfBirth());

        this.emailSender.sendRegistrationEmail(user);

        return userRepository.save(user);
    }

    public Page<User> findAll(int page, int size, String sortBy) {
        if (size > 10 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public User findByIdAndUpdate(UUID id, UserDTO body) {

        User found = this.findById(id);

        String newUsername = body.username().toLowerCase().trim();
        String newEmail = body.email().toLowerCase().trim();

        if (!found.getUsername().equals(body.username())) {

            if (this.userRepository.existsByUsername(body.username()))
                throw new BadRequestException("L'username " + body.username() + "è gia in uso");
        }

        if (!found.getEmail().equals(body.email())) {

            if (this.userRepository.existsByEmail(body.email()))
                throw new BadRequestException("L'email " + body.email() + "è gia in uso");
        }

        found.setUsername(newUsername);
        found.setEmail(newEmail);
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setDateOfBirth(body.dateOfBirth());

        if (body.password() != null && !body.password().isBlank()) {
            String nuovaPassword = this.bcrypt.encode(body.password());
            found.setPassword(nuovaPassword);
        }

        if (body.avatar() != null && !body.avatar().isBlank()) {
            found.setAvatar(body.avatar());
        }

        User updateUtente = this.userRepository.save(found);

        log.info("L'utente " + updateUtente.getId() + "è stato aggiornato correttamente");

        return updateUtente;
    }

    public void findByIdAndDelete(UUID id) {
        User found = findById(id);
        userRepository.delete(found);
    }

    public User avatarUpload(MultipartFile file, UUID utenteId) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File non valido o vuoto");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            throw new BadRequestException("File troppo grande (max 2MB)");
        }

        User found = this.findById(utenteId);

        try {
            Map result = cloudinaryUploader.uploader()
                    .upload(file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", "avatars",
                                    "resource_type", "image"
                            ));

            log.info("Cloudinary response: {}", result);

            String url = (String) result.get("secure_url");

            if (url == null) {
                throw new RuntimeException("Upload fallito: secure_url nullo");
            }

            found.setAvatar(url);

            return userRepository.save(found);

        } catch (IOException e) {
            throw new RuntimeException("Errore upload Cloudinary", e);
        }
    }

    public User changeRole(UUID userId, RoleUser newRole) {

        User found = findById(userId);

        if (found.getRole().equals(newRole)) {
            throw new BadRequestException(
                    "L'utente possiede già il ruolo " + newRole
            );
        }

        found.setRole(newRole);

        return userRepository.save(found);
    }
}
