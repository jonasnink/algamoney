package br.mp.mpro.algamoney.web.rest;

import br.mp.mpro.algamoney.AlgamoneyApp;

import br.mp.mpro.algamoney.domain.Endereco;
import br.mp.mpro.algamoney.repository.EnderecoRepository;
import br.mp.mpro.algamoney.service.EnderecoService;
import br.mp.mpro.algamoney.service.dto.EnderecoDTO;
import br.mp.mpro.algamoney.service.mapper.EnderecoMapper;
import br.mp.mpro.algamoney.web.rest.errors.ExceptionTranslator;
import br.mp.mpro.algamoney.service.dto.EnderecoCriteria;
import br.mp.mpro.algamoney.service.EnderecoQueryService;

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
 * Test class for the EnderecoResource REST controller.
 *
 * @see EnderecoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgamoneyApp.class)
public class EnderecoResourceIntTest {

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    @Autowired
    private EnderecoRepository enderecoRepository;


    @Autowired
    private EnderecoMapper enderecoMapper;
    

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoQueryService enderecoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnderecoMockMvc;

    private Endereco endereco;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnderecoResource enderecoResource = new EnderecoResource(enderecoService, enderecoQueryService);
        this.restEnderecoMockMvc = MockMvcBuilders.standaloneSetup(enderecoResource)
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
    public static Endereco createEntity(EntityManager em) {
        Endereco endereco = new Endereco()
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .cidade(DEFAULT_CIDADE)
            .estado(DEFAULT_ESTADO);
        return endereco;
    }

