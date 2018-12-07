package br.mp.mpro.algamoney.service.custom;

import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.domain.User;
//import br.mp.mpro.algamoney.mail.Mailer;
import br.mp.mpro.algamoney.repository.LancamentoRepository;
import br.mp.mpro.algamoney.repository.PessoaRepository;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.service.dto.custom.LancamentoCriteriaCustom;
import br.mp.mpro.algamoney.service.dto.custom.LancamentoEstatisticaPessoa;
import br.mp.mpro.algamoney.service.impl.LancamentoServiceImpl;
import br.mp.mpro.algamoney.service.mapper.LancamentoMapper;
import br.mp.mpro.algamoney.storage.S3;
import io.github.jhipster.service.filter.LocalDateFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Primary
public class LancamentoServiceCustom extends LancamentoServiceImpl {

    private final Logger log = LoggerFactory.getLogger(LancamentoServiceCustom.class);
    private final LancamentoRepository lancamentoRepository;
    private final LancamentoMapper lancamentoMapper;
    private final LancamentoQueryServiceCustom lancamentoQueryServiceCustom;
    private final PessoaRepository pessoaRepository;
    private final UserServiceCustom userServiceCustom;
    private final MailServiceCustom mailServiceCustom;
    private final S3 s3;

    public LancamentoServiceCustom(LancamentoRepository lancamentoRepository, LancamentoMapper lancamentoMapper, LancamentoQueryServiceCustom lancamentoQueryServiceCustom, PessoaRepository pessoaRepository, UserServiceCustom userServiceCustom, MailServiceCustom mailServiceCustom, S3 s3){
        super(lancamentoRepository, lancamentoMapper);
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoMapper = lancamentoMapper;
        this.lancamentoQueryServiceCustom = lancamentoQueryServiceCustom;
        this.pessoaRepository = pessoaRepository;
        this.userServiceCustom = userServiceCustom;
        this.mailServiceCustom = mailServiceCustom;
        this.s3 = s3;
    }

//    @Override
//    public LancamentoDTO save(LancamentoDTO lancamentoDTO) {
//        // lancamentoDTO.setObservacao(lancamentoDTO.getObservacao()+": " +LocalDateTime.now());
//        if(!StringUtils.isEmpty(lancamentoDTO.getId())){
//            LancamentoDTO lancamentoDTOSalvo = this.findOne(lancamentoDTO.getId()).get();
//            if (StringUtils.isEmpty(lancamentoDTO.getAnexo()) && StringUtils.hasText(lancamentoDTOSalvo.getAnexo())){
//                // s3.remover(lancamentoDTOSalvo.getAnexo());
//            } else if (StringUtils.hasText(lancamentoDTO.getAnexo()) && (StringUtils.hasText(lancamentoDTOSalvo.getAnexo()) && !lancamentoDTOSalvo.getAnexo().equals(lancamentoDTO.getAnexo()))){
//                // s3.substituir(lancamentoDTOSalvo.getAnexo(), lancamentoDTO.getAnexo());
//            }
//        } else if (StringUtils.hasText(lancamentoDTO.getAnexo())){
//            // s3.salvar(lancamentoDTO.getAnexo());
//        }
//
//        return super.save(lancamentoDTO);
//    }

    // Método executado ao ocorrer o evento ApplicationReadyEvent (evento disparado sempre após a aplicação ser iniciada)
//    @EventListener
//    protected void teste(ApplicationReadyEvent event){
//            System.out.println(">>>TESTE (APLICAÇÃO PRONTA PARA USO...)>>>>");
//    }

