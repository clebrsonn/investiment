package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.models.ImportedXSLX;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportRepository extends JpaRepository<ImportedXSLX, String> {
}
