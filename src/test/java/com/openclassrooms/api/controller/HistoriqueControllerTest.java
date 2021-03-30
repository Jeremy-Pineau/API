package com.openclassrooms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.api.model.Historique;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HistoriqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HistoriqueController historiqueController;

    @Test
    void getHistorique() throws Exception {
        List<String> dates = new ArrayList<>();
        List<Integer> idpromos = new ArrayList<>();
        List<Integer> idusers = new ArrayList<>();
        dates.add("2021-03-09T00:00:00.000+00:00");
        idpromos.add(1);
        idusers.add(1);
        dates.add("2021-03-24T01:40:08.000+00:00");
        idpromos.add(2);
        idusers.add(1);


        mockMvc.perform(get("/historique/" + 3))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.dateScan", is(dates.get(0))))
                .andExpect(jsonPath("$.idUser", is(idusers.get(0))))
                .andExpect(jsonPath("$.idPromo", is(idpromos.get(0))))
        ;

        mockMvc.perform(get("/historique/" + 5))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.dateScan", is(dates.get(1))))
                .andExpect(jsonPath("$.idUser", is(idusers.get(1))))
                .andExpect(jsonPath("$.idPromo", is(idpromos.get(1))))
        ;
    }

    @Test
    void getHistoriques() throws Exception {
        mockMvc.perform(get("/historique/all"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].dateScan", containsInAnyOrder("2021-03-09T00:00:00.000+00:00", "2021-03-24T01:40:08.000+00:00")))
                .andExpect(jsonPath("$[*].idUser", containsInAnyOrder(1, 1)))
                .andExpect(jsonPath("$[*].idPromo", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(3, 5)))
        ;
    }

    @Test
    void getHistoriquesByUserId() throws Exception {
        mockMvc.perform(get("/historique/allByUserId/1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].dateScan", containsInAnyOrder("2021-03-09T00:00:00.000+00:00", "2021-03-24T01:40:08.000+00:00")))
                .andExpect(jsonPath("$[*].idUser", containsInAnyOrder(1, 1)))
                .andExpect(jsonPath("$[*].idPromo", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(3, 5)))
        ;
    }

    @Test
    void getHistoriquesByPromoId() throws Exception {
        mockMvc.perform(get("/historique/allByPromoId/1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].dateScan", is("2021-03-09T00:00:00.000+00:00")))
                .andExpect(jsonPath("$[0].idUser", is(1)))
                .andExpect(jsonPath("$[0].idPromo", is(1)))
                .andExpect(jsonPath("$[0].id", is(3)))
        ;

        mockMvc.perform(get("/historique/allByPromoId/2"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].dateScan", is("2021-03-24T01:40:08.000+00:00")))
                .andExpect(jsonPath("$[0].idUser", is(1)))
                .andExpect(jsonPath("$[0].idPromo", is(2)))
                .andExpect(jsonPath("$[0].id", is(5)))
        ;
    }

    @Test
    void getHistoriquesByPromoIdAndUserId() throws Exception {
        mockMvc.perform(get("/historique/allByPromoAndUser?idUser=1&idPromo=1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.dateScan", is("2021-03-09T00:00:00.000+00:00")))
                .andExpect(jsonPath("$.idUser", is(1)))
                .andExpect(jsonPath("$.idPromo", is(1)))
        ;

        mockMvc.perform(get("/historique/allByPromoAndUser?idUser=1&idPromo=2"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.dateScan", is("2021-03-24T01:40:08.000+00:00")))
                .andExpect(jsonPath("$.idUser", is(1)))
                .andExpect(jsonPath("$.idPromo", is(2)))
        ;
    }

    @Test
    void createHistorique() throws Exception {

    }

    @Test
    void testCreateWithoutUserParam() throws Exception {
        mockMvc.perform(post("/historique/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void createUpdateDeleteHistorique() throws Exception {
        Historique h = new Historique();
        h.setId(8);
        h.setIdUser(2);
        h.setIdPromo(1);
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        Date date = dt.parse("2020-03-09 00:00:00.0");
        h.setDateScan(date);

        int nbHistoriques = historiqueController.getHistoriques().size();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(h);
        mockMvc.perform(post("/historique")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.id", is(8)))
                .andExpect(jsonPath("$.idUser", is(2)))
                .andExpect(jsonPath("$.idPromo", is(1)))
        ;

        assertEquals(nbHistoriques+1, historiqueController.getHistoriques().size());

        h.setIdPromo(2);
        mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(h);
        mockMvc.perform(put("/historique/" + h.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.id", is(8)))
                .andExpect(jsonPath("$.idUser", is(2)))
                .andExpect(jsonPath("$.idPromo", is(2)))
        ;

        historiqueController.deleteHistorique(8);
        assertEquals(nbHistoriques, historiqueController.getHistoriques().size());
    }

    @Test
    void testUpdateIdNotExist() throws Exception {
        Historique h = new Historique();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(h);

        mockMvc.perform(put("/historique/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void testUpdateWithoutParam() throws Exception {
        mockMvc.perform(put("/historique/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void deleteHistorique() throws Exception {
        mockMvc.perform(delete("/historique/" + 0))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
        ;
    }
}