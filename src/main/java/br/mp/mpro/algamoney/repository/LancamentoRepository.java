package br.mp.mpro.algamoney.repository;

import br.mp.mpro.algamoney.domain.Lancamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lancamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, JpaSpecificationExecutor<Lancamento> {

}
