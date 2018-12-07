package br.mp.mpro.algamoney.web.rest;

import br.mp.mpro.algamoney.AlgamoneyApp;

import br.mp.mpro.algamoney.domain.Contato;
import br.mp.mpro.algamoney.domain.Pessoa;
import br.mp.mpro.algamoney.repository.ContatoRepository;
import br.mp.mpro.algamoney.service.ContatoService;
import br.mp.mpro.algamoney.service.dto.ContatoDTO;
import br.mp.mpro.algamoney.service.mapper.ContatoMapper;
import br.mp.mpro.algamoney.web.rest.errors.ExceptionTranslator;
import br.mp.mpro.algamoney.service.dto.ContatoCriteria;
import br.mp.mpro.algamoney.service.ContatoQueryService;

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
import java.util.List;


import static br.mp.mpro.algamoney.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContatoResource REST controller.
 *
 * @see ContatoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgamoneyApp.class)
public class ContatoResourceIntTest {

    private static final Long DEFAULT_CODIGO = 1L;
    private static final Long UPDATED_CODIGO = 2L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_TESTE = "AAAAAAAAAA";
    private static final String UPDATED_TESTE = "BBBBBBBBBB";

    private static final String DEFAULT_TESTE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TESTE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TESTE_3 = "AAAAAAAAAA";
    private static final String UPDATED_TESTE_3 = "BBBBBBBBBB";

    @Autowired
    private ContatoRepository contatoRepository;


    @Autowired
    private ContatoMapper contatoMapper;
    

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private ContatoQueryService contatoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContatoMockMvc;

    private Contato contato;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContatoResource contatoResource = new ContatoResource(contatoService, contatoQueryService);
        this.restContatoMockMvc = MockMvcBuilders.standaloneSetup(contatoResource)
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
    public static Contato createEntity(EntityManager em) {
        Contato contato = new Contato()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .teste(DEFAULT_TESTE)
            .teste2(DEFAULT_TESTE_2)
            .teste3(DEFAULT_TESTE_3);
        // Add required entity
        Pessoa pessoa = PessoaResourceIntTest.createEntity(em);
        em.persist(pessoa);
        em.flush();
        contato.setPessoa(pessoa);
        return contato;
    }

    @Before
    public void initTest() {
        contato = createEntity(em);
    }

