package br.mp.mpro.algamoney.repository.listener;

import br.mp.mpro.algamoney.AlgamoneyApp;
import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.storage.S3;
import org.springframework.util.StringUtils;

import javax.persistence.PostLoad;

public class LancamentoAnexoListener {
    @PostLoad
    public void postLoad(Lancamento lancamento){
//        if (StringUtils.hasText(lancamento.getAnexo())){
//            S3 s3 = AlgamoneyApp.getBean(S3.class);
//            lancamento.setUrlAnexo(s3.configurarUrl(lancamento.getAnexo()));
//        }
//        if (StringUtils.hasText(lancamento.getAnexo())){
//            lancamento.setUrlAnexo("D:\\sistemas\\algamoney\\arquivos_upload"+lancamento.getAnexo());
//        }
    }

}
