package sit.int221.mydb.services;
import java.nio.file.Path;
import java.time.Instant;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public void deleteFileAll();

    public Stream<Path> loadAll();

//    public boolean delete(String filename);

    public void deleteFile(String filename);
}
