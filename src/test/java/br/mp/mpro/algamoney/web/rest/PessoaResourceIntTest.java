package br.mp.mpro.algamoney.web.rest;

import br.mp.mpro.algamoney.AlgamoneyApp;

import br.mp.mpro.algamoney.domain.Pessoa;
import br.mp.mpro.algamoney.domain.Endereco;
import br.mp.mpro.algamoney.domain.Contato;
import br.mp.mpro.algamoney.repository.PessoaRepository;
import br.mp.mpro.algamoney.service.PessoaService;
import br.mp.mpro.algamoney.service.dto.PessoaDTO;
import br.mp.mpro.algamoney.service.mapper.PessoaMapper;
import br.mp.mpro.algamoney.web.rest.errors.ExceptionTranslator;
import br.mp.mpro.algamoney.service.dto.PessoaCriteria;
import br.mp.mpro.algamoney.service.PessoaQueryService;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static br.mp.mpro.algamoney.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PessoaResource REST controller.
 *
 * @see PessoaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgamoneyApp.class)
public class PessoaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG_ARQUIVO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG_ARQUIVO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMG_ARQUIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_ARQUIVO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARQUIVO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARQUIVO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ARQUIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARQUIVO_CONTENT_TYPE = "image/png";

    @Autowired
    private PessoaRepository pessoaRepository;


    @Autowired
    private PessoaMapper pessoaMapper;
    

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaQueryService pessoaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PessoaResource pessoaResource = new PessoaResource(pessoaService, pessoaQueryService);
        this.restPessoaMockMvc = MockMvcBuilders.standaloneSetup(pessoaResource)
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
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(DEFAULT_NOME)
            .ativo(DEFAULT_ATIVO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .imgArquivo(DEFAULT_IMG_ARQUIVO)
            .imgArquivoContentType(DEFAULT_IMG_ARQUIVO_CONTENT_TYPE)
            .arquivo(DEFAULT_ARQUIVO)
            .arquivoContentType(DEFAULT_ARQUIVO_CONTENT_TYPE);
        return pessoa;
    }

    @Before
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testPessoa.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testPessoa.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testPessoa.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPessoa.getImgArquivo()).isEqualTo(DEFAULT_IMG_ARQUIVO);
        assertThat(testPessoa.getImgArquivoContentType()).isEqualTo(DEFAULT_IMG_ARQUIVO_CONTENT_TYPE);
        assertThat(testPessoa.getArquivo()).isEqualTo(DEFAULT_ARQUIVO);
        assertThat(testPessoa.getArquivoContentType()).isEqualTo(DEFAULT_ARQUIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa with an existing ID
        pessoa.setId(1L);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].imgArquivoContentType").value(hasItem(DEFAULT_IMG_ARQUIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imgArquivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG_ARQUIVO))))
            .andExpect(jsonPath("$.[*].arquivoContentType").value(hasItem(DEFAULT_ARQUIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].arquivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARQUIVO))));
    }
    

    @Test
    @Transactional
    public void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.imgArquivoContentType").value(DEFAULT_IMG_ARQUIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.imgArquivo").value(Base64Utils.encodeToString(DEFAULT_IMG_ARQUIVO)))
            .andExpect(jsonPath("$.arquivoContentType").value(DEFAULT_ARQUIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.arquivo").value(Base64Utils.encodeToString(DEFAULT_ARQUIVO)));
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome equals to DEFAULT_NOME
        defaultPessoaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPessoaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome is not null
        defaultPessoaShouldBeFound("nome.specified=true");

        // Get all the pessoaList where nome is null
        defaultPessoaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllPessoasByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where ativo equals to DEFAULT_ATIVO
        defaultPessoaShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the pessoaList where ativo equals to UPDATED_ATIVO
        defaultPessoaShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPessoasByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultPessoaShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the pessoaList where ativo equals to UPDATED_ATIVO
        defaultPessoaShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPessoasByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where ativo is not null
        defaultPessoaShouldBeFound("ativo.specified=true");

        // Get all the pessoaList where ativo is null
        defaultPessoaShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPessoasByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email equals to DEFAULT_EMAIL
        defaultPessoaShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the pessoaList where email equals to UPDATED_EMAIL
        defaultPessoaShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPessoasByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPessoaShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the pessoaList where email equals to UPDATED_EMAIL
        defaultPessoaShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPessoasByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email is not null
        defaultPessoaShouldBeFound("email.specified=true");

        // Get all the pessoaList where email is null
        defaultPessoaShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllPessoasByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        Endereco endereco = EnderecoResourceIntTest.createEntity(em);
        em.persist(endereco);
        em.flush();
        pessoa.setEndereco(endereco);
        pessoaRepository.saveAndFlush(pessoa);
        Long enderecoId = endereco.getId();

        // Get all the pessoaList where endereco equals to enderecoId
        defaultPessoaShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the pessoaList where endereco equals to enderecoId + 1
        defaultPessoaShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }


    @Test
    @Transactional
    public void getAllPessoasByContatosIsEqualToSomething() throws Exception {
        // Initialize the database
        Contato contatos = ContatoResourceIntTest.createEntity(em);
        em.persist(contatos);
        em.flush();
        pessoa.addContatos(contatos);
        pessoaRepository.saveAndFlush(pessoa);
        Long contatosId = contatos.getId();

        // Get all the pessoaList where contatos equals to contatosId
        defaultPessoaShouldBeFound("contatosId.equals=" + contatosId);

        // Get all the pessoaList where contatos equals to contatosId + 1
        defaultPessoaShouldNotBeFound("contatosId.equals=" + (contatosId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPessoaShouldBeFound(String filter) throws Exception {
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].imgArquivoContentType").value(hasItem(DEFAULT_IMG_ARQUIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imgArquivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG_ARQUIVO))))
            .andExpect(jsonPath("$.[*].arquivoContentType").value(hasItem(DEFAULT_ARQUIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].arquivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARQUIVO))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPessoaShouldNotBeFound(String filter) throws Exception {
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).get();
        // Disconnect from session so that the updates on updatedPessoa are not directly saved in db
        em.detach(updatedPessoa);
        updatedPessoa
            .nome(UPDATED_NOME)
            .ativo(UPDATED_ATIVO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .imgArquivo(UPDATED_IMG_ARQUIVO)
            .imgArquivoContentType(UPDATED_IMG_ARQUIVO_CONTENT_TYPE)
            .arquivo(UPDATED_ARQUIVO)
            .arquivoContentType(UPDATED_ARQUIVO_CONTENT_TYPE);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testPessoa.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testPessoa.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testPessoa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPessoa.getImgArquivo()).isEqualTo(UPDATED_IMG_ARQUIVO);
        assertThat(testPessoa.getImgArquivoContentType()).isEqualTo(UPDATED_IMG_ARQUIVO_CONTENT_TYPE);
        assertThat(testPessoa.getArquivo()).isEqualTo(UPDATED_ARQUIVO);
        assertThat(testPessoa.getArquivoContentType()).isEqualTo(UPDATED_ARQUIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Get the pessoa
        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoa.class);
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(pessoa1.getId());
        assertThat(pessoa1).isEqualTo(pessoa2);
        pessoa2.setId(2L);
        assertThat(pessoa1).isNotEqualTo(pessoa2);
        pessoa1.setId(null);
        assertThat(pessoa1).isNotEqualTo(pessoa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaDTO.class);
        PessoaDTO pessoaDTO1 = new PessoaDTO();
        pessoaDTO1.setId(1L);
        PessoaDTO pessoaDTO2 = new PessoaDTO();
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
        pessoaDTO2.setId(pessoaDTO1.getId());
        assertThat(pessoaDTO1).isEqualTo(pessoaDTO2);
        pessoaDTO2.setId(2L);
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
        pessoaDTO1.setId(null);
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pessoaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pessoaMapper.fromId(null)).isNull();
    }
}
