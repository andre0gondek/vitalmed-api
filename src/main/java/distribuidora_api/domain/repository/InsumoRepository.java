package distribuidora_api.domain.repository;

import distribuidora_api.domain.entity.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    @Query("SELECT i FROM Insumo i WHERE i.estoqueAtual <= i.estoqueMinimo")
    List<Insumo> findInsumosEmAlerta();
}
