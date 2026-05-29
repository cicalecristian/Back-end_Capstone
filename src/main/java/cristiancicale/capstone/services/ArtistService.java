package cristiancicale.capstone.services;

import cristiancicale.capstone.entities.Artist;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.payloads.ArtistDTO;
import cristiancicale.capstone.repositories.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ArtistService {

    private final ArtistRepository artistRepository;


    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist save(ArtistDTO body) {

        String avatar = body.avatar();

        if (avatar == null || avatar.isBlank()) {
            avatar = "https://images.unsplash.com/photo-1484876065684-b683cf17d276?fm=jpg&q=60&w=3000&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8bXVzaWMlMjBhcnRpc3R8ZW58MHx8MHx8fDA%3D";
        }

        Artist artist = new Artist(body.artistName(), body.nationality(), body.dateOfBirth(), body.genre(), avatar);

        return this.artistRepository.save(artist);
    }

    public Page<Artist> findAll(int page, int size, String sortBy) {
        if (size <= 0) size = 15;
        if (size > 1000) size = 1000;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.artistRepository.findAll(pageable);
    }

    public Artist findById(UUID id) {
        return artistRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Artist findByIdAndUpdate(UUID id, ArtistDTO body) {

        Artist found = findById(id);

        found.setArtistName(body.artistName());
        found.setNationality(body.nationality());
        found.setDateOfBirth(body.dateOfBirth());
        found.setGenre(body.genre());
        found.setAvatar(body.avatar());

        return artistRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Artist found = findById(id);
        artistRepository.delete(found);
    }
}
