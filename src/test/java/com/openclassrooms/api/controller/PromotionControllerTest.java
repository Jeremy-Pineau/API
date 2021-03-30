package com.openclassrooms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.api.model.Promotion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PromotionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PromotionController promotionController;

    @Test
    void getPromotions() throws Exception {
        mockMvc.perform(get("/promotion/all"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].codePromo", containsInAnyOrder("HALFNEXT", "TIERSNEXT")))
                .andExpect(jsonPath("$[*].detail", containsInAnyOrder("30% sur le prochain achat", "50% sur le prochain achat")))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
        ;
    }

    @Test
    void getPromotion() throws Exception {
        List<String> cp = new ArrayList<>();
        List<String> det = new ArrayList<>();
        cp.add("HALFNEXT");
        cp.add("TIERSNEXT");
        det.add("50% sur le prochain achat");
        det.add("30% sur le prochain achat");

        for(int i = 1; i < 3; i++){
            mockMvc.perform(get("/promotion/" + i))
                    .andExpect(status().isOk())
                    .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                    .andExpect(jsonPath("$.codePromo", is(cp.get(i-1))))
                    .andExpect(jsonPath("$.detail", is(det.get(i-1))))
                    .andExpect(jsonPath("$.id", is(i)))
            ;
        }
    }

    @Test
    void createUpdateDeletePromotion() throws Exception {
        Promotion p = new Promotion();
        p.setId(8);
        p.setCodePromo("test cp");
        p.setDetail("test detail");

        int nbPromos = promotionController.getPromotions().size();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(p);
        mockMvc.perform(post("/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.codePromo", is("test cp")))
                .andExpect(jsonPath("$.detail", is("test detail")))
        ;

        assertEquals(nbPromos+1, promotionController.getPromotions().size());

        p.setCodePromo("new cp");
        p.setDetail("new detail");
        mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(p);
        mockMvc.perform(put("/promotion/" + p.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.codePromo", is("new cp")))
                .andExpect(jsonPath("$.detail", is("new detail")))
        ;

        promotionController.deletePromotion(8);
        assertEquals(nbPromos, promotionController.getPromotions().size());
    }

    @Test
    void testUpdateIdNotExist() throws Exception {
        Promotion p = new Promotion();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(p);

        mockMvc.perform(put("/promotion/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void testUpdateWithoutUserParam() throws Exception {
        mockMvc.perform(put("/promotion/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void deletePromotionNotExist() throws Exception {
        mockMvc.perform(delete("/promotion/" + 0))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
        ;
    }

    @Test
    void testCreateWithoutUserParam() throws Exception {
        mockMvc.perform(post("/promotion/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }
}