    @Test
    @Transactional
    public void createContato() throws Exception {
        int databaseSizeBeforeCreate = contatoRepository.findAll().size();

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);
        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate + 1);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testContato.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContato.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContato.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testContato.getTeste()).isEqualTo(DEFAULT_TESTE);
        assertThat(testContato.getTeste2()).isEqualTo(DEFAULT_TESTE_2);
        assertThat(testContato.getTeste3()).isEqualTo(DEFAULT_TESTE_3);
    }

    @Test
    @Transactional
    public void createContatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoRepository.findAll().size();

        // Create the Contato with an existing ID
        contato.setId(1L);
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setNome(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setEmail(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setTelefone(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoes() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList
        restContatoMockMvc.perform(get("/api/contatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contato.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].teste").value(hasItem(DEFAULT_TESTE.toString())))
            .andExpect(jsonPath("$.[*].teste2").value(hasItem(DEFAULT_TESTE_2.toString())))
            .andExpect(jsonPath("$.[*].teste3").value(hasItem(DEFAULT_TESTE_3.toString())));
    }
    

    @Test
    @Transactional
    public void getContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get the contato
        restContatoMockMvc.perform(get("/api/contatoes/{id}", contato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contato.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.teste").value(DEFAULT_TESTE.toString()))
            .andExpect(jsonPath("$.teste2").value(DEFAULT_TESTE_2.toString()))
            .andExpect(jsonPath("$.teste3").value(DEFAULT_TESTE_3.toString()));
    }

    @Test
    @Transactional
    public void getAllContatoesByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where codigo equals to DEFAULT_CODIGO
        defaultContatoShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the contatoList where codigo equals to UPDATED_CODIGO
        defaultContatoShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllContatoesByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultContatoShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the contatoList where codigo equals to UPDATED_CODIGO
        defaultContatoShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllContatoesByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where codigo is not null
        defaultContatoShouldBeFound("codigo.specified=true");

        // Get all the contatoList where codigo is null
        defaultContatoShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByCodigoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where codigo greater than or equals to DEFAULT_CODIGO
        defaultContatoShouldBeFound("codigo.greaterOrEqualThan=" + DEFAULT_CODIGO);

        // Get all the contatoList where codigo greater than or equals to UPDATED_CODIGO
        defaultContatoShouldNotBeFound("codigo.greaterOrEqualThan=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllContatoesByCodigoIsLessThanSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where codigo less than or equals to DEFAULT_CODIGO
        defaultContatoShouldNotBeFound("codigo.lessThan=" + DEFAULT_CODIGO);

        // Get all the contatoList where codigo less than or equals to UPDATED_CODIGO
        defaultContatoShouldBeFound("codigo.lessThan=" + UPDATED_CODIGO);
    }


    @Test
    @Transactional
    public void getAllContatoesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome equals to DEFAULT_NOME
        defaultContatoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the contatoList where nome equals to UPDATED_NOME
        defaultContatoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultContatoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the contatoList where nome equals to UPDATED_NOME
        defaultContatoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome is not null
        defaultContatoShouldBeFound("nome.specified=true");

        // Get all the contatoList where nome is null
        defaultContatoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email equals to DEFAULT_EMAIL
        defaultContatoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contatoList where email equals to UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContatoesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContatoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contatoList where email equals to UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContatoesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email is not null
        defaultContatoShouldBeFound("email.specified=true");

        // Get all the contatoList where email is null
        defaultContatoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone equals to DEFAULT_TELEFONE
        defaultContatoShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the contatoList where telefone equals to UPDATED_TELEFONE
        defaultContatoShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllContatoesByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultContatoShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the contatoList where telefone equals to UPDATED_TELEFONE
        defaultContatoShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllContatoesByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone is not null
        defaultContatoShouldBeFound("telefone.specified=true");

        // Get all the contatoList where telefone is null
        defaultContatoShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByTesteIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste equals to DEFAULT_TESTE
        defaultContatoShouldBeFound("teste.equals=" + DEFAULT_TESTE);

        // Get all the contatoList where teste equals to UPDATED_TESTE
        defaultContatoShouldNotBeFound("teste.equals=" + UPDATED_TESTE);
    }

    @Test
    @Transactional
    public void getAllContatoesByTesteIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste in DEFAULT_TESTE or UPDATED_TESTE
        defaultContatoShouldBeFound("teste.in=" + DEFAULT_TESTE + "," + UPDATED_TESTE);

        // Get all the contatoList where teste equals to UPDATED_TESTE
        defaultContatoShouldNotBeFound("teste.in=" + UPDATED_TESTE);
    }

    @Test
    @Transactional
    public void getAllContatoesByTesteIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste is not null
        defaultContatoShouldBeFound("teste.specified=true");

        // Get all the contatoList where teste is null
        defaultContatoShouldNotBeFound("teste.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByTeste2IsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste2 equals to DEFAULT_TESTE_2
        defaultContatoShouldBeFound("teste2.equals=" + DEFAULT_TESTE_2);

        // Get all the contatoList where teste2 equals to UPDATED_TESTE_2
        defaultContatoShouldNotBeFound("teste2.equals=" + UPDATED_TESTE_2);
    }

    @Test
    @Transactional
    public void getAllContatoesByTeste2IsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste2 in DEFAULT_TESTE_2 or UPDATED_TESTE_2
        defaultContatoShouldBeFound("teste2.in=" + DEFAULT_TESTE_2 + "," + UPDATED_TESTE_2);

        // Get all the contatoList where teste2 equals to UPDATED_TESTE_2
        defaultContatoShouldNotBeFound("teste2.in=" + UPDATED_TESTE_2);
    }

    @Test
    @Transactional
    public void getAllContatoesByTeste2IsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste2 is not null
        defaultContatoShouldBeFound("teste2.specified=true");

        // Get all the contatoList where teste2 is null
        defaultContatoShouldNotBeFound("teste2.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByTeste3IsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste3 equals to DEFAULT_TESTE_3
        defaultContatoShouldBeFound("teste3.equals=" + DEFAULT_TESTE_3);

        // Get all the contatoList where teste3 equals to UPDATED_TESTE_3
        defaultContatoShouldNotBeFound("teste3.equals=" + UPDATED_TESTE_3);
    }

    @Test
    @Transactional
    public void getAllContatoesByTeste3IsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste3 in DEFAULT_TESTE_3 or UPDATED_TESTE_3
        defaultContatoShouldBeFound("teste3.in=" + DEFAULT_TESTE_3 + "," + UPDATED_TESTE_3);

        // Get all the contatoList where teste3 equals to UPDATED_TESTE_3
        defaultContatoShouldNotBeFound("teste3.in=" + UPDATED_TESTE_3);
    }

    @Test
    @Transactional
    public void getAllContatoesByTeste3IsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where teste3 is not null
        defaultContatoShouldBeFound("teste3.specified=true");

        // Get all the contatoList where teste3 is null
        defaultContatoShouldNotBeFound("teste3.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByPessoaIsEqualToSomething() throws Exception {
        // Initialize the database
        Pessoa pessoa = PessoaResourceIntTest.createEntity(em);
        em.persist(pessoa);
        em.flush();
        contato.setPessoa(pessoa);
        contatoRepository.saveAndFlush(contato);
        Long pessoaId = pessoa.getId();

        // Get all the contatoList where pessoa equals to pessoaId
        defaultContatoShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the contatoList where pessoa equals to pessoaId + 1
        defaultContatoShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContatoShouldBeFound(String filter) throws Exception {
        restContatoMockMvc.perform(get("/api/contatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contato.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].teste").value(hasItem(DEFAULT_TESTE.toString())))
            .andExpect(jsonPath("$.[*].teste2").value(hasItem(DEFAULT_TESTE_2.toString())))
            .andExpect(jsonPath("$.[*].teste3").value(hasItem(DEFAULT_TESTE_3.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContatoShouldNotBeFound(String filter) throws Exception {
        restContatoMockMvc.perform(get("/api/contatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingContato() throws Exception {
        // Get the contato
        restContatoMockMvc.perform(get("/api/contatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Update the contato
        Contato updatedContato = contatoRepository.findById(contato.getId()).get();
        // Disconnect from session so that the updates on updatedContato are not directly saved in db
        em.detach(updatedContato);
        updatedContato
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .teste(UPDATED_TESTE)
            .teste2(UPDATED_TESTE_2)
            .teste3(UPDATED_TESTE_3);
        ContatoDTO contatoDTO = contatoMapper.toDto(updatedContato);

        restContatoMockMvc.perform(put("/api/contatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isOk());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testContato.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContato.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContato.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testContato.getTeste()).isEqualTo(UPDATED_TESTE);
        assertThat(testContato.getTeste2()).isEqualTo(UPDATED_TESTE_2);
        assertThat(testContato.getTeste3()).isEqualTo(UPDATED_TESTE_3);
    }

    @Test
    @Transactional
    public void updateNonExistingContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restContatoMockMvc.perform(put("/api/contatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeDelete = contatoRepository.findAll().size();

        // Get the contato
        restContatoMockMvc.perform(delete("/api/contatoes/{id}", contato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contato.class);
        Contato contato1 = new Contato();
        contato1.setId(1L);
        Contato contato2 = new Contato();
        contato2.setId(contato1.getId());
        assertThat(contato1).isEqualTo(contato2);
        contato2.setId(2L);
        assertThat(contato1).isNotEqualTo(contato2);
        contato1.setId(null);
        assertThat(contato1).isNotEqualTo(contato2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoDTO.class);
        ContatoDTO contatoDTO1 = new ContatoDTO();
        contatoDTO1.setId(1L);
        ContatoDTO contatoDTO2 = new ContatoDTO();
        assertThat(contatoDTO1).isNotEqualTo(contatoDTO2);
        contatoDTO2.setId(contatoDTO1.getId());
        assertThat(contatoDTO1).isEqualTo(contatoDTO2);
        contatoDTO2.setId(2L);
        assertThat(contatoDTO1).isNotEqualTo(contatoDTO2);
        contatoDTO1.setId(null);
        assertThat(contatoDTO1).isNotEqualTo(contatoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contatoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contatoMapper.fromId(null)).isNull();
    }
}
