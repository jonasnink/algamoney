package br.mp.mpro.algamoney.web.rest;

import br.mp.mpro.algamoney.AlgamoneyApp;

import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.domain.Categoria;
import br.mp.mpro.algamoney.domain.Pessoa;
import br.mp.mpro.algamoney.repository.LancamentoRepository;
import br.mp.mpro.algamoney.service.LancamentoService;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.service.mapper.LancamentoMapper;
import br.mp.mpro.algamoney.web.rest.errors.ExceptionTranslator;
import br.mp.mpro.algamoney.service.dto.LancamentoCriteria;
import br.mp.mpro.algamoney.service.LancamentoQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static br.mp.mpro.algamoney.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.mp.mpro.algamoney.domain.enumeration.TipoLancamento;
/**
 * Test class for the LancamentoResource REST controller.
 *
 * @see LancamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgamoneyApp.class)
public class LancamentoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_PAGAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PAGAMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final TipoLancamento DEFAULT_TIPO_LANCAMENTO = TipoLancamento.RECEITA;
    private static final TipoLancamento UPDATED_TIPO_LANCAMENTO = TipoLancamento.DESPESA;

    @Autowired
    private LancamentoRepository lancamentoRepository;


    @Autowired
    private LancamentoMapper lancamentoMapper;
    

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private LancamentoQueryService lancamentoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLancamentoMockMvc;

    private Lancamento lancamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LancamentoResource lancamentoResource = new LancamentoResource(lancamentoService, lancamentoQueryService);
        this.restLancamentoMockMvc = MockMvcBuilders.standaloneSetup(lancamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lancamento createEntity(EntityManager em) {
        Lancamento lancamento = new Lancamento()
            .descricao(DEFAULT_DESCRICAO)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .valor(DEFAULT_VALOR)
            .observacao(DEFAULT_OBSERVACAO)
            .tipoLancamento(DEFAULT_TIPO_LANCAMENTO);
        return lancamento;
    }

    @Before
    public void initTest() {
        lancamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createLancamento() throws Exception {
        int databaseSizeBeforeCreate = lancamentoRepository.findAll().size();

        // Create the Lancamento
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);
        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Lancamento testLancamento = lancamentoList.get(lancamentoList.size() - 1);
        assertThat(testLancamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLancamento.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testLancamento.getDataPagamento()).isEqualTo(DEFAULT_DATA_PAGAMENTO);
        assertThat(testLancamento.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testLancamento.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testLancamento.getTipoLancamento()).isEqualTo(DEFAULT_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void createLancamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lancamentoRepository.findAll().size();

        // Create the Lancamento with an existing ID
        lancamento.setId(1L);
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLancamentos() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList
        restLancamentoMockMvc.perform(get("/api/lancamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].tipoLancamento").value(hasItem(DEFAULT_TIPO_LANCAMENTO.toString())));
    }
    

    @Test
    @Transactional
    public void getLancamento() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get the lancamento
        restLancamentoMockMvc.perform(get("/api/lancamentos/{id}", lancamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lancamento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.tipoLancamento").value(DEFAULT_TIPO_LANCAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getAllLancamentosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where descricao equals to DEFAULT_DESCRICAO
        defaultLancamentoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the lancamentoList where descricao equals to UPDATED_DESCRICAO
        defaultLancamentoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultLancamentoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the lancamentoList where descricao equals to UPDATED_DESCRICAO
        defaultLancamentoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where descricao is not null
        defaultLancamentoShouldBeFound("descricao.specified=true");

        // Get all the lancamentoList where descricao is null
        defaultLancamentoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataVencimento equals to DEFAULT_DATA_VENCIMENTO
        defaultLancamentoShouldBeFound("dataVencimento.equals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the lancamentoList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultLancamentoShouldNotBeFound("dataVencimento.equals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataVencimento in DEFAULT_DATA_VENCIMENTO or UPDATED_DATA_VENCIMENTO
        defaultLancamentoShouldBeFound("dataVencimento.in=" + DEFAULT_DATA_VENCIMENTO + "," + UPDATED_DATA_VENCIMENTO);

        // Get all the lancamentoList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultLancamentoShouldNotBeFound("dataVencimento.in=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataVencimento is not null
        defaultLancamentoShouldBeFound("dataVencimento.specified=true");

        // Get all the lancamentoList where dataVencimento is null
        defaultLancamentoShouldNotBeFound("dataVencimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataVencimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataVencimento greater than or equals to DEFAULT_DATA_VENCIMENTO
        defaultLancamentoShouldBeFound("dataVencimento.greaterOrEqualThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the lancamentoList where dataVencimento greater than or equals to UPDATED_DATA_VENCIMENTO
        defaultLancamentoShouldNotBeFound("dataVencimento.greaterOrEqualThan=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataVencimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataVencimento less than or equals to DEFAULT_DATA_VENCIMENTO
        defaultLancamentoShouldNotBeFound("dataVencimento.lessThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the lancamentoList where dataVencimento less than or equals to UPDATED_DATA_VENCIMENTO
        defaultLancamentoShouldBeFound("dataVencimento.lessThan=" + UPDATED_DATA_VENCIMENTO);
    }


    @Test
    @Transactional
    public void getAllLancamentosByDataPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataPagamento equals to DEFAULT_DATA_PAGAMENTO
        defaultLancamentoShouldBeFound("dataPagamento.equals=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the lancamentoList where dataPagamento equals to UPDATED_DATA_PAGAMENTO
        defaultLancamentoShouldNotBeFound("dataPagamento.equals=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataPagamento in DEFAULT_DATA_PAGAMENTO or UPDATED_DATA_PAGAMENTO
        defaultLancamentoShouldBeFound("dataPagamento.in=" + DEFAULT_DATA_PAGAMENTO + "," + UPDATED_DATA_PAGAMENTO);

        // Get all the lancamentoList where dataPagamento equals to UPDATED_DATA_PAGAMENTO
        defaultLancamentoShouldNotBeFound("dataPagamento.in=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataPagamento is not null
        defaultLancamentoShouldBeFound("dataPagamento.specified=true");

        // Get all the lancamentoList where dataPagamento is null
        defaultLancamentoShouldNotBeFound("dataPagamento.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataPagamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataPagamento greater than or equals to DEFAULT_DATA_PAGAMENTO
        defaultLancamentoShouldBeFound("dataPagamento.greaterOrEqualThan=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the lancamentoList where dataPagamento greater than or equals to UPDATED_DATA_PAGAMENTO
        defaultLancamentoShouldNotBeFound("dataPagamento.greaterOrEqualThan=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataPagamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where dataPagamento less than or equals to DEFAULT_DATA_PAGAMENTO
        defaultLancamentoShouldNotBeFound("dataPagamento.lessThan=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the lancamentoList where dataPagamento less than or equals to UPDATED_DATA_PAGAMENTO
        defaultLancamentoShouldBeFound("dataPagamento.lessThan=" + UPDATED_DATA_PAGAMENTO);
    }


    @Test
    @Transactional
    public void getAllLancamentosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where valor equals to DEFAULT_VALOR
        defaultLancamentoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the lancamentoList where valor equals to UPDATED_VALOR
        defaultLancamentoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultLancamentoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the lancamentoList where valor equals to UPDATED_VALOR
        defaultLancamentoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where valor is not null
        defaultLancamentoShouldBeFound("valor.specified=true");

        // Get all the lancamentoList where valor is null
        defaultLancamentoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where observacao equals to DEFAULT_OBSERVACAO
        defaultLancamentoShouldBeFound("observacao.equals=" + DEFAULT_OBSERVACAO);

        // Get all the lancamentoList where observacao equals to UPDATED_OBSERVACAO
        defaultLancamentoShouldNotBeFound("observacao.equals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where observacao in DEFAULT_OBSERVACAO or UPDATED_OBSERVACAO
        defaultLancamentoShouldBeFound("observacao.in=" + DEFAULT_OBSERVACAO + "," + UPDATED_OBSERVACAO);

        // Get all the lancamentoList where observacao equals to UPDATED_OBSERVACAO
        defaultLancamentoShouldNotBeFound("observacao.in=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where observacao is not null
        defaultLancamentoShouldBeFound("observacao.specified=true");

        // Get all the lancamentoList where observacao is null
        defaultLancamentoShouldNotBeFound("observacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByTipoLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where tipoLancamento equals to DEFAULT_TIPO_LANCAMENTO
        defaultLancamentoShouldBeFound("tipoLancamento.equals=" + DEFAULT_TIPO_LANCAMENTO);

        // Get all the lancamentoList where tipoLancamento equals to UPDATED_TIPO_LANCAMENTO
        defaultLancamentoShouldNotBeFound("tipoLancamento.equals=" + UPDATED_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByTipoLancamentoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where tipoLancamento in DEFAULT_TIPO_LANCAMENTO or UPDATED_TIPO_LANCAMENTO
        defaultLancamentoShouldBeFound("tipoLancamento.in=" + DEFAULT_TIPO_LANCAMENTO + "," + UPDATED_TIPO_LANCAMENTO);

        // Get all the lancamentoList where tipoLancamento equals to UPDATED_TIPO_LANCAMENTO
        defaultLancamentoShouldNotBeFound("tipoLancamento.in=" + UPDATED_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByTipoLancamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where tipoLancamento is not null
        defaultLancamentoShouldBeFound("tipoLancamento.specified=true");

        // Get all the lancamentoList where tipoLancamento is null
        defaultLancamentoShouldNotBeFound("tipoLancamento.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        Categoria categoria = CategoriaResourceIntTest.createEntity(em);
        em.persist(categoria);
        em.flush();
        lancamento.setCategoria(categoria);
        lancamentoRepository.saveAndFlush(lancamento);
        Long categoriaId = categoria.getId();

        // Get all the lancamentoList where categoria equals to categoriaId
        defaultLancamentoShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the lancamentoList where categoria equals to categoriaId + 1
        defaultLancamentoShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllLancamentosByPessoaIsEqualToSomething() throws Exception {
        // Initialize the database
        Pessoa pessoa = PessoaResourceIntTest.createEntity(em);
        em.persist(pessoa);
        em.flush();
        lancamento.setPessoa(pessoa);
        lancamentoRepository.saveAndFlush(lancamento);
        Long pessoaId = pessoa.getId();

        // Get all the lancamentoList where pessoa equals to pessoaId
        defaultLancamentoShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the lancamentoList where pessoa equals to pessoaId + 1
        defaultLancamentoShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLancamentoShouldBeFound(String filter) throws Exception {
        restLancamentoMockMvc.perform(get("/api/lancamentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].tipoLancamento").value(hasItem(DEFAULT_TIPO_LANCAMENTO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLancamentoShouldNotBeFound(String filter) throws Exception {
        restLancamentoMockMvc.perform(get("/api/lancamentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingLancamento() throws Exception {
        // Get the lancamento
        restLancamentoMockMvc.perform(get("/api/lancamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLancamento() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        int databaseSizeBeforeUpdate = lancamentoRepository.findAll().size();

        // Update the lancamento
        Lancamento updatedLancamento = lancamentoRepository.findById(lancamento.getId()).get();
        // Disconnect from session so that the updates on updatedLancamento are not directly saved in db
        em.detach(updatedLancamento);
        updatedLancamento
            .descricao(UPDATED_DESCRICAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .observacao(UPDATED_OBSERVACAO)
            .tipoLancamento(UPDATED_TIPO_LANCAMENTO);
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(updatedLancamento);

        restLancamentoMockMvc.perform(put("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeUpdate);
        Lancamento testLancamento = lancamentoList.get(lancamentoList.size() - 1);
        assertThat(testLancamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLancamento.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testLancamento.getDataPagamento()).isEqualTo(UPDATED_DATA_PAGAMENTO);
        assertThat(testLancamento.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testLancamento.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testLancamento.getTipoLancamento()).isEqualTo(UPDATED_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingLancamento() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoRepository.findAll().size();

        // Create the Lancamento
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLancamentoMockMvc.perform(put("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLancamento() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        int databaseSizeBeforeDelete = lancamentoRepository.findAll().size();

        // Get the lancamento
        restLancamentoMockMvc.perform(delete("/api/lancamentos/{id}", lancamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lancamento.class);
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setId(1L);
        Lancamento lancamento2 = new Lancamento();
        lancamento2.setId(lancamento1.getId());
        assertThat(lancamento1).isEqualTo(lancamento2);
        lancamento2.setId(2L);
        assertThat(lancamento1).isNotEqualTo(lancamento2);
        lancamento1.setId(null);
        assertThat(lancamento1).isNotEqualTo(lancamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoDTO.class);
        LancamentoDTO lancamentoDTO1 = new LancamentoDTO();
        lancamentoDTO1.setId(1L);
        LancamentoDTO lancamentoDTO2 = new LancamentoDTO();
        assertThat(lancamentoDTO1).isNotEqualTo(lancamentoDTO2);
        lancamentoDTO2.setId(lancamentoDTO1.getId());
        assertThat(lancamentoDTO1).isEqualTo(lancamentoDTO2);
        lancamentoDTO2.setId(2L);
        assertThat(lancamentoDTO1).isNotEqualTo(lancamentoDTO2);
        lancamentoDTO1.setId(null);
        assertThat(lancamentoDTO1).isNotEqualTo(lancamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lancamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lancamentoMapper.fromId(null)).isNull();
    }
}