    // @Scheduled(fixedDelay = 1000 * 3) // executa a cada 3 segundos
//    @Scheduled(cron = "0 * * * * MON-FRI", zone = "America/Manaus") // PARAMETROS 1º: SEGUNDO, 2º: MINUTO, 3º: HORA, 4º: DIA, 5º MÊS, 6º: DIA DA SEMANA (MON-TUE-WED-THU-FRI-SAT-SUN) ou (0-6)
//    public void avisarSobreLancamentosVencidos(){
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> MÉTODO SENDO EXECUTADO ..................................");
//    }
    @Scheduled(cron = "0 0 * * * MON-FRI", zone = "America/Manaus") // PARAMETROS 1º: SEGUNDO, 2º: MINUTO, 3º: HORA, 4º: DIA, 5º MÊS, 6º: DIA DA SEMANA (MON-TUE-WED-THU-FRI-SAT-SUN) ou (0-6)
    public void avisarSobreLancamentosVencidos(){
        if(log.isDebugEnabled()){
            log.debug("Preparando envio de e-mails de aviso de lançamentos vencidos.");
        }
        LancamentoCriteriaCustom lancamentoCriteriaCustom = new LancamentoCriteriaCustom();
        lancamentoCriteriaCustom.setDataVencimento(new LocalDateFilter().setLessOrEqualThan(LocalDate.now()));
        //lancamentoCriteriaCustom.getDataPagamento().setSpecified(true);
        List<Lancamento> vencidos = lancamentoQueryServiceCustom.findByEntityCriteria(lancamentoCriteriaCustom);

//        List<Lancamento> vencidos = lancamentoMapper.toEntity(lancamentoQueryServiceCustom.findByCriteria(lancamentoCriteriaCustom));
        if(vencidos.isEmpty()){
            log.info("Sem lançamentos vencidos para aviso.");
            return;
        }
        log.info("Existem {} lançamentos vencidos...", vencidos.size());
        List<User> destinatarios = userServiceCustom.GetUsuarios().stream().filter(u->u.getEmail().equalsIgnoreCase("jonasnink@hotmail.com")).collect(Collectors.toList());

        if(destinatarios.isEmpty()){
            log.warn("Existem lançamentos vencidos, mas o sistema não encontrou destinatários.");
            return;
        }

        mailServiceCustom.enviarLamentosVencidos(vencidos, destinatarios);

        log.info("Envio de e-mail de aviso concluído.");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> MÉTODO EXECUTADO (EMAIL COM LANÇAMENTOS VENCIDOS SENDO ENVIADO...)");
    }

    public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception{
        LancamentoCriteriaCustom lancamentoCriteriaCustom = new LancamentoCriteriaCustom();

        lancamentoCriteriaCustom.setDataVencimentoInicio(new LocalDateFilter().setGreaterOrEqualThan(inicio));
        lancamentoCriteriaCustom.setDataVencimentoFim(new LocalDateFilter().setLessOrEqualThan(fim));

        List<LancamentoDTO> lancamentoDTOList = lancamentoQueryServiceCustom.findByCriteria(lancamentoCriteriaCustom);

        List<LancamentoEstatisticaPessoa> lancamentoEstatisticaPessoaList = lancamentoDTOList.stream()
            .collect(Collectors.groupingBy(
                LancamentoDTO::getPessoaId,
                Collectors.groupingBy(
                    LancamentoDTO::getTipoLancamento,
                    Collectors.reducing(
                        BigDecimal.ZERO,
                        LancamentoDTO::getValor,
                        BigDecimal::add))))
            .entrySet()
            .stream()
            .flatMap(q1 -> q1.getValue()
                .entrySet()
                .stream()
                .map(q2 -> new LancamentoEstatisticaPessoa(q2.getKey(), pessoaRepository.findById(q1.getKey()).get(), q2.getValue())))
            .collect(Collectors.toList());

        Map<String, Object> parametros = new HashMap<>();
        // parametros.put("DT_INICIO", Date.valueOf(inicio));
        // parametros.put("DT_FIM", Date.valueOf(fim));
        parametros.put("REPORT_LOCALE", new Locale("pt","BR"));

        //InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
        InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_equipe.jasper");

        //JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(lancamentoEstatisticaPessoaList));
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