    @Before
    public void initTest() {
        endereco = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndereco() throws Exception {
        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);
        restEnderecoMockMvc.perform(post("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isCreated());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate + 1);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testEndereco.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEndereco.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEndereco.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEndereco.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEndereco.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createEnderecoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();

        // Create the Endereco with an existing ID
        endereco.setId(1L);
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoMockMvc.perform(post("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEnderecos() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList
        restEnderecoMockMvc.perform(get("/api/enderecos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    

    @Test
    @Transactional
    public void getEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get the endereco
        restEnderecoMockMvc.perform(get("/api/enderecos/{id}", endereco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(endereco.getId().intValue()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getAllEnderecosByLogradouroIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro equals to DEFAULT_LOGRADOURO
        defaultEnderecoShouldBeFound("logradouro.equals=" + DEFAULT_LOGRADOURO);

        // Get all the enderecoList where logradouro equals to UPDATED_LOGRADOURO
        defaultEnderecoShouldNotBeFound("logradouro.equals=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLogradouroIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro in DEFAULT_LOGRADOURO or UPDATED_LOGRADOURO
        defaultEnderecoShouldBeFound("logradouro.in=" + DEFAULT_LOGRADOURO + "," + UPDATED_LOGRADOURO);

        // Get all the enderecoList where logradouro equals to UPDATED_LOGRADOURO
        defaultEnderecoShouldNotBeFound("logradouro.in=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByLogradouroIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where logradouro is not null
        defaultEnderecoShouldBeFound("logradouro.specified=true");

        // Get all the enderecoList where logradouro is null
        defaultEnderecoShouldNotBeFound("logradouro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnderecosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero equals to DEFAULT_NUMERO
        defaultEnderecoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the enderecoList where numero equals to UPDATED_NUMERO
        defaultEnderecoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultEnderecoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the enderecoList where numero equals to UPDATED_NUMERO
        defaultEnderecoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where numero is not null
        defaultEnderecoShouldBeFound("numero.specified=true");

        // Get all the enderecoList where numero is null
        defaultEnderecoShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnderecosByComplementoIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento equals to DEFAULT_COMPLEMENTO
        defaultEnderecoShouldBeFound("complemento.equals=" + DEFAULT_COMPLEMENTO);

        // Get all the enderecoList where complemento equals to UPDATED_COMPLEMENTO
        defaultEnderecoShouldNotBeFound("complemento.equals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByComplementoIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento in DEFAULT_COMPLEMENTO or UPDATED_COMPLEMENTO
        defaultEnderecoShouldBeFound("complemento.in=" + DEFAULT_COMPLEMENTO + "," + UPDATED_COMPLEMENTO);

        // Get all the enderecoList where complemento equals to UPDATED_COMPLEMENTO
        defaultEnderecoShouldNotBeFound("complemento.in=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByComplementoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where complemento is not null
        defaultEnderecoShouldBeFound("complemento.specified=true");

        // Get all the enderecoList where complemento is null
        defaultEnderecoShouldNotBeFound("complemento.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnderecosByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro equals to DEFAULT_BAIRRO
        defaultEnderecoShouldBeFound("bairro.equals=" + DEFAULT_BAIRRO);

        // Get all the enderecoList where bairro equals to UPDATED_BAIRRO
        defaultEnderecoShouldNotBeFound("bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro in DEFAULT_BAIRRO or UPDATED_BAIRRO
        defaultEnderecoShouldBeFound("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO);

        // Get all the enderecoList where bairro equals to UPDATED_BAIRRO
        defaultEnderecoShouldNotBeFound("bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where bairro is not null
        defaultEnderecoShouldBeFound("bairro.specified=true");

        // Get all the enderecoList where bairro is null
        defaultEnderecoShouldNotBeFound("bairro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnderecosByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep equals to DEFAULT_CEP
        defaultEnderecoShouldBeFound("cep.equals=" + DEFAULT_CEP);

        // Get all the enderecoList where cep equals to UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCepIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep in DEFAULT_CEP or UPDATED_CEP
        defaultEnderecoShouldBeFound("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP);

        // Get all the enderecoList where cep equals to UPDATED_CEP
        defaultEnderecoShouldNotBeFound("cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cep is not null
        defaultEnderecoShouldBeFound("cep.specified=true");

        // Get all the enderecoList where cep is null
        defaultEnderecoShouldNotBeFound("cep.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnderecosByCidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade equals to DEFAULT_CIDADE
        defaultEnderecoShouldBeFound("cidade.equals=" + DEFAULT_CIDADE);

        // Get all the enderecoList where cidade equals to UPDATED_CIDADE
        defaultEnderecoShouldNotBeFound("cidade.equals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCidadeIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade in DEFAULT_CIDADE or UPDATED_CIDADE
        defaultEnderecoShouldBeFound("cidade.in=" + DEFAULT_CIDADE + "," + UPDATED_CIDADE);

        // Get all the enderecoList where cidade equals to UPDATED_CIDADE
        defaultEnderecoShouldNotBeFound("cidade.in=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllEnderecosByCidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where cidade is not null
        defaultEnderecoShouldBeFound("cidade.specified=true");

        // Get all the enderecoList where cidade is null
        defaultEnderecoShouldNotBeFound("cidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnderecosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado equals to DEFAULT_ESTADO
        defaultEnderecoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the enderecoList where estado equals to UPDATED_ESTADO
        defaultEnderecoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultEnderecoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the enderecoList where estado equals to UPDATED_ESTADO
        defaultEnderecoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllEnderecosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList where estado is not null
        defaultEnderecoShouldBeFound("estado.specified=true");

        // Get all the enderecoList where estado is null
        defaultEnderecoShouldNotBeFound("estado.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEnderecoShouldBeFound(String filter) throws Exception {
        restEnderecoMockMvc.perform(get("/api/enderecos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEnderecoShouldNotBeFound(String filter) throws Exception {
        restEnderecoMockMvc.perform(get("/api/enderecos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingEndereco() throws Exception {
        // Get the endereco
        restEnderecoMockMvc.perform(get("/api/enderecos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Update the endereco
        Endereco updatedEndereco = enderecoRepository.findById(endereco.getId()).get();
        // Disconnect from session so that the updates on updatedEndereco are not directly saved in db
        em.detach(updatedEndereco);
        updatedEndereco
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO);
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(updatedEndereco);

        restEnderecoMockMvc.perform(put("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isOk());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEndereco.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEndereco.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEndereco.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEndereco.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Create the Endereco
        EnderecoDTO enderecoDTO = enderecoMapper.toDto(endereco);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnderecoMockMvc.perform(put("/api/enderecos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enderecoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        int databaseSizeBeforeDelete = enderecoRepository.findAll().size();

        // Get the endereco
        restEnderecoMockMvc.perform(delete("/api/enderecos/{id}", endereco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Endereco.class);
        Endereco endereco1 = new Endereco();
        endereco1.setId(1L);
        Endereco endereco2 = new Endereco();
        endereco2.setId(endereco1.getId());
        assertThat(endereco1).isEqualTo(endereco2);
        endereco2.setId(2L);
        assertThat(endereco1).isNotEqualTo(endereco2);
        endereco1.setId(null);
        assertThat(endereco1).isNotEqualTo(endereco2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoDTO.class);
        EnderecoDTO enderecoDTO1 = new EnderecoDTO();
        enderecoDTO1.setId(1L);
        EnderecoDTO enderecoDTO2 = new EnderecoDTO();
        assertThat(enderecoDTO1).isNotEqualTo(enderecoDTO2);
        enderecoDTO2.setId(enderecoDTO1.getId());
        assertThat(enderecoDTO1).isEqualTo(enderecoDTO2);
        enderecoDTO2.setId(2L);
        assertThat(enderecoDTO1).isNotEqualTo(enderecoDTO2);
        enderecoDTO1.setId(null);
        assertThat(enderecoDTO1).isNotEqualTo(enderecoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enderecoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enderecoMapper.fromId(null)).isNull();
    }
}
