package br.mp.mpro.algamoney.web.rest.custom;


import br.mp.mpro.algamoney.service.LancamentoQueryService;
import br.mp.mpro.algamoney.service.LancamentoService;
import br.mp.mpro.algamoney.service.custom.LancamentoQueryServiceCustom;
import br.mp.mpro.algamoney.service.custom.LancamentoServiceCustom;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.service.dto.custom.Anexo;
import br.mp.mpro.algamoney.storage.S3;
import br.mp.mpro.algamoney.web.rest.LancamentoResource;
import br.mp.mpro.algamoney.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.algamoney.web.rest.util.HeaderUtil;
import br.mp.mpro.algamoney.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/custom")
public class LancamentoResourceCustom extends LancamentoResource {
    private final Logger log = LoggerFactory.getLogger(LancamentoResource.class);

    private static final String ENTITY_NAME = "lancamento";

    private final LancamentoService lancamentoService;
    private final LancamentoServiceCustom lancamentoServiceCustom;
    private final LancamentoQueryService lancamentoQueryService;
    private final LancamentoQueryServiceCustom lancamentoQueryServiceCustom;

    private final S3 s3;

    public LancamentoResourceCustom(LancamentoService lancamentoService, LancamentoServiceCustom lancamentoServiceCustom, LancamentoQueryService lancamentoQueryService, LancamentoQueryServiceCustom lancamentoQueryServiceCustom, S3 s3) {
        super(lancamentoService, lancamentoQueryService);
        this.lancamentoService = lancamentoService;
        this.lancamentoServiceCustom = lancamentoServiceCustom;
        this.lancamentoQueryService = lancamentoQueryService;
        this.lancamentoQueryServiceCustom = lancamentoQueryServiceCustom;
        this.s3 = s3;
    }

    @Override
    @PostMapping("/lancamentos")
    @Timed
    public ResponseEntity<LancamentoDTO> createLancamento(@RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new lancamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoDTO result = lancamentoServiceCustom.save(lancamentoDTO);
        return ResponseEntity.created(new URI("/api/lancamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @Override
    @PutMapping("/lancamentos")
    @Timed
    public ResponseEntity<LancamentoDTO> updateLancamento(@RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoDTO result = lancamentoServiceCustom.save(lancamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lancamentoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/lancamentos/resumo")
    @Timed
    public ResponseEntity<List<LancamentoDTO>> getAllLancamentosResumo(Pageable pageable) {
        log.debug("REST request to get a page of Lancamentos");
        Page<LancamentoDTO> page = lancamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamentos/resumo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("lancamentos/download")
    public ResponseEntity<byte[]> download(@RequestParam String comprovante) throws  IOException {
        byte[] arquivo = Files.readAllBytes(Paths.get("D:/sistemas/algamoney/arquivos_upload/"+comprovante));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(arquivo);
    }

    @GetMapping("lancamentos/relatorios/por-pessoa")
    public ResponseEntity<byte[]> relatorioPorPessoa(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) throws Exception{
        byte[] relatorio = lancamentoServiceCustom.relatorioPorPessoa(inicio, fim);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
    }
    @PostMapping("lancamentos/anexo")
    public String uploadAnexo(@RequestParam MultipartFile file) throws IOException{
        String nome = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        OutputStream out = new FileOutputStream("D:/sistemas/algamoney/arquivos_upload/"+ nome);
        out.write(file.getBytes());
        out.close();
        return nome;
    }
//    @PostMapping("lancamentos/s3/anexo")
//    public Anexo uploadAnexoS3(@RequestParam MultipartFile file) throws IOException{
//        String nome = s3.salvarTemporariamente(file);
//        return new Anexo(nome, s3.configurarUrl(nome));
//    }
}
