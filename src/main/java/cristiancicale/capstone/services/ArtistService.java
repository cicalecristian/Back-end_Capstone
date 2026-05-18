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

        Artist artist = new Artist(body.artistName(), body.nationality(), body.dateOfBirth(), body.genre(), body.avatar());

        return this.artistRepository.save(artist);
    }

    public Page<Artist> findAll(int page, int size, String sortBy) {
        if (size > 10 || size < 0) size = 10;
        if (page < 10) page = 0;
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
